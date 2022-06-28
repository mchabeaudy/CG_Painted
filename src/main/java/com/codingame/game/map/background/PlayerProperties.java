package com.codingame.game.map.background;

public class PlayerProperties {

    private final Square avatar;
    private final Square name;

    public PlayerProperties(Square avatar, Square name) {
        this.avatar = avatar;
        this.name = name;
    }

    public static PlayerProperties of(Square avatar, Square name) {
        return new PlayerProperties(avatar, name);
    }

    public Square getAvatar() {
        return avatar;
    }

    public Square getName() {
        return name;
    }
}
