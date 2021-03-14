package com.avtakhov.game.game_objects;

import com.badlogic.gdx.graphics.Texture;

public class Foam extends Bullet {
    public Foam(Texture img) {
        super(img);
        MAX_DISTANCE = 50;
        SPEED = 3;
    }
}
