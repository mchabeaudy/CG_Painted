package com.codingame.game.map;

import static java.util.stream.IntStream.range;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GameMap {


    private final int[][] boxes;

    public GameMap(Random random, int gameLevel) {
        boxes = new int[25][25];
        switch (gameLevel) {
            case 1:
                buildLevel1();
                break;
            case 2:
            default:
                buildRandomMap(random);
                break;
        }

    }

    private void buildLevel1() {
        range(0, 25).forEach(y -> range(0, 25).forEach(x -> boxes[y][x] = 0));
    }

    private void buildRandomMap(Random random) {
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
