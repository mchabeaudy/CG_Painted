package com.codingame.game;

import static com.codingame.game.Constants.BOX;
import static com.codingame.game.map.MapElement.TEAM1;
import static com.codingame.game.map.MapElement.TEAM2;

import com.codingame.game.action.Action;
import com.codingame.game.action.Direction;
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

    public UnitUI(Viewer viewer, String unitImage, int x, int y) {
        this(viewer, unitImage, x, y, 0);
    }

    public UnitUI(Viewer viewer, String unitImage, int x, int y, int team) {
        this.xConvertor = viewer.getXConvertor();
        this.yConvertor = viewer.getYConvertor();
        this.viewer = viewer;
        this.team = team;
        this.unitImage = unitImage;
        sprite = viewer.getGraphics().createSprite()
                .setImage(unitImage)
                .setBaseWidth(viewer.getTileWidth() * 9 / 10)
                .setBaseHeight(viewer.getTileWidth() * 9 / 10);
        setX(x);
        setY(y);
    }

    public void setX(int x) {
        this.x = x;
        sprite.setX(xConvertor.apply(x));
    }

    public void setY(int y) {
        this.y = y;
        sprite.setY(yConvertor.apply(y));
    }

    public void forceMoveUi(Direction direction) {
        moveUi(direction, true);
    }

    private void moveUi(Direction direction, boolean forceMove) {
        switch (direction) {
            case UP:
                moveUp(forceMove);
                break;
            case DOWN:
                moveDown(forceMove);
                break;
            case LEFT:
                moveLeft(forceMove);
                break;
            case RIGHT:
                moveRight(forceMove);
                break;
            default:
                throw new IllegalArgumentException("Unknown direction");
        }
    }

    public void moveUi(Direction direction) {
        moveUi(direction, false);
    }

    private void moveUp(boolean forceMove) {
        if (!unitImage.equals(BOX)) {
            sprite.setImage(unitImage.substring(0, unitImage.lastIndexOf("-")) + "-t.png");
        }
        viewer.getGraphics().commitEntityState(0, sprite);
        if (viewer.isEmpty(x, y - 1) || forceMove) {
            setY(y - 1);
        }
    }

    private void moveDown(boolean forceMove) {
        if (!unitImage.equals(BOX)) {
            sprite.setImage(unitImage.substring(0, unitImage.lastIndexOf("-")) + "-b.png");
        }
        viewer.getGraphics().commitEntityState(0, sprite);
        if (viewer.isEmpty(x, y + 1) || forceMove) {
            setY(y + 1);
        }
    }

    private void moveRight(boolean forceMove) {
        if (!unitImage.equals(BOX)) {
            sprite.setImage(unitImage.substring(0, unitImage.lastIndexOf("-")) + "-r.png");
        }
        viewer.getGraphics().commitEntityState(0, sprite);
        if (viewer.isEmpty(x + 1, y) || forceMove) {
            setX(x + 1);
        }
    }

    private void moveLeft(boolean forceMove) {
        if (!unitImage.equals(BOX)) {
            sprite.setImage(unitImage.substring(0, unitImage.lastIndexOf("-")) + "-l.png");
        }
        viewer.getGraphics().commitEntityState(0, sprite);
        if (viewer.isEmpty(x - 1, y) || forceMove) {
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

    public void tpTo(Point tp) {
        setX(tp.getX());
        setY(tp.getY());
        viewer.getGraphics().commitEntityState(0, sprite);
    }
}