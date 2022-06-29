package com.codingame.game.map.background;

import com.codingame.game.map.Point;

public class Square {

    private final int xMin;
    private final int yMin;
    private final int xMax;
    private final int yMax;
    private final int width;
    private final int height;

    public Square(Point upLeft, Point downRight)
    {
        xMin = upLeft.getX();
        yMin = upLeft.getY();
        xMax = downRight.getX();
        yMax = downRight.getY();
        width = xMax - xMin;
        height = yMax - yMin;
    }

    public int getXMin() {
        return xMin;
    }

    public int getYMin() {
        return yMin;
    }

    public int getXMax() {
        return xMax;
    }

    public int getYMax() {
        return yMax;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public static Square of(Point upLeft, Point downRight)
    {
        return new Square(upLeft, downRight);
    }
}
