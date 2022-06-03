package com.codingame.game.map;

import static com.codingame.game.map.MapElement.NEUTRAL;
import static com.codingame.game.map.MapElement.WALL;
import static java.util.stream.IntStream.range;

import com.codingame.game.map.v2.Block;
import com.codingame.game.map.v2.blocks.Block01;
import com.codingame.game.map.v2.blocks.Block10;
import com.codingame.game.map.v2.blocks.Block11;
import com.codingame.game.map.v2.blocks.Block12;
import com.codingame.game.map.v2.blocks.Block20;
import com.codingame.game.map.v2.blocks.Block21;
import com.codingame.game.map.v2.blocks.Block22;
import com.codingame.game.map.v2.blocks.Corner;
import com.codingame.game.map.v2.blocks.StartBlock;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.Getter;

@Getter
public class GameMap {


    private MapElement[][] elements;
    private List<Teleport> teleports = new ArrayList<>();
    private List<Box> boxes = new ArrayList<>();
    private boolean withRocks;
    private int width;
    private int height;

    public GameMap(Random random, int gameLevel) {
        initDimensions(gameLevel);
        initMap(gameLevel, random);
    }

    private void initMap(int gameLevel, Random random) {
        switch (gameLevel) {

            case 1:
                buildLevel1();
                break;
            case 2:
                buildMap(random, false);
                break;
            case 3:
            case 4:
                buildMap(random, true);
                break;
            default:
                throw new IllegalArgumentException("Level " + gameLevel + " has not been implemented");
        }
    }

    private void initDimensions(int gameLevel) {
        switch (gameLevel) {
            case 1:
                width = 15;
                height = 15;
                break;
            case 2:
            case 3:
            case 4:
                width = 24;
                height = 19;
                break;
            default:
                throw new IllegalArgumentException("Level " + gameLevel + " has not been implemented");
        }
        elements = new MapElement[height][width];
    }

    private void buildLevel1() {
        range(0, height).forEach(y -> range(0, width).forEach(x -> elements[y][x] = NEUTRAL));
    }

    public void buildMap(Random random, boolean withElements) {
        this.withRocks = random.nextBoolean();
        int mapWidth = 24;
        int mapHeight = 19;

        range(0, mapHeight).forEach(y -> range(0, mapWidth).forEach(x -> elements[y][x] = NEUTRAL));

        // up left
        Block block00 = Corner.of(withRocks, random, 0, 0);
        Block block01 = Block01.of(withRocks, random, 0, 4);
        Block block02 = StartBlock.of(random, 0, 7);
        Block block10 = Block10.of(random, 4, 0);
        Block block11 = Block11.of(random, 4, 3);
        Block block12 = Block12.of(random, 5, 7);
        Block block20 = Block20.of(random, 8, 0);
        Block block21 = Block21.of(random, 8, 4);
        Block block22 = Block22.of(random, 8, 8);

        // up right
        Block block30 = block20.verticalFlip(mapWidth);
        Block block40 = block10.verticalFlip(mapWidth);
        Block block50 = block00.verticalFlip(mapWidth);
        Block block31 = block21.verticalFlip(mapWidth);
        Block block41 = block11.verticalFlip(mapWidth);
        Block block51 = block01.verticalFlip(mapWidth);
        Block block32 = block22.verticalFlip(mapWidth);
        Block block42 = block12.verticalFlip(mapWidth);
        Block block52 = block02.verticalFlip(mapWidth);

        // down left
        Block block03 = Block01.of(withRocks, random, 0, 4).horizontalFlip(mapHeight);
        Block block04 = block00.horizontalFlip(mapHeight);
        Block block13 = Block11.of(random, 4, 3).horizontalFlip(mapHeight);
        Block block14 = Block10.of(random, 4, 0).horizontalFlip(mapHeight);
        Block block23 = Block21.of(random, 8, 4).horizontalFlip(mapHeight);
        Block block24 = Block20.of(random, 8, 0).horizontalFlip(mapHeight);

        // down right
        Block block33 = block23.verticalFlip(mapWidth);
        Block block43 = block13.verticalFlip(mapWidth);
        Block block53 = block03.verticalFlip(mapWidth);
        Block block34 = block24.verticalFlip(mapWidth);
        Block block44 = block14.verticalFlip(mapWidth);
        Block block54 = block04.verticalFlip(mapWidth);

        List<Block> blocks = new ArrayList<>();
        blocks.add(block00);
        blocks.add(block10);
        blocks.add(block20);
        blocks.add(block30);
        blocks.add(block40);
        blocks.add(block50);
        blocks.add(block01);
        blocks.add(block11);
        blocks.add(block21);
        blocks.add(block31);
        blocks.add(block41);
        blocks.add(block51);
        blocks.add(block02);
        blocks.add(block12);
        blocks.add(block22);
        blocks.add(block32);
        blocks.add(block42);
        blocks.add(block52);
        blocks.add(block03);
        blocks.add(block13);
        blocks.add(block23);
        blocks.add(block33);
        blocks.add(block43);
        blocks.add(block53);
        blocks.add(block04);
        blocks.add(block14);
        blocks.add(block24);
        blocks.add(block34);
        blocks.add(block44);
        blocks.add(block54);

        blocks.forEach(b -> b.getWalls().forEach(p -> elements[b.getY() + p.getY()][b.getX() + p.getX()] = WALL));
    }


}
