package com.codingame.game;

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

    public String getAction() throws TimeoutException {
        return getOutputs().get(0);
    }

}
