package com.codingame.game.map;

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
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class GameMap {

    private final int[][] boxes;
    private List<Transporter> transporters = new ArrayList<>();
    private boolean withRocks;
    private int width;
    private int height;

    public GameMap(Random random, int gameLevel) {
        width = 20;
        height = 20;
        switch (gameLevel) {
            case 1:
                boxes = new int[20][20];
                buildLevel1();
                break;
            case 2:
                boxes = new int[20][20];
                buildRandomMap(random, false);
                break;
            case 3:
                width = 24;
                height = 19;
                boxes = new int[19][24];
                buildMapV3(random);
                break;
            case 4:
                boxes = new int[20][20];
                buildRandomMap(random, true);
                break;
            default:
                boxes = new int[20][20];
                break;
        }

    }

    private void buildLevel1() {
        range(0, 20).forEach(y -> range(0, 20).forEach(x -> boxes[y][x] = 0));
    }

    public void buildMapV3(Random random) {
        this.withRocks = random.nextBoolean();
        int mapWidth = 24;
        int mapHeight = 19;

        range(0, mapHeight).forEach(y -> range(0, mapWidth).forEach(x -> boxes[y][x] = 0));

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

        blocks.forEach(b -> b.getWalls()
                .forEach(p -> boxes[b.getY() + p.getY()][b.getX() + p.getX()] = 3));
    }

    private void buildRandomMap(Random random, boolean withTeleportAndBlocks) {
        this.withRocks = withTeleportAndBlocks;
        MapFragmentType[] allTypes = MapFragmentType.values();
        List<MapFragmentType> types = Arrays.stream(allTypes).filter(t -> t != MapFragmentType.CORNER_ROOM)
                .collect(Collectors.toList());
        List<MapFragmentType> notMiddle = new ArrayList<>();
        notMiddle.add(MapFragmentType.CORNER_ROOM);
        notMiddle.add(MapFragmentType.T2);
        notMiddle.add(MapFragmentType.T4);
        List<MapFragmentType> middleTypes = Arrays.stream(allTypes).filter(t -> !notMiddle.contains(t))
                .collect(Collectors.toList());

        MapFragment corner;
        if (random.nextInt(3) != 2) {
            corner = MapFragment.of(MapFragmentType.CORNER_ROOM);
            if (withTeleportAndBlocks && random.nextInt(3) != 2) {
                boolean cross = random.nextBoolean();
                transporters.add(new Transporter(1, 1, 0));
                transporters.add(new Transporter(23, 1, cross ? 1 : 0));
                transporters.add(new Transporter(1, 23, 1));
                transporters.add(new Transporter(23, 23, cross ? 1 : 0));
            }
        } else {
            corner = MapFragment.of(allTypes[random.nextInt(allTypes.length)]);
        }
        // 0 - 0
        buildFragment(0, 0, corner);
        // 1 - 0 
        MapFragment f10 = randomFragment(random, types);
        buildFragment(5, 0, f10);
        // 2 - 0
        buildFragment(10, 0, randomFragment(random, middleTypes));
        // 3 - 0 
        buildFragment(15, 0, f10.verticalFlip());
        // 4 - 0 
        buildFragment(20, 0, corner.verticalFlip());
        // 0 - 1
        MapFragment f01 = randomFragment(random, types);
        buildFragment(0, 5, f01);
        // 1 - 1
        MapFragment f11 = randomFragment(random, types);
        buildFragment(5, 5, f11);
        // 2 - 1
        buildFragment(10, 5, randomFragment(random, middleTypes));
        // 3 - 1
        buildFragment(15, 5, f11.verticalFlip());
        // 4 - 1
        buildFragment(20, 5, f01.verticalFlip());
        // 0 - 2
        buildFragment(0, 10, MapFragment.of(MapFragmentType.EMPTY));
        // 1 - 2
        MapFragment f12 = randomFragment(random, types);
        buildFragment(5, 10, f12);
        // 2 - 2
        buildFragment(10, 10, randomFragment(random, middleTypes));
        // 3 - 2
        buildFragment(15, 10, f12.verticalFlip());
        // 4 - 2
        buildFragment(20, 10, MapFragment.of(MapFragmentType.EMPTY));
        // 0 - 3
        MapFragment f03 = randomFragment(random, types);
        buildFragment(0, 15, f03);
        // 1 - 3
        MapFragment f13 = randomFragment(random, types);
        buildFragment(5, 15, f13);
        // 2 - 3
        buildFragment(10, 15, randomFragment(random, middleTypes));
        // 3 - 3
        buildFragment(15, 15, f13.verticalFlip());
        // 4 - 3
        buildFragment(20, 15, f03.verticalFlip());
        // 0 - 4
        buildFragment(0, 20, corner.horizontalFlip());
        // 1 - 4
        MapFragment f14 = randomFragment(random, types);
        buildFragment(5, 20, f14);
        // 2 - 4
        buildFragment(10, 20, randomFragment(random, middleTypes));
        // 3 - 4
        buildFragment(15, 20, f14.verticalFlip());
        // 4 - 4
        buildFragment(20, 20, corner.horizontalFlip().verticalFlip());

    }

    private MapFragment randomFragment(Random random, List<MapFragmentType> types) {
        return MapFragment.of(types.get(random.nextInt(types.size())));
    }

    private void buildFragment(int dx, int dy, MapFragment fragment) {
        range(dy, dy + 5).forEach(y -> range(dx, dx + 5).forEach(
                x -> boxes[y][x] =
                        fragment.getWalls().stream().noneMatch(p -> p.hasSameCoordinates(x - dx, y - dy)) ? 0 : 3));

    }

    public int[][] getBoxes() {
        return boxes;
    }
}
