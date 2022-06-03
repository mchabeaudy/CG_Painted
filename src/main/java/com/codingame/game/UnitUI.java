package com.codingame.game;

import static com.codingame.game.map.MapElement.TEAM1;
import static com.codingame.game.map.MapElement.TEAM2;

import com.codingame.game.action.Action;
import com.codingame.game.map.MapElement;
import com.codingame.game.map.Point;
import com.codingame.gameengine.module.entities.Sprite;
import java.util.function.UnaryOperator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnitUI extends Point {

    private Sprite sprite;
    private int team;
    private int playerId;
    private int unitId;
    private Viewer viewer;
    private String unitImage;
    private Action action;

    private UnaryOperator<Integer> xConvertor, yConvertor;

    public UnitUI(UnaryOperator<Integer> xConvertor, UnaryOperator<Integer> yConvertor,
            Viewer viewer,
            String unitImage) {
        this.xConvertor = xConvertor;
        this.yConvertor = yConvertor;
        this.viewer = viewer;
        this.unitImage = unitImage;
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
        sprite.setImage(unitImage.substring(0, unitImage.lastIndexOf("-")) + "-t.png");
        viewer.getGraphics().commitEntityState(0, sprite);
        if (viewer.isEmpty(x, y - 1)) {
            setY(y - 1);
        }
    }

    public void moveDown() {
        sprite.setImage(unitImage.substring(0, unitImage.lastIndexOf("-")) + "-b.png");
        viewer.getGraphics().commitEntityState(0, sprite);
        if (viewer.isEmpty(x, y + 1)) {
            setY(y + 1);
        }
    }

    public void moveRight() {
        sprite.setImage(unitImage.substring(0, unitImage.lastIndexOf("-")) + "-r.png");
        viewer.getGraphics().commitEntityState(0, sprite);
        if (viewer.isEmpty(x + 1, y)) {
            setX(x + 1);
        }
    }

    public void moveLeft() {
        sprite.setImage(unitImage.substring(0, unitImage.lastIndexOf("-")) + "-l.png");
        viewer.getGraphics().commitEntityState(0, sprite);
        if (viewer.isEmpty(x - 1, y)) {
            setX(x - 1);
        }
    }

    public MapElement getMapElement() {
        switch (team) {
            case 1:
                return TEAM1;
            case 2:
                return TEAM2;
            default:
                throw new IllegalStateException("team has not been set for this unit");
        }
    }
}