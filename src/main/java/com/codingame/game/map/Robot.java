package com.codingame.game.map;

import com.codingame.game.UnitUI;
import com.codingame.game.action.Action;
import com.codingame.game.action.MoveAction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Robot implements Displayable {

    private int id;
    private int team;
    private int playerId;
    private int init;
    private Action action;
    private UnitUI ui;

    public Robot(int id, int team, int playerId, int init) {
        this.id = id;
        this.team = team;
        this.playerId = playerId;
        this.init = init;
    }

    @Override
    public String toDisplay() {
        return "" + id + " " + ui.getX() + " " + ui.getY() + " " + playerId + " " + team + " " + init;
    }

    public void nextInit(int robotCount) {
        init = robotCount == init ? 1 : init + 1;
    }

    public boolean hasTpAction() {
        return action.getMoveAction() == MoveAction.TELEPORT;
    }

    public boolean hasPushAction() {
        return action.getMoveAction() == MoveAction.PUSH;
    }

    public void move()
    {
        getUi().moveUi(action.getDirection());
    }

    public boolean hasPullAction() {
        return action.getMoveAction() == MoveAction.PULL;
    }
}
