package com.codingame.game;

import com.codingame.game.action.Action;
import com.codingame.game.action.InvalidAction;
import com.codingame.game.action.MoveAction;
import com.codingame.game.map.Displayable;
import com.codingame.game.map.GameMap;
import com.codingame.game.map.Unit;
import com.codingame.gameengine.core.AbstractMultiplayerPlayer;
import com.codingame.gameengine.core.AbstractPlayer.TimeoutException;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.GameManager;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.endscreen.EndScreenModule;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
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

    private Viewer viewer;
    private Board board;
    private List<Unit> units = new ArrayList<>();

    private final Random random = new Random();
    private static final int MAX_TURN = 100;


    private int playerCount;
    private int turn;


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
                initLevel2();
                break;
            default:
                throw new IllegalStateException("Level " + gameManager.getLeagueLevel() + " has not been implemented");
        }
    }

    private void initLevel1() {
        Player p1 = gameManager.getPlayer(0);
        p1.setTeamId(1);
        Unit u1 = new Unit();
        u1.setPlayerId(p1.getIndex());
        u1.setTeam(p1.getTeamId());
        u1.setId(1);
        units.add(u1);

        Player p2 = gameManager.getPlayer(1);
        p2.setTeamId(2);
        Unit u2 = new Unit();
        u2.setPlayerId(p2.getIndex());
        u2.setTeam(p2.getTeamId());
        u2.setId(2);
        units.add(u2);

        board = new Board(15, 15, new GameMap(random, 1));
        viewer = new Viewer(gameManager, graphicEntityModule);
        viewer.init(board, units,1);
    }

    private void initLevel2() {
        Player p1 = gameManager.getPlayer(0);
        p1.setTeamId(1);
        Unit u1 = new Unit();
        u1.setPlayerId(p1.getIndex());
        u1.setTeam(p1.getTeamId());
        u1.setId(1);
        units.add(u1);

        Player p2 = gameManager.getPlayer(1);
        p2.setTeamId(2);
        Unit u2 = new Unit();
        u2.setPlayerId(p2.getIndex());
        u2.setTeam(p2.getTeamId());
        u2.setId(2);
        units.add(u2);

        board = new Board(24, 19, new GameMap(random, 2));
        viewer = new Viewer(gameManager, graphicEntityModule);
        viewer.init(board, units,2);
    }

    @Override
    public void gameTurn(int turn) {
        this.turn = turn;
        if (turn == 1) {
            sendFirstInputs();
        }
        for (Player p : gameManager.getActivePlayers()) {
            sendInput(p);
            p.execute();
        }

        for (Player player : gameManager.getActivePlayers()) {
            try {
                List<Action> actions = player.getAction();
                List<Unit> playerUnits = getUnits(player.getIndex());
                for (int i = 0; i < actions.size(); i++) {
                    playerUnits.get(i).setAction(actions.get(i));
                }
                gameManager.addToGameSummary(
                        String.format("Player %s played :%s", player.getNicknameToken(),
                                System.lineSeparator() + actions.stream().map(Action::getMessage)
                                        .collect(Collectors.joining(System.lineSeparator()))));
            } catch (TimeoutException e) {
                deactivatePlayer(player, "timeout!");
            } catch (InvalidAction e) {
                deactivatePlayer(player, e.getMessage());
            }
        }
        calculateState();
        viewer.paint();
        if (turn == MAX_TURN) {
            endGame();
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
            player.sendInputLine(Integer.toString(units.size()));
            sendDisplayableToInput(board.getGameMap().getTeleports(), player);
        }
    }

    private void calculateState() {
        units.forEach(u -> {
            Action a = u.getAction();
            if (a != null && a.getMoveAction() == MoveAction.MOVE) {
                switch (a.getDirection()) {
                    case UP:
                        u.getUi().moveUp();
                        break;
                    case DOWN:
                        u.getUi().moveDown();
                        break;
                    case LEFT:
                        u.getUi().moveLeft();
                        break;
                    case RIGHT:
                        u.getUi().moveRight();
                        break;
                    default:
                        throw new IllegalStateException("Wrong direction : " + a.getDirection());
                }
            }
        });
    }

    private List<Unit> getUnits(int playerIndex) {
        return units.stream()
                .filter(u -> u.getPlayerId() == playerIndex)
                .sorted(Comparator.comparingInt(Unit::getId))
                .collect(Collectors.toList());
    }


    private void sendInput(Player player) {
        for (int y = 0; y < board.getHeight(); y++) {
            TileUI[] tilesLine = viewer.getTiles()[y];
            String line = Arrays.stream(tilesLine).map(t -> t.getElement().getDisplay()).collect(Collectors.joining());
            player.sendInputLine(line);
        }
        sendDisplayableToInput(units, player);
        sendDisplayableToInput(board.getGameMap().getBoxes(), player);
    }

    private <T extends Displayable> void sendDisplayableToInput(List<T> displayableList, Player player) {
        displayableList.stream()
                .map(Displayable::toDisplay)
                .forEach(player::sendInputLine);
    }

    private void endGame() {
        gameManager.endGame();
    }

    @Override
    public void onEnd() {
        endScreenModule.setScores(
                gameManager.getPlayers().stream().mapToInt(AbstractMultiplayerPlayer::getScore).toArray());
    }

    private void deactivatePlayer(Player player, String message) {
        gameManager.addToGameSummary(GameManager.formatErrorMessage(player.getNicknameToken() + " - " + message));
        player.deactivate(message);
        player.setScore(-1);
    }
}
