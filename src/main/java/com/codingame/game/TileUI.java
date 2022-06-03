package com.codingame.game;

import com.codingame.game.map.MapElement;
import com.codingame.gameengine.module.entities.Sprite;
import lombok.Getter;

@Getter
public class TileUI {

    private MapElement element;
    private final Sprite sprite;

    public TileUI(int x, int y, Viewer viewer, MapElement initialElement) {
        element = initialElement;
        sprite = viewer.getGraphics().createSprite()
                .setImage(initialElement.getImage())
                .setBaseWidth(viewer.getTileWidth())
                .setBaseHeight(viewer.getTileWidth())
                .setX(x)
                .setY(y);
    }



    public void setElement(MapElement element){
        this.element = element;
        sprite.setImage(element.getImage());
    }

}
