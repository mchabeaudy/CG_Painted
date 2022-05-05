package com.codingame.game;

import static java.util.stream.IntStream.range;

import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import java.util.function.Function;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Viewer {

    private static final int BACKGROUND_COLOR = 0x7F7F7F;
    private final MultiplayerGameManager<Player> gameManager;
    private final GraphicEntityModule graphics;

    private int viewerWidth;
    private int viewerHeight;
    private int width;
    private int height;
    private int tileWidth;
    private TileUI[][] tiles;
    private UnitUI unit;

    public void init(Board board) {
        graphics.createRectangle().setWidth(viewerWidth).setHeight(viewerHeight).setFillColor(BACKGROUND_COLOR);
        viewerWidth = graphics.getWorld().getWidth();
        viewerHeight = graphics.getWorld().getHeight();
        width = board.getWidth();
        height = board.getHeight();
        tileWidth = viewerHeight / -~height;
        int startX = viewerWidth / 2 - tileWidth * width / 2;
        int fontSize = tileWidth / 3;
        tiles = new TileUI[height][width];
        range(0, height).forEach(y -> {
            int yG = height - y - 1;
            range(0, width).forEach(x -> {
                int xG = x;
                tiles[y][x] = new TileUI(startX + xG * tileWidth, tileWidth / 2 + yG * tileWidth - fontSize / 2, this,
                        board.getGameMap().getBoxes()[y][x]);
                if (x == 10 && y == 10) {
                }
            });
        });
        Function<Integer, Integer> xConvertor = t -> startX + t * tileWidth + tileWidth / 15;
        Function<Integer, Integer> yConvertor = t -> tileWidth / 2 + t * tileWidth - fontSize / 2 + tileWidth / 15;
        UnitUI u = new UnitUI(xConvertor, yConvertor, this);
        u.setX(5);
        u.setY(5);
        UnitUI u1 = new UnitUI(xConvertor, yConvertor, this);
        u1.setX(5);
        u1.setY(10);
        UnitUI u2 = new UnitUI(xConvertor, yConvertor, this);
        u2.setX(10);
        u2.setY(5);
        UnitUI u3 = new UnitUI(xConvertor, yConvertor, this);
        u3.setX(10);
        u3.setY(10);
        setUnit(u);
    }

    public void setUnit(UnitUI unit) {
        this.unit = unit;
    }

    public UnitUI getUnit() {
        return unit;
    }
}
