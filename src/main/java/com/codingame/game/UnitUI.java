package com.codingame.game;

import com.codingame.gameengine.module.entities.Sprite;
import java.util.function.Function;

public class UnitUI {

    private int x, y;
    private Sprite sprite;
    private int team = 0;

    private Function<Integer, Integer> xConvertor, yConvertor;

    public UnitUI(Function<Integer, Integer> xConvertor, Function<Integer, Integer> yConvertor, Viewer viewer) {
        this.xConvertor = xConvertor;
        this.yConvertor = yConvertor;
        sprite = viewer.getGraphics().createSprite()
                .setImage("test2.png")
                .setBaseWidth(viewer.getTileWidth() * 9 / 10)
                .setBaseHeight(viewer.getTileWidth() * 9 / 10);
    }

    public void setX(int x) {
        this.x = x;
        sprite.setX(xConvertor.apply(x));
    }

    public void setY(int y) {
        this.y = y;
        sprite.setY(yConvertor.apply(y));
    }

    public void moveUp() {
        setY(y + 1);
    }

    public void moveDown() {
        setY(y - 1);
    }

    public void moveRight() {
        setX(x + 1);
    }

    public void moveLeft() {
        setX(x - 1);
    }


}
