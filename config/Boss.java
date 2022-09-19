Boss.javaimport static java.util.stream.IntStream.range;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Player {

    static boolean doTp = true;
    static int playerId;
    static List<Point> tps = new ArrayList<>();
    static List<Robot> robots = new ArrayList<>();

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int playerId = in.nextInt(); // your player id
        Player.playerId = playerId;
        int team = in.nextInt(); // your team id
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
            tps.add(new Point(tpX, tpY));
        }

        // game loop
        while (true) {
            robots.clear();
            for (int i = 0; i < boardHeight; i++) {
                String line = in.next();
            }
            for (int i = 0; i < robotCount; i++) {
                int robotX = in.nextInt();
                int robotY = in.nextInt();
                int robotOwner = in.nextInt(); // id of the player who own this robot
                int robotTeam = in.nextInt();
                int robotInit = in.nextInt();
                int robotId = in.nextInt();
                robots.add(new Robot(robotX, robotY, robotOwner, robotTeam, robotInit, robotId));
            }
            for (int i = 0; i < boxCount; i++) {
                int boxX = in.nextInt();
                int boxY = in.nextInt();
            }
            for (int i = 0; i < robotPerPlayer; i++) {

                // Write an action using System.out.println()
                // To debug: System.err.println("Debug messages...");

                if (doTp) {
                    System.out.println("TAKE");
                } else {
                    String move;
                    switch (new Random().nextInt(4)) {
                        case 0:
                            move = "MOVE UP";
                            break;
                        case 1:
                            move = "MOVE DOWN";
                            break;
                        case 2:
                            move = "MOVE LEFT";
                            break;
                        case 3:
                            move = "MOVE RIGHT";
                            break;
                        default:
                            throw new IllegalStateException();
                    }
                    System.out.println(move);
                }
            }
            doTp = false;
        }
    }

    static class Point {

        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }

    static class Robot {

        int robotX;
        int robotY;
        int robotOwner;
        int robotTeam;
        int robotInit;
        int robotId;

        public Robot(int robotX, int robotY, int robotOwner, int robotTeam, int robotInit,
                int robotId) {
            this.robotX = robotX;
            this.robotY = robotY;
            this.robotOwner = robotOwner;
            this.robotTeam = robotTeam;
            this.robotInit = robotInit;
            this.robotId = robotId;
        }
    }

}