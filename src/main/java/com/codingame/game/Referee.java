package com.codingame.game;

import com.codingame.gameengine.core.AbstractMultiplayerPlayer;
import com.codingame.gameengine.core.AbstractPlayer.TimeoutException;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.GameManager;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.endscreen.EndScreenModule;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import java.util.Random;
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;

public class Referee extends AbstractReferee {

    @Inject
    private MultiplayerGameManager<Player> gameManager;
    @Inject
    private GraphicEntityModule graphicEntityModule;
    @Inject
    private EndScreenModule endScreenModule;

    private Viewer viewer;

    private final Random random = new Random();
    private static final int MAX_TURN = 100;

    private int playerCount;
    private int turn;


    @Override
    public void init() {
        playerCount = gameManager.getPlayerCount();
        gameManager.setMaxTurns(MAX_TURN * playerCount);
        random.setSeed(gameManager.getSeed());
        viewer = new Viewer(gameManager, graphicEntityModule);
        viewer.init(new Board(20, 20));
    }

    @Override
    public void gameTurn(int turn) {
        for (Player p : gameManager.getActivePlayers()) {
            sendInput(p, turn);
            p.execute();
        }
        this.turn = turn;

        for (Player player : gameManager.getActivePlayers()) {
            try {
                String action = player.getAction();
                gameManager.addToGameSummary(String.format("Player %s played %s", player.getNicknameToken(), action));
            } catch (TimeoutException e) {
                deactivatePlayer(player, "timeout!");
            }
        }
        calculateState(turn);
        if (turn == MAX_TURN) {
            endGame();
        }

    }

    private void calculateState(int turn) {

    }


    private void sendInput(Player player, int turn) {
        if (turn < gameManager.getPlayerCount()) {
            // Color
            player.sendInputLine(player.getIndex() == 1 ? "RED" : "BLUE");
        }

        player.sendInputLine("2");
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
