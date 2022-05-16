package com.codingame.game;

import static java.util.stream.IntStream.range;

import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import java.util.function.UnaryOperator;
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
    private UnitUI unit1;
    private UnitUI unit2;
    private UnaryOperator<Integer> xConvertor;
    private UnaryOperator<Integer> yConvertor;
    private Board board;

    public void init(Board board) {
        this.board = board;
        graphics.createRectangle().setWidth(viewerWidth).setHeight(viewerHeight).setFillColor(BACKGROUND_COLOR);
        viewerWidth = graphics.getWorld().getWidth();
        viewerHeight = graphics.getWorld().getHeight();
        width = board.getWidth();
        height = board.getHeight();
        tileWidth = viewerHeight / -~height;
        int startX = viewerWidth / 2 - tileWidth * width / 2;
        int fontSize = tileWidth / 3;
        tiles = new TileUI[height][width];
        xConvertor = t -> startX + t * tileWidth + tileWidth / 15;
        yConvertor = t -> tileWidth / 2 + t * tileWidth - fontSize / 2 + tileWidth / 15;
        range(0, height).forEach(y -> {
            int yG = height - y - 1;
            range(0, width).forEach(x -> {
                int xG = x;
                tiles[y][x] = new TileUI(startX + xG * tileWidth, tileWidth / 2 + yG * tileWidth - fontSize / 2, this,
                        board.getGameMap().getBoxes()[y][x]);
            });
        });
        unit1 = new UnitUI(xConvertor, yConvertor, this);
        unit1.setX(2);
        unit1.setY(12);
        unit2 = new UnitUI(xConvertor, yConvertor, this);
        unit2.setX(22);
        unit2.setY(12);
        paint();
    }


    public void paint() {
        tiles[24 - unit1.getY()][unit1.getX()].paint(1);
        tiles[24 - unit2.getY()][unit2.getX()].paint(2);
    }

}
