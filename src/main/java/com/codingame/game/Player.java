package com.codingame.game;

import com.codingame.game.action.Action;
import com.codingame.game.action.InvalidAction;
import com.codingame.gameengine.core.AbstractMultiplayerPlayer;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player extends AbstractMultiplayerPlayer {

    private int teamId;
    private int robotCount;

    @Override
    public int getExpectedOutputLines() {
        return robotCount;
    }

    public List<Action> getActions() throws TimeoutException, InvalidAction {
        List<Action> actions = new ArrayList<>();
        for(String input: getOutputs()){
            actions.add(Action.fromInput(input));
        }
        return actions;
    }

}
