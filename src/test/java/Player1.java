import java.util.Random;
import java.util.Scanner;

public class Player1 {


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int playerId = in.nextInt();
        int team = in.nextInt();
        int boardWidth = in.nextInt();
        int boardHeight = in.nextInt();
        int tpCount = in.nextInt();
        int boxCount = in.nextInt();
        int robotCount = in.nextInt();
        for (int k = 0; k < tpCount; k++) {
            int tpX = in.nextInt();
            int tpY = in.nextInt();
            int tpGroupId = in.nextInt();
        }

        while (true) {
//            for (int i = /

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

}