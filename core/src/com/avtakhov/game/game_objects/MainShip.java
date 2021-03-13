package com.avtakhov.game.game_objects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class MainShip extends Ship {

    OrthographicCamera camera;

    public MainShip(Texture img, OrthographicCamera camera) {
        super(img);
        this.camera = camera;
    }


    @Override
    public void setX(float x) {
        super.setX(x);
        camera.position.x = x + getWidth() / 2;
    }

    public void setY(float y) {
        super.setY(y);
        camera.position.y = y + getHeight() / 2;
    }

    @Override
    public void setBounds(float x, float y, float w, float h) {
        super.setBounds(x, y, w, h);
        setX(x);
        setY(y);
    }


}