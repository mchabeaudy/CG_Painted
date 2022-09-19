package com.codingame.game.map;

import com.codingame.game.UnitUI;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Box extends Point implements Displayable {

    private UnitUI unitUI;

    public Box(int x, int y) {
        super(x, y);
    }

    @Override
    public String toDisplay() {
        return "" + getX() + " " + getY();
    }

}
