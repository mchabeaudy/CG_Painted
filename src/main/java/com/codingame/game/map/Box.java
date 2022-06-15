package com.codingame.game.map;

public class Box extends Point implements Displayable {

    public Box(int x, int y) {
        super(x, y);
    }

    @Override
    public String toDisplay() {
        return "" + getX() + " " + getY();
    }

}
