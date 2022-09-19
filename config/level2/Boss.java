import static java.util.stream.IntStream.range;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Player {

    private static Random random = new Random();

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int playerId = in.nextInt(); // your player id
        int teamId = in.nextInt(); // your team id
        int boardWidth = in.nextInt(); // width of the board
        int boardHeight = in.nextInt(); // height of the board
        int tpCount = in.nextInt(); // number of teleport
        int boxCount = in.nextInt(); // number of boxes
        int robotCount = in.nextInt(); // number of robots
        int robotPerPlayer = in.nextInt(); // number of robots per player
        for (int i = 0; i < tpCount; i++) {
            int tpX = in.nextInt();
            int tpY = in.nextInt();
            int tpGroupId = in.nextInt();
        }

        // game loop
        while (true) {
            for (int i = 0; i < boardHeight; i++) {
                String line = in.next();
            }
            for (int i = 0; i < robotCount; i++) {
                int robotId = in.nextInt();
                int robotX = in.nextInt();
                int robotY = in.nextInt();
                int robotOwner = in.nextInt(); // id of the player who own this robot
                int robotTeam = in.nextInt();
                int robotInit = in.nextInt();
            }
            for (int i = 0; i < boxCount; i++) {
                int boxX = in.nextInt();
                int boxY = in.nextInt();
            }
            for (int i = 0; i < robotPerPlayer; i++) {
                switch (random.nextInt(4)) {
                    case 0:
                        System.out.println("MOVE DOWN");
                        break;
                    case 1:
                        System.out.println("MOVE UP");
                        break;
                    case 2:
                        System.out.println("MOVE LEFT");
                        break;
                    case 3:
                        System.out.println("MOVE RIGHT");
                        break;
                }
            }
        }
    }

}