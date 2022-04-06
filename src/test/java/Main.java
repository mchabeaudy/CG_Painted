import com.codingame.gameengine.runner.MultiplayerGameRunner;

public class Main {


    public static void main(String[] args)
    {
        MultiplayerGameRunner gameRunner = new MultiplayerGameRunner();
        gameRunner.addAgent(Player1.class, "Bob");
        gameRunner.addAgent(Player1.class, "Alice");
        gameRunner.setLeagueLevel(1);
        gameRunner.start();
    }
}
