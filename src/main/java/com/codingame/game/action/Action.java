package com.codingame.game.action;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class Action {

    private final Move move;

    public static Action fromInput(String input) throws InvalidAction {
        Move move = Move.fromString(input)
                .orElseThrow(() -> new InvalidAction("Unknown action : " + input));
        return new ActionBuilder()
                .move(move)
                .build();
    }

}
