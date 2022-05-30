package com.codingame.game.map.v2.blocks;

import com.codingame.game.map.Point;
import com.codingame.game.map.v2.Block;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Block22 extends Block {
    private static final List<List<Point>> WALLS_LIST = new ArrayList<>();

    static {
        WALLS_LIST.add(Collections.emptyList());
        WALLS_LIST.add(walls1());
        WALLS_LIST.add(walls2());
        WALLS_LIST.add(walls3());
        WALLS_LIST.add(walls4());
    }

    private Block22(List<Point> walls,  int x, int y)
    {
        super(4,3,walls,x,y);
    }


    public Block22 of(Random random, int x, int y)
    {
        return new Block22(WALLS_LIST.get(random.nextInt(WALLS_LIST.size())),x,y);
    }
    private static List<Point> walls1() {
        List<Point> walls = new ArrayList<>();
        walls.add(Point.of(0,0));
        walls.add(Point.of(0,1));
        walls.add(Point.of(0,2));
        walls.add(Point.of(1,1));
        walls.add(Point.of(2,1));
        return walls;
    }
    private static List<Point> walls2() {
        List<Point> walls = new ArrayList<>();
        walls.add(Point.of(2,0));
        walls.add(Point.of(3,0));
        walls.add(Point.of(1,1));
        walls.add(Point.of(2,1));
        return walls;
    }
    private static List<Point> walls3() {
        List<Point> walls = new ArrayList<>();
        walls.add(Point.of(1,1));
        walls.add(Point.of(2,1));
        walls.add(Point.of(2,2));
        walls.add(Point.of(2,3));
        return walls;
    }
    private static List<Point> walls4() {
        List<Point> walls = new ArrayList<>();
        walls.add(Point.of(0,0));
        walls.add(Point.of(0,1));
        walls.add(Point.of(0,2));
        walls.add(Point.of(2,0));
        walls.add(Point.of(3,0));
        walls.add(Point.of(2,2));
        walls.add(Point.of(3,2));
        return walls;
    }
}
