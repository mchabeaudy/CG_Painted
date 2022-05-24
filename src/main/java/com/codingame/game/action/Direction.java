package com.codingame.game.action;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public enum Direction {
    NONE,
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public static Optional<Direction> fromString(String move)
    {
        return Arrays.stream(values())
                .filter(m-> Objects.equals(m.name(), move))
                .findAny();
    }
}
