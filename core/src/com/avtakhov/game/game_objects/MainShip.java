package com.avtakhov.game.game_objects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class MainShip extends Ship {

    OrthographicCamera camera;
    private String text = "";
    private int color;
    public MainShip(Texture img, OrthographicCamera camera) {
        super(img);
        this.camera = camera;
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        camera.position.x = x;
    }

    public void setY(float y) {
        super.setY(y);
        camera.position.y = y;
    }


    public int getColor1() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}