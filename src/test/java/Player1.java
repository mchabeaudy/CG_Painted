import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Player1 {

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
            List<Robot> robots = new ArrayList<>();
            List<Robot> myRobots = new ArrayList<>();
            List<Box> boxes = new ArrayList<>();
            for (int i = 0; i < boardHeight; i++) {
                String line = in.next();
                System.err.println(line);
            }
            for (int i = 0; i < robotCount; i++) {
                int robotId = in.nextInt();
                int robotX = in.nextInt();
                int robotY = in.nextInt();
                int robotOwner = in.nextInt(); // id of the player who own this robot
                int robotTeam = in.nextInt();
                int robotInit = in.nextInt();
                Robot robot = new Robot(robotX, robotY, robotId, robotOwner, robotTeam, robotInit);
                robots.add(robot);
                if(robotOwner==playerId){
                    myRobots.add(robot);
                }
            }
            for (int i = 0; i < boxCount; i++) {
                int boxX = in.nextInt();
                int boxY = in.nextInt();
                boxes.add(new Box(boxX, boxY));
            }
            for (int i = 0; i < robotPerPlayer; i++) {

                // Write an action using System.out.println()
                // To debug: System.err.println("Debug messages...");
                Robot robot = myRobots.get(i);
                Box box = boxes.stream().filter(b -> b.dist2(robot) == 1).findAny().orElse(null);
                if (Objects.nonNull(box)) {
                    if(random.nextBoolean()){
                        System.out.println("PUSH UP");
                    }else{
                        System.out.println("PULL DOWN");
                    }
                    System.err.println("Robot : "+robot.x+" "+robot.y+"  box : "+box.x+" "+box.y);
                } else {
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

    public static class Point {

        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int dist2(Point p) {
            return (p.x - x) * (p.x - x) + (p.y - y) * (p.y - y);
        }
    }

    public static class Robot extends Point {

        int robotId;
        int robotOwner;
        int robotTeam;
        int robotInit;

        public Robot(int x, int y, int robotId, int robotOwner, int robotTeam, int robotInit) {
            super(x, y);
            this.robotId = robotId;
            this.robotOwner = robotOwner;
            this.robotTeam = robotTeam;
            this.robotInit = robotInit;
        }

        public int getRobotId() {
            return robotId;
        }

        public void setRobotId(int robotId) {
            this.robotId = robotId;
        }

        public int getRobotOwner() {
            return robotOwner;
        }

        public void setRobotOwner(int robotOwner) {
            this.robotOwner = robotOwner;
        }

        public int getRobotTeam() {
            return robotTeam;
        }

        public void setRobotTeam(int robotTeam) {
            this.robotTeam = robotTeam;
        }

        public int getRobotInit() {
            return robotInit;
        }

        public void setRobotInit(int robotInit) {
            this.robotInit = robotInit;
        }
    }

    public static class Box extends Point {

        public Box(int x, int y) {
            super(x, y);
        }
    }
}