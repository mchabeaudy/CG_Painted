package com.codingame.game.map;

import com.codingame.game.UnitUI;
import com.codingame.game.action.Action;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Unit implements Displayable {

    private int id;
    private int team;
    private int playerId;
    private int init;
    private Action action;
    private UnitUI ui;

    @Override
    public String toDisplay() {
        return "" + ui.getX() + " " + ui.getY() + " " + playerId + " " + team + " " + init;
    }

}
