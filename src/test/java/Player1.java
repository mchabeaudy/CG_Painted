import java.util.Random;
import java.util.Scanner;

public class Player1 {


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String color = in.next();

        while (true) {
            int unitCount = in.nextInt();
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