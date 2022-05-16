package com.codingame.game;

import com.codingame.gameengine.module.entities.Sprite;

public class TileUI {

    private int x;
    private int y;
    private int owner;
    private Sprite sprite;

    public TileUI(int x, int y, Viewer viewer, int type) {
        String color;
        color = getColorPng(type);
        sprite = viewer.getGraphics().createSprite()
                .setImage(color)
                .setBaseWidth(viewer.getTileWidth())
                .setBaseHeight(viewer.getTileWidth())
                .setX(x)
                .setY(y);
    }

    private String getColorPng(int type) {
        String color;
        switch (type) {
            case 0:
                color = "grey_tile.png";
                break;
            case 1:
                color = "yellow_tile.png";
                break;
            case 2:
                color = "blue_tile.png";
                break;
            case 3:
                color = "black_tile.png";
                break;
            default:
                throw new IllegalArgumentException("Unknown color : "+type);
        }
        return color;
    }

    public void paint(int type){
        sprite.setImage(getColorPng(type));
    }

}
