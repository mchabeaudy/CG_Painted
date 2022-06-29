package com.codingame.game;

import static com.codingame.game.Constants.AVATAR_BACKGROUND;
import static com.codingame.game.Constants.BG_1;
import static com.codingame.game.Constants.BG_2;
import static com.codingame.game.Constants.BG_3;
import static com.codingame.game.Constants.BOX;
import static com.codingame.game.Constants.ROBOT1A;
import static com.codingame.game.Constants.ROBOT1B;
import static com.codingame.game.Constants.ROBOT2A;
import static com.codingame.game.Constants.ROBOT2B;
import static com.codingame.game.Constants.TELEPORT1;
import static com.codingame.game.Constants.TELEPORT2;
import static com.codingame.game.map.MapElement.WALL;
import static java.util.stream.IntStream.range;

import com.codingame.game.map.Robot;
import com.codingame.game.map.background.BackgroundProperty;
import com.codingame.game.map.background.PlayerProperties;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.TextBasedEntity.TextAlign;
import java.util.List;
import java.util.function.UnaryOperator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Viewer {

    private static final int BACKGROUND_COLOR = 0x888888;
    private final MultiplayerGameManager<Player> gameManager;
    private final GraphicEntityModule graphics;

    private int viewerWidth;
    private int viewerHeight;
    private int width;
    private int height;
    private int tileWidth;
    private TileUI[][] tiles;
    private List<Robot> robots;
    private UnaryOperator<Integer> xConvertor;
    private UnaryOperator<Integer> yConvertor;
    private Board board;

    public void init(Board board, List<Robot> robots, int leagueLevel, List<Player> players) {
        this.robots = robots;
        this.board = board;
        viewerWidth = graphics.getWorld().getWidth();
        viewerHeight = graphics.getWorld().getHeight();
        initGraphics(leagueLevel, players);
        width = board.getWidth();
        height = board.getHeight();
        tileWidth = viewerHeight / height;
        int gap = (viewerHeight - tileWidth * height) / 2;
        int startX = viewerWidth / 2 - tileWidth * width / 2;

        tiles = new TileUI[height][width];
        xConvertor = x -> startX + x * tileWidth + tileWidth / 15;
        yConvertor = y -> gap + (height - 1 - y) * tileWidth + tileWidth / 15;
        range(0, height).forEach(y -> {
            int yG = height - y - 1;
            range(0, width).forEach(x ->
                tiles[y][x] = new TileUI(startX + x * tileWidth,
                    gap + yG * tileWidth, this,
                    board.getGameMap().getElements()[y][x])
            );
        });

        board.getGameMap().getTeleports()
            .forEach(tp -> new UnitUI(this, tp.getGroupId() == 1 ? TELEPORT1 : TELEPORT2, tp.getX(),
                tp.getY()));
        board.getGameMap().getBoxes()
            .forEach(box -> new UnitUI(this, BOX, box.getX(), box.getY()));

        switch (leagueLevel) {
            case 1:
                initLevel1(robots);
                break;
            case 2:
            case 3:
                initLevel23(robots);
                break;
            case 4:
                initLevel4(robots);
                break;
            default:
                throw new IllegalStateException("level not implemented");
        }
        paint(robots);
    }

    private void initGraphics(int leagueLevel, List<Player> players) {
        String background;
        BackgroundProperty backgroundProperty;
        switch (leagueLevel) {
            case 1:
                backgroundProperty = BG_1;
                background = "background1.png";
                break;
            case 2:
            case 3:
                backgroundProperty = BG_2;
                background = "background2.png";
                break;
            case 4:
                backgroundProperty = BG_3;
                background = "background3.png";
                break;
            default:
                throw new IllegalStateException("not implemented");
        }

        graphics.createSprite().setImage(background)
            .setBaseWidth(viewerWidth)
            .setBaseHeight(viewerHeight);

        PlayerProperties prop0 = backgroundProperty.getPlayerProperties().get(0);
        int textWidth = prop0.getName().getWidth() * viewerWidth
            / backgroundProperty.getWidth();

        for (int i = 0; i < gameManager.getPlayerCount(); i++) {
            Player player = players.get(i);
            PlayerProperties property = backgroundProperty.getPlayerProperties().get(i);
            graphics.createRectangle()
                .setFillColor(AVATAR_BACKGROUND)
                .setX(property.getAvatar().getXMin() * viewerWidth / backgroundProperty.getWidth())
                .setWidth(
                    property.getAvatar().getWidth() * viewerWidth / backgroundProperty.getWidth())
                .setY(
                    property.getAvatar().getYMin() * viewerHeight / backgroundProperty.getHeight())
                .setHeight(property.getAvatar().getHeight() * viewerHeight
                    / backgroundProperty.getHeight());

            graphics.createBitmapText()
                .setText(player.getNicknameToken())
                .setFont("Minecraft")
                .setFontSize(45)
                .setX(property.getName().getXMin() * viewerWidth / backgroundProperty.getWidth())
                .setY(property.getName().getYMin() * viewerHeight / backgroundProperty.getHeight())
                .setRotation(Math.PI)
                .setTextAlign(TextAlign.LEFT)
                .setMaxWidth(textWidth);
            graphics.createSprite()
                .setImage(player.getAvatarToken())
                .setX(property.getAvatar().getXMin() * viewerWidth / backgroundProperty.getWidth())
                .setY(
                    property.getAvatar().getYMin() * viewerHeight / backgroundProperty.getHeight())
                .setBaseWidth(
                    property.getAvatar().getWidth() * viewerWidth / backgroundProperty.getWidth())
                .setBaseHeight(
                    prop0.getAvatar().getHeight() * viewerHeight / backgroundProperty.getHeight());

        }

    }

    private void initLevel4(List<Robot> robots) {
        robots.get(0).setUi(new UnitUI(this, ROBOT1A, 1, 1, 1));
        robots.get(1).setUi(new UnitUI(this, ROBOT1A, 3, 8, 1));
        robots.get(2).setUi(new UnitUI(this, ROBOT1B, 1, 10, 1));
        robots.get(3).setUi(new UnitUI(this, ROBOT1B, 3, 10, 1));
        robots.get(4).setUi(new UnitUI(this, ROBOT2A, 20, 8, 2));
        robots.get(5).setUi(new UnitUI(this, ROBOT2A, 22, 8, 2));
        robots.get(6).setUi(new UnitUI(this, ROBOT2B, 20, 10, 2));
        robots.get(7).setUi(new UnitUI(this, ROBOT2B, 22, 10, 2));
    }

    private void initLevel1(List<Robot> robots) {
        robots.get(0).setUi(new UnitUI(this, ROBOT1A, 2, 7, 1));
        robots.get(1).setUi(new UnitUI(this, ROBOT2A, 12, 7, 2));
    }

    private void initLevel23(List<Robot> robots) {
        robots.get(0).setUi(new UnitUI(this, ROBOT1A, 2, 8, 1));
        robots.get(1).setUi(new UnitUI(this, ROBOT1A, 2, 10, 1));
        robots.get(2).setUi(new UnitUI(this, ROBOT2A, 21, 8, 2));
        robots.get(3).setUi(new UnitUI(this, ROBOT2A, 21, 10, 2));
    }


    private void addBox(int startX, int fontSize, int x, int y) {
        getGraphics().createSprite()
            .setImage("box.png")
            .setBaseWidth(getTileWidth())
            .setBaseHeight(getTileWidth())
            .setX(startX + x * tileWidth)
            .setY(tileWidth / 2 + y * tileWidth - fontSize / 2);
    }


    public void paint(List<Robot> robots) {
        robots.stream().map(Robot::getUi)
            .forEach(u -> tiles[u.getY()][u.getX()].setElement(u.getMapElement()));
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
