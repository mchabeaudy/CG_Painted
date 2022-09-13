package com.codingame.game.map.background;

import java.util.List;

public class BackgroundProperty {

    private final Square teamOneScore;
    private final Square teamTwoScore;
    private final List<PlayerProperties> playerProperties;
    private final int width;
    private final int height;
    private final int scoreFontSize;

    public BackgroundProperty(Square teamOneScore,
            Square teamTwoScore,
            List<PlayerProperties> playerProperties,
            int width,
            int height,
            int scoreFontSize) {
        this.teamOneScore = teamOneScore;
        this.teamTwoScore = teamTwoScore;
        this.playerProperties = playerProperties;
        this.width = width;
        this.height = height;
        this.scoreFontSize = scoreFontSize;
    }

    public static BackgroundProperty of(Square teamOneScore,
            Square teamTwoScore,
            List<PlayerProperties> playerProperties,
            int width,
            int height,
            int scoreFontSize) {
        return new BackgroundProperty(teamOneScore, teamTwoScore, playerProperties, width, height, scoreFontSize);
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

    public int getScoreFontSize() {
        return scoreFontSize;
    }
}
