package com.codingame.game.map;

public class Box extends Point implements Displayable {

    @Override
    public String toDisplay() {
        return "" + getX() + " " + getY();
    }

}
