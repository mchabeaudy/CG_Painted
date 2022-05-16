package com.codingame.game;

import com.codingame.gameengine.module.entities.Sprite;
import java.util.function.Function;
import lombok.Getter;

@Getter
public class UnitUI {

    private int x, y;
    private Sprite sprite;
    private int team = 0;
    private Viewer viewer;

    private Function<Integer, Integer> xConvertor, yConvertor;

    public UnitUI(Function<Integer, Integer> xConvertor, Function<Integer, Integer> yConvertor, Viewer viewer,
            String unitImage) {
        this.xConvertor = xConvertor;
        this.yConvertor = yConvertor;
        this.viewer = viewer;
        sprite = viewer.getGraphics().createSprite()
                .setImage(unitImage)
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
        if (y - 1 >= 0) {
            int b = viewer.getBoard().getGameMap().getBoxes()[y - 1][x];
            if (b == 0 || b == 1 || b == 2) {
                setY(y - 1);
            }
        }
    }

    public void moveDown() {
        if (y + 1 < 25) {
            int b = viewer.getBoard().getGameMap().getBoxes()[y + 1][x];
            if (b == 0 || b == 1 || b == 2) {
                setY(y + 1);
            }
        }
    }

    public void moveRight() {
        if (x + 1 < 25) {
            int b = viewer.getBoard().getGameMap().getBoxes()[y][x + 1];
            if (b == 0 || b == 1 || b == 2) {
                setX(x + 1);
            }
        }
    }

    public void moveLeft() {
        if (x - 1 >= 0) {
            int b = viewer.getBoard().getGameMap().getBoxes()[y][x - 1];
            if (b == 0 || b == 1 || b == 2) {
                setX(x - 1);
            }
        }
    }


}
