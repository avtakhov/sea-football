package com.avtakhov.game.game_objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class BotShip extends Ship {

    private final Ball ball;


    public BotShip(Texture img, Ball ball) {
        super(img);
        this.ball = ball;

    }
}
