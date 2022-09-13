package com.codingame.game.map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MapElement {
    WALL("W", "wall.png"),
    NEUTRAL("E", "ground-tile.png"),
    TEAM1("1", "green-tile.png"),
    TEAM2("2", "magenta-tile.jpg");
    private final String display;
    private final String image;
}
