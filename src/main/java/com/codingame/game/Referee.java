package com.codingame.game;

import static com.codingame.game.map.MapElement.TEAM1;
import static com.codingame.game.map.MapElement.TEAM2;

import com.codingame.game.action.Action;
import com.codingame.game.action.InvalidAction;
import com.codingame.game.map.Displayable;
import com.codingame.game.map.GameMap;
import com.codingame.game.map.Robot;
import com.codingame.gameengine.core.AbstractMultiplayerPlayer;
import com.codingame.gameengine.core.AbstractPlayer.TimeoutException;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.GameManager;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.endscreen.EndScreenModule;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.tooltip.TooltipModule;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import javax.inject.Inject;

public class Referee extends AbstractReferee {

    @Inject
    private MultiplayerGameManager<Player> gameManager;
    @Inject
    private GraphicEntityModule graphicEntityModule;
    @Inject
    private EndScreenModule endScreenModule;
    @Inject
    private TooltipModule tooltipModule;

    private final List<Robot> robots = new ArrayList<>();
    private Viewer viewer;
    private Board board;

    private final Random random = new Random();
    private static final int MAX_TURN = 100;


    private int playerCount;


    @Override
    public void init() {
        playerCount = gameManager.getPlayerCount();
        gameManager.setMaxTurns(MAX_TURN * playerCount);
        random.setSeed(gameManager.getSeed());
        gameManager.setFrameDuration(400);
        switch (gameManager.getLeagueLevel()) {
            case 1:
                initLevel1();
                break;
            case 2:
            case 3:
                initLevel23(gameManager.getLeagueLevel());
                break;
            case 4:
                initLevel4();
                break;
            default:
                throw new IllegalStateException(
                        "Level " + gameManager.getLeagueLevel() + " has not been implemented");
        }
    }

    private void initLevel1() {
        Player p1 = gameManager.getPlayer(0);
        p1.setRobotCount(1);
        p1.setTeamId(1);
        Player p2 = gameManager.getPlayer(1);
        p2.setRobotCount(1);
        p2.setTeamId(2);
        robots.add(new Robot(1, p1.getTeamId(), p1.getIndex(), 1));
        robots.add(new Robot(2, p2.getTeamId(), p2.getIndex(), 2));

        board = new Board(15, 15, new GameMap(random, 1));
        viewer = new Viewer(gameManager, graphicEntityModule);
        viewer.init(board, robots, 1, gameManager.getPlayers());
    }


    private void initLevel23(int level) {
        Player p1 = gameManager.getPlayer(0);
        p1.setTeamId(1);
        p1.setRobotCount(2);
        Player p2 = gameManager.getPlayer(1);
        p2.setTeamId(2);
        p2.setRobotCount(2);
        robots.add(new Robot(1, p1.getTeamId(), p1.getIndex(), 1));
        robots.add(new Robot(2, p1.getTeamId(), p1.getIndex(), 4));
        robots.add(new Robot(3, p2.getTeamId(), p2.getIndex(), 2));
        robots.add(new Robot(4, p2.getTeamId(), p2.getIndex(), 3));

        board = new Board(24, 19, new GameMap(random, level));
        viewer = new Viewer(gameManager, graphicEntityModule);
        viewer.init(board, robots, level, gameManager.getPlayers());
    }

    private void initLevel4() {
        Player p1 = gameManager.getPlayer(0);
        p1.setTeamId(1);
        p1.setRobotCount(2);
        Player p2 = gameManager.getPlayer(1);
        p2.setTeamId(1);
        p2.setRobotCount(2);
        Player p3 = gameManager.getPlayer(2);
        p3.setTeamId(2);
        p3.setRobotCount(2);
        Player p4 = gameManager.getPlayer(3);
        p4.setTeamId(2);
        p4.setRobotCount(2);
        robots.add(new Robot(1, p1.getTeamId(), p1.getIndex(), 1));
        robots.add(new Robot(2, p1.getTeamId(), p1.getIndex(), 8));
        robots.add(new Robot(3, p2.getTeamId(), p2.getIndex(), 2));
        robots.add(new Robot(4, p2.getTeamId(), p2.getIndex(), 7));
        robots.add(new Robot(5, p3.getTeamId(), p3.getIndex(), 3));
        robots.add(new Robot(6, p3.getTeamId(), p3.getIndex(), 6));
        robots.add(new Robot(7, p4.getTeamId(), p4.getIndex(), 4));
        robots.add(new Robot(8, p4.getTeamId(), p4.getIndex(), 5));

        board = new Board(24, 19, new GameMap(random, 4));
        viewer = new Viewer(gameManager, graphicEntityModule);
        viewer.init(board, robots, 4, gameManager.getPlayers());
    }

    @Override
    public void gameTurn(int turn) {
        robots.forEach(r -> r.nextInit(robots.size()));
        if (turn == 1) {
            sendFirstInputs();
        }
        for (Player p : gameManager.getActivePlayers()) {
            sendInput(p);
            p.execute();
        }
        handleActions();
        calculateState();
        viewer.paint(robots);
        calculateScore();
        if (turn == MAX_TURN) {
            endGame();
        }

    }

    private void handleActions() {
        for (Player player : gameManager.getActivePlayers()) {
            try {
                List<Action> actions = player.getActions();
                List<Robot> playerRobots = getUnits(player.getIndex());
                for (int i = 0; i < actions.size(); i++) {
                    playerRobots.get(i).setAction(actions.get(i));
                }
                gameManager.addToGameSummary(
                        String.format("Player %s played :%s", player.getNicknameToken(),
                                System.lineSeparator() + actions.stream().map(Action::instruction)
                                        .collect(Collectors.joining(System.lineSeparator()))));
            } catch (TimeoutException e) {
                deactivatePlayer(player, "timeout!");
            } catch (InvalidAction e) {
                deactivatePlayer(player, e.getMessage());
            }
        }
    }

    private void sendFirstInputs() {
        for (Player player : gameManager.getActivePlayers()) {
            player.sendInputLine(Integer.toString(player.getIndex()));
            player.sendInputLine(Integer.toString(player.getTeamId()));
            player.sendInputLine(Integer.toString(viewer.getBoard().getWidth()));
            player.sendInputLine(Integer.toString(viewer.getBoard().getHeight()));
            player.sendInputLine(Integer.toString(board.getGameMap().getTeleports().size()));
            player.sendInputLine(Integer.toString(board.getGameMap().getBoxes().size()));
            player.sendInputLine(Integer.toString(robots.size()));
            player.sendInputLine(Integer.toString(robots.size() / gameManager.getPlayerCount()));
            sendDisplayableToInput(board.getGameMap().getTeleports(), player);
        }
    }

    private void calculateScore() {
        int score1 = 0;
        int score2 = 0;
        for (TileUI[] line : viewer.getTiles()) {
            for (TileUI tile : line) {
                if (tile.getElement() == TEAM1) {
                    score1++;
                } else if (tile.getElement() == TEAM2) {
                    score2++;
                }
            }
        }
        viewer.updateScores(score1, score2);
    }

    private void calculateState() {
        robots.stream()
                .filter(r -> isActivePlayer(r.getPlayerId()))
                .sorted(Comparator.comparingInt(Robot::getInit))
                .forEach(viewer::resolveAction);
        robots.forEach(r -> tooltipModule.setTooltipText(r.getUi().getSprite(),
                "x:" + r.getUi().getX() + " y:" + r.getUi().getY()));
    }

    private boolean isActivePlayer(int playerId) {
        return gameManager.getActivePlayers().stream().anyMatch(p -> p.getIndex() == playerId);
    }

    private List<Robot> getUnits(int playerIndex) {
        return robots.stream()
                .filter(u -> u.getPlayerId() == playerIndex)
                .sorted(Comparator.comparingInt(Robot::getId))
                .collect(Collectors.toList());
    }


    private void sendInput(Player player) {
        for (int y = 0; y < board.getHeight(); y++) {
            TileUI[] tilesLine = viewer.getTiles()[y];
            String line = Arrays.stream(tilesLine).map(t -> t.getElement().getDisplay())
                    .collect(Collectors.joining());
            player.sendInputLine(line);
        }
        sendDisplayableToInput(robots, player);
        sendDisplayableToInput(board.getGameMap().getBoxes(), player);
    }

    private <T extends Displayable> void sendDisplayableToInput(List<T> displayableList,
            Player player) {
        displayableList.stream()
                .map(Displayable::toDisplay)
                .forEach(player::sendInputLine);
    }

    private void endGame() {
        gameManager.getPlayers().forEach(p -> p.setScore(
                Integer.parseInt((p.getTeamId() == 1 ? viewer.getScore1() : viewer.getScore2()).getText())));
        gameManager.endGame();
    }

    @Override
    public void onEnd() {
        endScreenModule.setScores(
                gameManager.getPlayers().stream().mapToInt(AbstractMultiplayerPlayer::getScore)
                        .toArray());
    }

    private void deactivatePlayer(Player player, String message) {
        gameManager.addToGameSummary(
                GameManager.formatErrorMessage(player.getNicknameToken() + " - " + message));
        player.deactivate(message);
        player.setScore(-1);
    }
}
