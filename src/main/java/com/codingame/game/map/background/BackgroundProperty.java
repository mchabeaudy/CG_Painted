package com.codingame.game.map.background;

import java.util.List;

public class BackgroundProperty {

    private final Square teamOneScore;
    private final Square teamTwoScore;
    private final List<PlayerProperties> playerProperties;
    private final int width;
    private final int height;

    public BackgroundProperty(Square teamOneScore,
        Square teamTwoScore,
        List<PlayerProperties> playerProperties,
        int width,
        int height) {
        this.teamOneScore = teamOneScore;
        this.teamTwoScore = teamTwoScore;
        this.playerProperties = playerProperties;
        this.width = width;
        this.height = height;
    }

    public static BackgroundProperty of(Square teamOneScore,
        Square teamTwoScore,
        List<PlayerProperties> playerProperties,
        int width,
        int height) {
        return new BackgroundProperty(teamOneScore, teamTwoScore, playerProperties, width, height);
    }

    public Square getTeamOneScore() {
        return teamOneScore;
    }

    public Square getTeamTwoScore() {
        return teamTwoScore;
    }

    public List<PlayerProperties> getPlayerProperties() {
        return playerProperties;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
