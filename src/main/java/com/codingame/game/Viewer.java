package com.codingame.game;

import static com.codingame.game.map.MapElement.WALL;
import static java.util.stream.IntStream.range;

import com.codingame.game.map.Unit;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
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
    private List<UnitUI> uiUnits = new ArrayList<>();
    private List<Unit> units;
    private UnaryOperator<Integer> xConvertor;
    private UnaryOperator<Integer> yConvertor;
    private Board board;

    public void init(Board board, List<Unit> units, int leagueLevel) {
        this.units = units;
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
        xConvertor = x -> startX + x * tileWidth + tileWidth / 15;
        yConvertor = y -> tileWidth / 2 + (height - 1 - y) * tileWidth - fontSize / 2 + tileWidth / 15;
        range(0, height).forEach(y -> {
            int yG = height - y - 1;
            range(0, width).forEach(x -> {
                int xG = x;
                tiles[y][x] = new TileUI(startX + xG * tileWidth, tileWidth / 2 + yG * tileWidth - fontSize / 2, this,
                        board.getGameMap().getElements()[y][x]);
            });
        });

//        addBox(startX, fontSize, 1, 10);
//        addBox(startX, fontSize, 23, 10);
//        addBox(startX, fontSize, 11, 10);

        UnitUI unit1 = new UnitUI(xConvertor, yConvertor, this, "robot-01-t.png");
        unit1.setX(2);
        unit1.setY(8);
        unit1.setTeam(1);
        units.get(0).setUi(unit1);
        this.uiUnits.add(unit1);
        UnitUI unit2 = new UnitUI(xConvertor, yConvertor, this, "robot-08-t.png");
        if (leagueLevel == 1) {
            unit2.setX(8);
        } else {
            unit2.setX(21);
        }
        unit2.setY(8);
        unit2.setTeam(2);
        units.get(1).setUi(unit2);
        this.uiUnits.add(unit2);

        paint();
    }

    private void addBox(int startX, int fontSize, int x, int y) {
        getGraphics().createSprite()
                .setImage("box.png")
                .setBaseWidth(getTileWidth())
                .setBaseHeight(getTileWidth())
                .setX(startX + x * tileWidth)
                .setY(tileWidth / 2 + y * tileWidth - fontSize / 2);
    }


    public void paint() {
        uiUnits.forEach(u -> tiles[u.getY()][u.getX()].setElement(u.getMapElement()));
    }

    public List<UnitUI> getUiUnits(int playerId) {
        return uiUnits.stream()
                .filter(u -> u.getPlayerId() == playerId)
                .sorted(Comparator.comparingInt(UnitUI::getUnitId))
                .collect(Collectors.toList());
    }

    public boolean isEmpty(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return false;
        }
        if (board.getGameMap().getElements()[y][x] == WALL) {
            return false;
        }
        if (getBoard().getGameMap().getBoxes().stream().anyMatch(b -> b.hasSameCoordinates(x, y))) {
            return false;
        }
        if (units.stream().anyMatch(u -> u.getUi().hasSameCoordinates(x, y))) {
            return false;
        }
        return true;
    }

}
