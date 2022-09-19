package com.codingame.game.action;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public enum MoveAction {
    MOVE,
    WAIT,
    PUSH,
    PULL,
    TELEPORT;

    public static Optional<MoveAction> fromString(String move)
    {
        return Arrays.stream(values())
                .filter(m-> Objects.equals(m.name(), move))
                .findAny();
    }
}
