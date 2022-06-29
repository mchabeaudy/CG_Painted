package com.codingame.game;

import com.codingame.game.map.Point;
import com.codingame.game.map.background.BackgroundProperty;
import com.codingame.game.map.background.PlayerProperties;
import com.codingame.game.map.background.Square;
import java.util.ArrayList;

public final class Constants {

    public static final String ROBOT1A = "robot-01-t.png";
    public static final String ROBOT1B = "robot-02-t.png";
    public static final String ROBOT2A = "robot-08-t.png";
    public static final String ROBOT2B = "robot-09-t.png";
    public static final String TELEPORT1 = "tp-01.png";
    public static final String TELEPORT2 = "tp-02.png";
    public static final String BOX = "box.png";

    public static final int MAP_WIDTH_LEVEL1 = 15;
    public static final int MAP_HEIGHT_LEVEL1 = 15;
    public static final int MAP_WIDTH_LEVEL234 = 24;
    public static final int MAP_HEIGHT_LEVEL234 = 19;
    public static final int AVATAR_BACKGROUND = 0x737373;


    public static final BackgroundProperty BG_1 = BackgroundProperty.of(
        Square.of(Point.of(186, 1908), Point.of(916, 2416)),
        Square.of(Point.of(4220, 1908), Point.of(5153, 2416)),
        new ArrayList<>(),
        5334,
        3000
    );

    public static final BackgroundProperty BG_2 = BackgroundProperty.of(
        Square.of(Point.of(114, 1386), Point.of(565, 1633)),
        Square.of(Point.of(3537, 1386), Point.of(3888, 1633)),
        new ArrayList<>(),
        4000,
        2250
    );

    public static final BackgroundProperty BG_3 = BackgroundProperty.of(
        Square.of(Point.of(113, 1056), Point.of(465, 1305)),
        Square.of(Point.of(3537, 1056), Point.of(3888, 1305)),
        new ArrayList<>(),
        4000,
        2250
    );

    static {
        BG_1.getPlayerProperties().add(PlayerProperties.of(
            Square.of(Point.of(293, 1123), Point.of(800, 1631)),
            Square.of(Point.of(0, 662), Point.of(1100, 906))));
        BG_1.getPlayerProperties().add(PlayerProperties.of(
            Square.of(Point.of(4544, 1123), Point.of(5051, 1631)),
            Square.of(Point.of(4240, 662), Point.of(5334, 906))));


        BG_2.getPlayerProperties().add(PlayerProperties.of(
            Square.of(Point.of(132, 898), Point.of(440, 1205)),
            Square.of(Point.of(0, 536), Point.of(564, 719))));
        BG_2.getPlayerProperties().add(PlayerProperties.of(
            Square.of(Point.of(3558, 898), Point.of(3866, 1205)),
            Square.of(Point.of(3430, 536), Point.of(4000, 719))));


        BG_3.getPlayerProperties().add(PlayerProperties.of(
            Square.of(Point.of(166, 713), Point.of(403, 950)),
            Square.of(Point.of(0, 420), Point.of(564, 602))));
        BG_3.getPlayerProperties().add(PlayerProperties.of(
            Square.of(Point.of(166, 1407), Point.of(403, 1644)),
            Square.of(Point.of(0, 1759), Point.of(564, 1943))));
        BG_3.getPlayerProperties().add(PlayerProperties.of(
            Square.of(Point.of(3596, 713), Point.of(3833, 950)),
            Square.of(Point.of(3429, 420), Point.of(4000, 602))));
        BG_3.getPlayerProperties().add(PlayerProperties.of(
            Square.of(Point.of(3596, 1407), Point.of(3833, 1644)),
            Square.of(Point.of(3429, 1759), Point.of(4000, 1943))));
    }

    private Constants() {

    }
}
