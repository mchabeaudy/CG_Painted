package com.codingame.game.map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Point {

    protected int x, y;

    public static Point of(int x, int y) {
        return new Point(x, y);
    }

    public boolean hasSameCoordinates(int x, int y) {
        return this.x == x && this.y == y;
    }

    public boolean hasSameCoordinates(Point p) {
        return hasSameCoordinates(p.x, p.y);
    }

}
