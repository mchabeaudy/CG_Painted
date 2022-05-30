package com.codingame.game.map.v2.blocks;

import static java.util.stream.IntStream.range;

import com.codingame.game.map.Point;
import com.codingame.game.map.v2.Block;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Block01 extends Block {

    private static final List<List<Point>> WALLS_LIST = new ArrayList<>();

    static {
        WALLS_LIST.add(Collections.emptyList());
        WALLS_LIST.add(walls1());
        WALLS_LIST.add(walls2());
        WALLS_LIST.add(walls3());
        WALLS_LIST.add(walls4());
        WALLS_LIST.add(walls5());
    }

    private Block01(List<Point> walls, int x, int y) {
        super(4, 3, walls, x, y);
    }


    public static Block01 of(boolean withTp, Random random, int x, int y) {
        List<Point> walls = new ArrayList<>();
        if (withTp) {
            walls.add(Point.of(0, 0));
            walls.add(Point.of(1, 0));
            walls.add(Point.of(2, 0));
            walls.add(Point.of(3, 0));
            range(0, random.nextInt(3))
                    .map(i -> 3 - i)
                    .forEach(i -> walls.add(Point.of(i, 2)));
        } else {
            walls.addAll(WALLS_LIST.get(random.nextInt(WALLS_LIST.size())));
        }
        return new Block01(walls, x, y);
    }

    private static List<Point> walls1() {
        List<Point> walls = new ArrayList<>();
        walls.add(Point.of(1, 1));
        walls.add(Point.of(2, 1));
        walls.add(Point.of(2, 2));
        walls.add(Point.of(3, 2));
        return walls;
    }

    private static List<Point> walls2() {
        List<Point> walls = new ArrayList<>();
        walls.add(Point.of(1, 1));
        walls.add(Point.of(2, 1));
        walls.add(Point.of(2, 2));
        return walls;
    }

    private static List<Point> walls3() {
        List<Point> walls = new ArrayList<>();
        walls.add(Point.of(1, 1));
        walls.add(Point.of(2, 1));
        walls.add(Point.of(2, 0));
        walls.add(Point.of(3, 0));
        return walls;
    }

    private static List<Point> walls4() {
        List<Point> walls = new ArrayList<>();
        walls.add(Point.of(1, 1));
        walls.add(Point.of(2, 1));
        walls.add(Point.of(2, 0));
        walls.add(Point.of(3, 0));
        return walls;
    }

    private static List<Point> walls5() {
        List<Point> walls = new ArrayList<>();
        walls.add(Point.of(0, 1));
        walls.add(Point.of(1, 1));
        walls.add(Point.of(2, 1));
        return walls;
    }

}
