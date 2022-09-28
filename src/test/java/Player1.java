import static java.util.stream.IntStream.range;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.stream.Collectors;

public class Player1 {

    private static Random random = new Random();

    static List<Square> squares;

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
        List<Point> tps = new ArrayList<>();
        for (int i = 0; i < tpCount; i++) {
            int tpX = in.nextInt();
            int tpY = in.nextInt();
            tps.add(new Point(tpX, tpY));
            int tpGroupId = in.nextInt();
        }

        // game loop
        while (true) {
            List<Robot> robots = new ArrayList<>();
            List<Robot> myRobots = new ArrayList<>();
            List<Box> boxes = new ArrayList<>();
            squares = new ArrayList<>();
            for (int i = 0; i < boardHeight; i++) {
                int y = i;
                String line = in.next();
                range(0, line.length()).forEach(x -> squares.add(new Square(x, y, line.charAt(x))));
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
                if (robotOwner == playerId) {
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

//                Square nextEmpty =
//                        toNextEmpty(robot, robots.stream().filter(r -> r.getRobotId() != robot.getRobotId())
//                                .collect(Collectors.toList()));
//                if (nextEmpty == null) {
//                    System.out.println("WAIT");
//                } else if (nextEmpty.x > robot.x) {
//                    System.out.println("MOVE RIGHT");
//                } else if (nextEmpty.x < robot.x) {
//                    System.out.println("MOVE LEFT");
//                } else if (nextEmpty.y > robot.y) {
//                    System.out.println("MOVE DOWN");
//                } else {
//                    System.out.println("MOVE UP");
//                }
                Square next = toNextEmpty2(robot, teamId,robots.stream().filter(r -> r.getRobotId() != robot.getRobotId())
                                .collect(Collectors.toList()), boxes);
                System.out.println("MOVE " + next.x + ' ' + next.y);

            }
        }
    }

    static Square toNextEmpty2(Robot robot, int teamId, List<Robot> others, List<Box> boxes) {
        return squares.stream().filter(s -> s.isDifferentFrom('x') && s.isDifferentFrom(teamId) && others.stream()
                        .noneMatch(s::hasSameCoordinate) && boxes.stream().noneMatch(s::hasSameCoordinate))
                .min(Comparator.comparingInt(robot::dist2))
                .get();
    }

    static Square toNextEmpty(Robot robot, List<Robot> others) {
        List<Square> covered = new ArrayList<>();
        Square robotSquare = squares.stream().filter(s -> s.dist2(robot) == 0).findAny()
                .orElseThrow(() -> new IllegalStateException("problem to find square"));
        Stack<Square> initialPath = new Stack<>();
        initialPath.add(robotSquare);
        List<Stack<Square>> paths = new ArrayList<>();
        paths.add(initialPath);
        while (true) {

            int cSize = covered.size();
            List<Stack<Square>> newPaths = new ArrayList<>();
            for (Stack<Square> p : paths) {
                Square last = p.peek();
                List<Square> newSquares = squares.stream()
                        .filter(s -> last.dist2(s) == 1 && !covered.contains(s) && s.isDifferentFrom('x') && others
                                .stream().noneMatch(s::hasSameCoordinate))
                        .collect(Collectors.toList());
                covered.addAll(newSquares);
                if (newSquares.isEmpty()) {
                    newPaths.add(p);
                } else {
                    newSquares.stream().map(s -> {
                                Stack<Square> stack = new Stack<>();
                                stack.addAll(p);
                                stack.add(s);
                                return stack;
                            })
                            .forEach(newPaths::add);
                }
            }
            paths = newPaths;
            System.err.println("paths size : " + paths.size());
            paths.forEach(p -> System.err.println(
                    p.stream().map(s -> "" + s.x + " " + s.y).collect(Collectors.joining(", "))));
            if (cSize == covered.size() || covered.stream().anyMatch(c -> c.isDifferentFrom(robot.getRobotTeam()))) {
                break;
            }
        }

        return paths.stream().filter(p -> p.stream().anyMatch(s -> s.isDifferentFrom(robot.getRobotTeam())))
                .findAny()
                .map(p -> p.get(1))
                .orElse(null);
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

        public boolean hasSameCoordinate(Point point) {
            return point.x == x && point.y == y;
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

    public static class Square extends Point {

        public char type;

        public Square(int x, int y, char type) {
            super(x, y);
            this.type = type;
        }

        public char getType() {
            return type;
        }

        public void setType(char type) {
            this.type = type;
        }

        boolean isDifferentFrom(char type) {
            return this.type != type;
        }

        boolean isDifferentFrom(int type) {
            return this.type != String.valueOf(type).charAt(0);
        }

    }
}