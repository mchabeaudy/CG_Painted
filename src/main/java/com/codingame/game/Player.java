package com.codingame.game;

import com.codingame.game.action.Action;
import com.codingame.game.action.InvalidAction;
import com.codingame.gameengine.core.AbstractMultiplayerPlayer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player extends AbstractMultiplayerPlayer {

    @Override
    public int getExpectedOutputLines() {
        return 1;
    }

    public Action getAction() throws TimeoutException, InvalidAction {
        return Action.fromInput(getOutputs().get(0));
    }

}
