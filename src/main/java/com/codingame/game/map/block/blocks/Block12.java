package com.codingame.game.map.block.blocks;

import com.codingame.game.map.Point;
import com.codingame.game.map.block.Block;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Block12 extends Block {

    private static final List<List<Point>> WALLS_LIST = new ArrayList<>();

    static {
        WALLS_LIST.add(Collections.emptyList());
        WALLS_LIST.add(walls1());
        WALLS_LIST.add(walls2());
        WALLS_LIST.add(walls3());
    }

    private Block12(List<Point> walls, int x, int y) {
        super(3, 5, walls, x, y);
    }

    public static Block12 of(Random random, int x, int y) {
        return new Block12(WALLS_LIST.get(random.nextInt(WALLS_LIST.size())), x, y);
    }

    private static List<Point> walls1() {
        List<Point> walls = new ArrayList<>();
        walls.add(Point.of(1, 0));
        walls.add(Point.of(2, 0));
        walls.add(Point.of(2, 1));
        walls.add(Point.of(0, 2));
        walls.add(Point.of(2, 3));
        walls.add(Point.of(2, 4));
        walls.add(Point.of(1, 4));
        return walls;
    }

    private static List<Point> walls2() {
        List<Point> walls = new ArrayList<>();
        walls.add(Point.of(2, 0));
        walls.add(Point.of(0, 1));
        walls.add(Point.of(2, 1));
        walls.add(Point.of(0, 2));
        walls.add(Point.of(0, 3));
        walls.add(Point.of(2, 3));
        walls.add(Point.of(2, 4));
        return walls;
    }

    private static List<Point> walls3() {
        List<Point> walls = new ArrayList<>();
        walls.add(Point.of(1, 1));
        walls.add(Point.of(1, 2));
        walls.add(Point.of(1, 3));
        walls.add(Point.of(0, 2));
        return walls;
    }
}
