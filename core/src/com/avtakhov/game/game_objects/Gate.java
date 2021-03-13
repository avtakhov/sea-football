package com.avtakhov.game.game_objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Gate extends RenderObject{
    public Gate(Sprite img) {
        super(img);
    }

    public Gate(Texture texture) {
        super(texture);
    }

}
