package com.codingame.game.map;

import com.codingame.game.action.Direction;
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

    public Point(Point point) {
        this(point.x, point.y);
    }

    public static Point of(int x, int y) {
        return new Point(x, y);
    }

    public boolean hasSameCoordinates(int x, int y) {
        return this.x == x && this.y == y;
    }

    public boolean hasSameCoordinates(Point p) {
        return hasSameCoordinates(p.x, p.y);
    }

    public void move(Direction direction) {
        switch (direction) {
            case UP:
                y -= 1;
                break;
            case DOWN:
                y += 1;
                break;
            case LEFT:
                x -= 1;
                break;
            case RIGHT:
                x += 1;
                break;
        }
    }
}
