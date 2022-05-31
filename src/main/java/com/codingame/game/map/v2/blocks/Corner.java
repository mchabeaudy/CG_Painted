package com.codingame.game.map.v2.blocks;

import com.codingame.game.map.Point;
import com.codingame.game.map.v2.Block;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Corner extends Block {

    private static final List<List<Point>> WALLS_LIST = new ArrayList<>();

    static {
        WALLS_LIST.add(Collections.emptyList());
        WALLS_LIST.add(walls1());
        WALLS_LIST.add(walls2());
        WALLS_LIST.add(walls3());
        WALLS_LIST.add(walls4());
        WALLS_LIST.add(walls5());
        WALLS_LIST.add(walls6());
    }

    public static Corner of(boolean withTp, Random random, int x, int y) {
        List<Point> walls = new ArrayList<>();
        if (!withTp) {
            walls.addAll(WALLS_LIST.get(random.nextInt(WALLS_LIST.size())));
        }
        return new Corner(walls, x, y);
    }

    private Corner(List<Point> walls, int x, int y) {
        super(4, 4, walls, x, y);
    }

    private static List<Point> walls1() {
        List<Point> walls = new ArrayList<>();
        walls.add(Point.of(1, 1));
        walls.add(Point.of(1, 2));
        walls.add(Point.of(2, 2));
        walls.add(Point.of(3, 2));
        walls.add(Point.of(3, 3));
        return walls;
    }

    private static List<Point> walls2() {
        List<Point> walls = new ArrayList<>();
        walls.add(Point.of(1, 1));
        walls.add(Point.of(2, 1));
        walls.add(Point.of(2, 2));
        walls.add(Point.of(2, 3));
        walls.add(Point.of(3, 3));
        return walls;
    }

    private static List<Point> walls3() {
        List<Point> walls = new ArrayList<>();
        walls.add(Point.of(2, 3));
        walls.add(Point.of(3, 2));
        walls.add(Point.of(3, 3));
        return walls;
    }

    private static List<Point> walls4() {
        List<Point> walls = new ArrayList<>();
        walls.add(Point.of(1, 1));
        walls.add(Point.of(2, 1));
        walls.add(Point.of(2, 2));
        walls.add(Point.of(3, 2));
        walls.add(Point.of(3, 3));
        return walls;
    }

    private static List<Point> walls5() {
        List<Point> walls = new ArrayList<>();
        walls.add(Point.of(2, 1));
        walls.add(Point.of(1, 2));
        walls.add(Point.of(2, 2));
        walls.add(Point.of(3, 2));
        walls.add(Point.of(3, 3));
        return walls;
    }

    private static List<Point> walls6() {
        List<Point> walls = new ArrayList<>();
        walls.add(Point.of(1, 1));
        walls.add(Point.of(1, 2));
        walls.add(Point.of(2, 2));
        walls.add(Point.of(2, 3));
        walls.add(Point.of(3, 3));
        return walls;
    }
}
