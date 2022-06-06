import static java.util.stream.IntStream.range;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Random;

class Player {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String color = in.next();

        while (true) {
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