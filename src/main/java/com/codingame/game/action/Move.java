package com.codingame.game.action;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public enum Move {
    UP,
    DOWN,
    RIGHT,
    LEFT;

    public static Optional<Move> fromString(String move)
    {
        return Arrays.stream(values())
                .filter(m-> Objects.equals(m.name(), move))
                .findAny();
    }
}
