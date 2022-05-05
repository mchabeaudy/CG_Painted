package com.codingame.game;

import com.codingame.game.map.GameMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Board {

    private int width;
    private int height;

    private GameMap gameMap;
}
