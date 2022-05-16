package com.codingame.game.map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Unit extends Point {

    private int id;
    private int playerId;
    private int teamId;

    @Override
    public boolean equals(Object o) {
        return o instanceof Unit && ((Unit) o).id == id;
    }
}
