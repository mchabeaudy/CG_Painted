package com.codingame.game;

import com.codingame.game.action.Action;
import com.codingame.game.action.InvalidAction;
import com.codingame.gameengine.core.AbstractMultiplayerPlayer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player extends AbstractMultiplayerPlayer {

    private int teamId;

    @Override
    public int getExpectedOutputLines() {
        return 1;
    }

    public List<Action> getAction() throws TimeoutException, InvalidAction {
        List<Action> actions = new ArrayList<>();
        for(String input: getOutputs()){
            actions.add(Action.fromInput(input));
        }
        return actions;
    }

}
