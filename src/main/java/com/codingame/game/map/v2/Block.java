package com.codingame.game.map.v2;

import com.codingame.game.map.Point;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Block {

    private final int width;
    private final int height;
    private final List<Point> walls;
    private final int x;
    private final int y;


}
