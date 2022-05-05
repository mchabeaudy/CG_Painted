package com.codingame.game.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapFragment {

    private final MapFragmentType type;

    private final List<Point> walls;
    private static final Map<MapFragmentType, MapFragment> m = new HashMap<>();

    private MapFragment(MapFragmentType type, List<Point> walls) {
        this.type = type;
        this.walls = walls;
    }

    public static MapFragment of(MapFragmentType type)
    {
        if(m.containsKey(type)){
            return m.get(type);
        }
        MapFragment fragment = new MapFragment(type);
        m.put(type,fragment);
        return fragment;
    }

    public MapFragment(MapFragmentType type) {
        this.type = type;
        walls = new ArrayList<>();
        switch (type) {
            case ROOM:
                buildRoom();
                break;
            case CROSS:
                buildCross();
                break;
            case EMPTY:
                break;
            case SQUARE:
                buildSquare();
                break;
            case DIAMOND:
                buildDiamond();
                break;
            case CORNERS_H:
                buildCornersH();
                break;
            case CORNERS_V:
                buildCornersV();
                break;
            case CORRIDOR_V:
                buildCorridorV();
                break;
            case CORRIDOR_H:
                buildCorridorH();
                break;
            case CORNER_ROOM:
                buildCornerRoom();
                break;
            case MID_WALLS_1_H:
                buildMidWalls1H();
                break;
            case MID_WALLS_1_V:
                buildMidWall1V();
                break;
            case MID_WALLS_2_H:
                buildMidWall2H();
                break;
            case MID_WALLS_2_V:
                buildMidWalls2V();
                break;
            case FILLED_CORRIDOR_H:
                buildFilledCorridorH();
                break;
            case FILLED_CORRIDOR_V:
                buildFilledCorridorV();
                break;
            case T1:
                buildT1();
                break;
            case T2:
                buildT2();
                break;
            case T3:
                buildT3();
                break;
            case T4:
                buildT4();
                break;
            default:
                throw new IllegalArgumentException("Unknown type : " + type);
        }

    }

    private void buildT4() {
        walls.add(Point.of(1, 1));
        walls.add(Point.of(1, 2));
        walls.add(Point.of(1, 3));
        walls.add(Point.of(2, 2));
        walls.add(Point.of(3, 2));
    }

    private void buildT3() {
        walls.add(Point.of(2, 1));
        walls.add(Point.of(2, 2));
        walls.add(Point.of(1, 3));
        walls.add(Point.of(2, 3));
        walls.add(Point.of(3, 3));
    }

    private void buildT2() {
        walls.add(Point.of(3, 1));
        walls.add(Point.of(3, 2));
        walls.add(Point.of(3, 3));
        walls.add(Point.of(2, 2));
        walls.add(Point.of(1, 2));
    }

    private void buildT1() {
        walls.add(Point.of(2, 3));
        walls.add(Point.of(2, 2));
        walls.add(Point.of(1, 1));
        walls.add(Point.of(2, 1));
        walls.add(Point.of(3, 1));
    }

    private void buildFilledCorridorV() {
        walls.add(Point.of(0, 0));
        walls.add(Point.of(0, 1));
        walls.add(Point.of(0, 3));
        walls.add(Point.of(0, 4));
        walls.add(Point.of(4, 0));
        walls.add(Point.of(4, 1));
        walls.add(Point.of(4, 3));
        walls.add(Point.of(4, 4));
        walls.add(Point.of(2, 1));
        walls.add(Point.of(2, 2));
        walls.add(Point.of(2, 3));
    }

    private void buildFilledCorridorH() {
        walls.add(Point.of(0, 0));
        walls.add(Point.of(1, 0));
        walls.add(Point.of(3, 0));
        walls.add(Point.of(4, 0));
        walls.add(Point.of(0, 4));
        walls.add(Point.of(1, 4));
        walls.add(Point.of(3, 4));
        walls.add(Point.of(4, 4));
        walls.add(Point.of(1, 2));
        walls.add(Point.of(2, 2));
        walls.add(Point.of(3, 2));
    }

    private void buildMidWalls2V() {
        walls.add(Point.of(1, 1));
        walls.add(Point.of(1, 2));
        walls.add(Point.of(1, 3));
        walls.add(Point.of(3, 1));
        walls.add(Point.of(3, 2));
        walls.add(Point.of(3, 3));
    }

    private void buildMidWall2H() {
        walls.add(Point.of(1, 1));
        walls.add(Point.of(2, 1));
        walls.add(Point.of(3, 1));
        walls.add(Point.of(1, 3));
        walls.add(Point.of(2, 3));
        walls.add(Point.of(3, 3));
    }

    private void buildMidWall1V() {
        walls.add(Point.of(1, 0));
        walls.add(Point.of(1, 1));
        walls.add(Point.of(1, 3));
        walls.add(Point.of(1, 4));
        walls.add(Point.of(3, 0));
        walls.add(Point.of(3, 1));
        walls.add(Point.of(3, 3));
        walls.add(Point.of(3, 4));
    }

    private void buildMidWalls1H() {
        walls.add(Point.of(0, 1));
        walls.add(Point.of(1, 1));
        walls.add(Point.of(3, 1));
        walls.add(Point.of(4, 1));
        walls.add(Point.of(0, 3));
        walls.add(Point.of(1, 3));
        walls.add(Point.of(3, 3));
        walls.add(Point.of(4, 3));
    }

    private void buildCornerRoom() {
        walls.add(Point.of(0, 4));
        walls.add(Point.of(1, 4));
        walls.add(Point.of(2, 4));
        walls.add(Point.of(3, 4));
        walls.add(Point.of(4, 4));
        walls.add(Point.of(4, 3));
        walls.add(Point.of(4, 1));
        walls.add(Point.of(4, 0));
    }

    private void buildCorridorH() {
        walls.add(Point.of(0, 0));
        walls.add(Point.of(1, 0));
        walls.add(Point.of(3, 0));
        walls.add(Point.of(4, 0));
        walls.add(Point.of(0, 4));
        walls.add(Point.of(1, 4));
        walls.add(Point.of(3, 4));
        walls.add(Point.of(4, 4));
    }

    private void buildCorridorV() {
        walls.add(Point.of(0, 0));
        walls.add(Point.of(0, 1));
        walls.add(Point.of(0, 3));
        walls.add(Point.of(0, 4));
        walls.add(Point.of(4, 0));
        walls.add(Point.of(4, 1));
        walls.add(Point.of(4, 3));
        walls.add(Point.of(4, 4));
    }

    private void buildCornersV() {
        walls.add(Point.of(0, 0));
        walls.add(Point.of(0, 1));
        walls.add(Point.of(1, 1));
        walls.add(Point.of(0, 3));
        walls.add(Point.of(1, 3));
        walls.add(Point.of(0, 4));
        walls.add(Point.of(4, 0));
        walls.add(Point.of(4, 1));
        walls.add(Point.of(3, 1));
        walls.add(Point.of(4, 3));
        walls.add(Point.of(3, 3));
        walls.add(Point.of(4, 4));
    }

    private void buildCornersH() {
        walls.add(Point.of(0, 0));
        walls.add(Point.of(1, 0));
        walls.add(Point.of(3, 0));
        walls.add(Point.of(4, 0));
        walls.add(Point.of(1, 1));
        walls.add(Point.of(3, 1));
        walls.add(Point.of(0, 4));
        walls.add(Point.of(1, 4));
        walls.add(Point.of(3, 4));
        walls.add(Point.of(4, 4));
        walls.add(Point.of(1, 3));
        walls.add(Point.of(3, 3));
    }

    private void buildDiamond() {
        walls.add(Point.of(0, 0));
        walls.add(Point.of(4, 0));
        walls.add(Point.of(2, 1));
        walls.add(Point.of(1, 2));
        walls.add(Point.of(2, 2));
        walls.add(Point.of(3, 2));
        walls.add(Point.of(2, 3));
        walls.add(Point.of(0, 4));
        walls.add(Point.of(4, 4));
    }

    private void buildSquare() {
        walls.add(Point.of(1, 1));
        walls.add(Point.of(2, 1));
        walls.add(Point.of(3, 1));
        walls.add(Point.of(1, 2));
        walls.add(Point.of(2, 2));
        walls.add(Point.of(3, 2));
        walls.add(Point.of(1, 3));
        walls.add(Point.of(2, 3));
        walls.add(Point.of(3, 3));
    }

    private void buildCross() {
        walls.add(Point.of(0, 0));
        walls.add(Point.of(0, 1));
        walls.add(Point.of(0, 3));
        walls.add(Point.of(0, 4));
        walls.add(Point.of(1, 0));
        walls.add(Point.of(1, 1));
        walls.add(Point.of(1, 3));
        walls.add(Point.of(1, 4));
        walls.add(Point.of(3, 0));
        walls.add(Point.of(3, 1));
        walls.add(Point.of(3, 3));
        walls.add(Point.of(3, 4));
        walls.add(Point.of(4, 0));
        walls.add(Point.of(4, 1));
        walls.add(Point.of(4, 3));
        walls.add(Point.of(4, 4));
    }

    private void buildRoom() {
        walls.add(Point.of(0, 0));
        walls.add(Point.of(0, 1));
        walls.add(Point.of(1, 0));
        walls.add(Point.of(0, 3));
        walls.add(Point.of(0, 4));
        walls.add(Point.of(1, 4));
        walls.add(Point.of(3, 0));
        walls.add(Point.of(4, 0));
        walls.add(Point.of(4, 1));
        walls.add(Point.of(4, 3));
        walls.add(Point.of(4, 4));
        walls.add(Point.of(3, 4));
    }

    public List<Point> getWalls() {
        return walls;
    }

    public MapFragment verticalFlip() {
        return new MapFragment(type,
                walls.stream().map(p -> Point.of(4 - p.getX(), p.getY())).collect(Collectors.toList()));
    }

    public MapFragment horizontalFlip() {
        return new MapFragment(type,
                walls.stream().map(p -> Point.of(p.getX(), 4 - p.getY())).collect(Collectors.toList()));
    }
}
