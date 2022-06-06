package com.codingame.game;

import static com.codingame.game.map.MapElement.WALL;
import static java.util.stream.IntStream.range;

import com.codingame.game.map.Robot;
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
    private List<Robot> robots;
    private UnaryOperator<Integer> xConvertor;
    private UnaryOperator<Integer> yConvertor;
    private Board board;

    public void init(Board board, List<Robot> robots, int leagueLevel) {
        this.robots = robots;
        this.board = board;
        graphics.createRectangle().setWidth(viewerWidth).setHeight(viewerHeight)
            .setFillColor(BACKGROUND_COLOR);
        viewerWidth = graphics.getWorld().getWidth();
        viewerHeight = graphics.getWorld().getHeight();
        width = board.getWidth();
        height = board.getHeight();
        tileWidth = viewerHeight / -~height;
        int startX = viewerWidth / 2 - tileWidth * width / 2;
        int fontSize = tileWidth / 3;
        tiles = new TileUI[height][width];
        xConvertor = x -> startX + x * tileWidth + tileWidth / 15;
        yConvertor =
            y -> tileWidth / 2 + (height - 1 - y) * tileWidth - fontSize / 2 + tileWidth / 15;
        range(0, height).forEach(y -> {
            int yG = height - y - 1;
            range(0, width).forEach(x -> {
                int xG = x;
                tiles[y][x] = new TileUI(startX + xG * tileWidth,
                    tileWidth / 2 + yG * tileWidth - fontSize / 2, this,
                    board.getGameMap().getElements()[y][x]);
            });
        });

//        addBox(startX, fontSize, 1, 10);
//        addBox(startX, fontSize, 23, 10);
//        addBox(startX, fontSize, 11, 10);

        board.getGameMap().getTeleports().forEach(tp -> {
            String image = tp.getGroupId() == 1 ? "circle-1.png" : "circle-2.png";
            UnitUI ui = new UnitUI(xConvertor, yConvertor, this, image);
            ui.setX(tp.getX());
            ui.setY(tp.getY());
        });

        if (leagueLevel == 1 || leagueLevel == 2) {
            initLevel1or2(robots, leagueLevel);
        } else if (leagueLevel == 4) {
            UnitUI unit1 = new UnitUI(xConvertor, yConvertor, this, "robot-01-t.png");
            unit1.setX(1);
            unit1.setY(1);
            unit1.setTeam(1);
            robots.get(0).setUi(unit1);
            this.uiUnits.add(unit1);
            UnitUI unit2 = new UnitUI(xConvertor, yConvertor, this, "robot-01-t.png");
            unit2.setX(3);
            unit2.setY(7);
            unit2.setTeam(1);
            robots.get(1).setUi(unit2);
            this.uiUnits.add(unit2);
            UnitUI unit3 = new UnitUI(xConvertor, yConvertor, this, "robot-02-t.png");
            unit3.setX(1);
            unit3.setY(9);
            unit3.setTeam(1);
            robots.get(2).setUi(unit3);
            this.uiUnits.add(unit3);
            UnitUI unit4 = new UnitUI(xConvertor, yConvertor, this, "robot-02-t.png");
            unit4.setX(3);
            unit4.setY(9);
            unit4.setTeam(1);
            robots.get(3).setUi(unit4);
            this.uiUnits.add(unit4);
            UnitUI unit5 = new UnitUI(xConvertor, yConvertor, this, "robot-08-t.png");
            unit5.setX(20);
            unit5.setY(7);
            unit5.setTeam(2);
            robots.get(4).setUi(unit5);
            this.uiUnits.add(unit5);
            UnitUI unit6 = new UnitUI(xConvertor, yConvertor, this, "robot-08-t.png");
            unit6.setX(22);
            unit6.setY(7);
            unit6.setTeam(2);
            robots.get(5).setUi(unit6);
            this.uiUnits.add(unit6);
            UnitUI unit7 = new UnitUI(xConvertor, yConvertor, this, "robot-09-t.png");
            unit7.setX(20);
            unit7.setY(9);
            unit7.setTeam(2);
            robots.get(6).setUi(unit7);
            this.uiUnits.add(unit7);
            UnitUI unit8 = new UnitUI(xConvertor, yConvertor, this, "robot-09-t.png");
            unit8.setX(22);
            unit8.setY(9);
            unit8.setTeam(2);
            robots.get(7).setUi(unit8);
            this.uiUnits.add(unit8);
        }

        paint();
    }

    private void initLevel1or2(List<Robot> robots, int leagueLevel) {
        UnitUI unit1 = new UnitUI(xConvertor, yConvertor, this, "robot-01-t.png");
        unit1.setX(2);
        unit1.setY(8);
        unit1.setTeam(1);
        robots.get(0).setUi(unit1);
        this.uiUnits.add(unit1);
        UnitUI unit2 = new UnitUI(xConvertor, yConvertor, this, "robot-08-t.png");
        if (leagueLevel == 1) {
            unit2.setX(8);
        } else {
            unit2.setX(21);
        }
        unit2.setY(8);
        unit2.setTeam(2);
        robots.get(1).setUi(unit2);
        this.uiUnits.add(unit2);
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
        if (robots.stream().anyMatch(u -> u.getUi().hasSameCoordinates(x, y))) {
            return false;
        }
        return true;
    }

}
