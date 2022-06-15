package com.codingame.game.map.block.blocks;

import com.codingame.game.map.Point;
import com.codingame.game.map.block.Block;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StartBlock extends Block {


    private StartBlock(List<Point> walls, int x, int y) {
        super(5, 5, walls, x, y);
    }

    public static StartBlock of(Random random, int x, int y) {
        List<Point> walls = new ArrayList<>();
        if (random.nextBoolean()) {
            walls.add(Point.of(4, 0));
            walls.add(Point.of(4, 1));
            walls.add(Point.of(4, 2));
            walls.add(Point.of(4, 3));
            walls.add(Point.of(4, 4));
        }
        return new StartBlock(walls, x, y);
    }

}
