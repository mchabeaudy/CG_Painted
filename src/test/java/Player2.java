import static java.util.stream.IntStream.range;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Player2 {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        Player2 p = new Player2();
        p.playerId = in.nextInt(); // your player id (0 to 3)
        p.teamId = in.nextInt(); // your team id (1 or 2)
        p.boardWidth = in.nextInt(); // width of the board (15 in level 1, 24 in other levels)
        p.boardHeight = in.nextInt(); // height of the board (15 in level 1, 19 in other levels)
        p.tpCount = in.nextInt(); // number of teleport (0 to 4. 0 in this level)
        p.boxCount = in.nextInt(); // number of boxes (0 to 4. 0 in this level)
        p.robotCount = in.nextInt(); // number of robots (2 to 8. 2 in this level)
        p.robotPerPlayer = in.nextInt(); // number of robots per player (1 or 2. 1 in this level)
        for (int i = 0; i < p.tpCount; i++) {
            int tpX = in.nextInt();
            int tpY = in.nextInt();
            int tpGroupId = in.nextInt();
            p.addTp(tpX, tpY, tpGroupId);
        }

        // game loop
        while (true) {
            p.refresh();
            for (int y = 0; y < p.boardHeight; y++) {
                String line = in.next(); // boardHeight characters (. for empty, 1 or 2 for painted square)
                // System.err.println(line);
                p.readLine(line, y);
            }
            for (int i = 0; i < p.robotCount; i++) {
                int robotId = in.nextInt(); // robot id (1 to 8)
                int robotX = in.nextInt(); // x robot position
                int robotY = in.nextInt(); // y robot position
                int robotOwner = in.nextInt(); // id of the player who own this robot (0 to 3)
                int robotTeam = in.nextInt(); // team id of the robot (1 or 2)
                int robotInit = in.nextInt(); // robot initiative (1 to 8. used in case of conflict)
                p.addRobot(robotX, robotY, robotId, robotOwner, robotTeam, robotInit);
            }
            for (int i = 0; i < p.boxCount; i++) {
                int boxX = in.nextInt();
                int boxY = in.nextInt();
                p.addBox(boxX, boxY);
            }
            String[] instructions = p.evaluateInstructions();
            for (int i = 0; i < p.robotPerPlayer; i++) {
                // Write an action using System.out.println()
                // To debug: System.err.println("Debug messages...");

                System.out.println(instructions[i]);
            }
        }
    }

    // inputs
    int playerId;
    int teamId;
    int boardWidth;
    int boardHeight;
    int tpCount;
    int boxCount;
    int robotCount;
    int robotPerPlayer;

    Square[][] squares;
    List<Square> squaresList = new ArrayList<>();
    List<Robot> robots = new ArrayList<>();
    List<Robot> myRobots = new ArrayList<>();


    void refresh() {
        squares = new Square[boardHeight][boardWidth];
        squaresList.clear();
        robots.clear();
        myRobots.clear();
    }

    void readLine(String line, int y) {
        range(0, line.length()).forEach(x -> {
            squares[y][x] = new Square(x, y, line.charAt(x));
            squaresList.add(squares[y][x]);
        });
    }

    void addTp(int x, int y, int groupId) {
    }

    void addRobot(int robotX, int robotY, int robotId, int robotOwner, int robotTeam, int robotInit) {
        Robot r = new Robot(robotX, robotY, robotId, robotOwner, robotTeam, robotInit);
        robots.add(r);
        if (robotOwner == playerId) {
            myRobots.add(r);
        }
    }

    void addBox(int boxX, int boxY) {
    }

    String[] evaluateInstructions() {
        String[] instructions = new String[robotPerPlayer];
        range(0,robotPerPlayer).forEach(i->{
            Robot r = myRobots.get(i);
            Square robotSquare = squares[r.robotY][r.robotX];
            Square nearestEmptySquare = squaresList.stream()
                    .filter(s->s.c=='.' || s.c==(teamId==1?'2':'1'))
                    .min(Comparator.comparingInt(robotSquare::manhattanDist))
                    .get();
            List<Square> path = path(robotSquare,nearestEmptySquare);
            if(path.size()==1){
                instructions[i] = "WAIT";
            }else{
                instructions[i] = "MOVE " +path.get(1).x+" "+path.get(1).y;
            }
        });
        return instructions;
    }

    List<Square> path(Square from, Square to) {
        Square[][] copy = new Square[boardHeight][boardWidth];
        range(0, boardHeight).forEach(y -> range(0, boardWidth).forEach(x -> copy[y][x] = squares[y][x].copy()));
        Square fromCopy = copy[from.y][from.x];
        Square toCopy = copy[to.y][to.x];

        LinkedList<Square> initialPath = new LinkedList<>();
        initialPath.add(fromCopy);
        fromCopy.visited = true;

        List<LinkedList<Square>> paths = new ArrayList<>();
        paths.add(initialPath);
        while (!toCopy.visited) {
            List<LinkedList<Square>> newPaths = new ArrayList<>();
            for (LinkedList<Square> path : paths) {
                Square last = path.getLast();
                last.neighbours(copy).stream()
                        .filter(Square::canBeAddedToNewPath)
                        .map(square -> createNewPath(path, square))
                        .forEach(newPaths::add);
            }
            if (newPaths.isEmpty()) {
                throw new IllegalStateException("cannot find path");
            }
            paths = newPaths;
        }

        return paths.stream().filter(p -> p.getLast() == toCopy).findAny().get();
    }

    LinkedList<Square> createNewPath(LinkedList<Square> path, Square square) {
        square.setVisited(true);
        LinkedList<Square> newPath = new LinkedList<>(path);
        newPath.add(square);
        return newPath;
    }

    class Square {

        int x;
        int y;
        char c;
        boolean visited; // used for pathFinding

        Square(int x, int y, char c) {
            this.x = x;
            this.y = y;
            this.c = c;
            visited = false;
        }

        List<Square> neighbours(Square[][] map) {
            List<Square> neighbours = new ArrayList<>();
            if (isInMap(x - 1, y)) {
                neighbours.add(map[y][x - 1]);
            }
            if (isInMap(x + 1, y)) {
                neighbours.add(map[y][x + 1]);
            }
            if (isInMap(x, y - 1)) {
                neighbours.add(map[y - 1][x]);
            }
            if (isInMap(x, y + 1)) {
                neighbours.add(map[y + 1][x]);
            }
            return neighbours;
        }

        boolean canBeAddedToNewPath() {
            return !visited && (c == '.' || c == '1' || c == '2');
        }

        Square copy() {
            return new Square(x, y, c);
        }

        void setVisited(boolean visited) {
            this.visited = visited;
        }

        int manhattanDist(Square s)
        {
            return Math.abs(s.x-x)+Math.abs(s.y-y);
        }
    }

    boolean isInMap(int x, int y) {
        return 0 <= x && 0 <= y && x < boardWidth && y < boardHeight;
    }

    class Robot {

        int robotX;
        int robotY;
        int robotId;
        int robotOwner;
        int robotTeam;
        int robotInit;

        public Robot(int robotX, int robotY, int robotId, int robotOwner, int robotTeam, int robotInit) {
            this.robotX = robotX;
            this.robotY = robotY;
            this.robotId = robotId;
            this.robotOwner = robotOwner;
            this.robotTeam = robotTeam;
            this.robotInit = robotInit;
        }
    }

}