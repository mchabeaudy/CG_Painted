package com.codingame.game;

import com.codingame.gameengine.module.entities.Sprite;
import java.util.Random;

public class TileUI {

    private int x;
    private int y;
    private int owner;
    private Sprite sprite;

    public TileUI(int x, int y, Viewer viewer) {
        String color;
        switch (new Random().nextInt(4)) {
            case 1:
                color = "yellow_tile.png";
                break;
            case 2:
                color = "blue_tile.png";
                break;
            case 3:
            default:
                color = "tile.png";
                break;
        }
        sprite = viewer.getGraphics().createSprite()
                .setImage(color)
                .setBaseWidth(viewer.getTileWidth())
                .setBaseHeight(viewer.getTileWidth())
                .setX(x)
                .setY(y);
    }

}
