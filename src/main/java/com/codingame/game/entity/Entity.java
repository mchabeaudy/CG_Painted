package com.codingame.game.entity;

import com.codingame.game.map.Point;

public abstract class Entity extends Point {

    abstract String getEntityId();
    abstract String getOwner();
    abstract String getParam1();
    abstract String getParam2();

}
