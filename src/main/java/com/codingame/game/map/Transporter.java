package com.codingame.game.map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transporter extends Point {

    private int linkId;

    public Transporter(int x, int y, int linkId) {
        super(x, y);
        this.linkId = linkId;
    }
}
