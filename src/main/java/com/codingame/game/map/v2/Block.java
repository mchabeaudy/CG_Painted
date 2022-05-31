package com.codingame.game.map.v2;

import com.codingame.game.map.Point;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Block {

    private final int width;
    private final int height;
    private final List<Point> walls;
    private final int x;
    private final int y;

    public Block verticalFlip(int mapWidth) {
        int newX = mapWidth - x - width;
        List<Point> newWalls = walls.stream()
                .map(p -> Point.of(width - p.getX() - 1, p.getY()))
                .collect(Collectors.toList());
        return new Block(width, height, newWalls, newX, y);
    }

    public Block horizontalFlip(int mapHeight) {
        int newY = mapHeight - y - height;
        List<Point> newWalls = walls.stream()
                .map(p -> Point.of(p.getX(), height - p.getY() - 1))
                .collect(Collectors.toList());
        return new Block(width, height, newWalls, x, newY);
    }

    public List<Point> getWalls() {
        return walls;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
