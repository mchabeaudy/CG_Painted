package com.codingame.game.map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Teleport extends Point implements Displayable {

    private int groupId;

    public Teleport(int x, int y, int groupId) {
        super(x, y);
        this.groupId = groupId;
    }

    @Override
    public String toDisplay() {
        return "" + getX() + " " + getY() + " " + getGroupId();
    }
}
