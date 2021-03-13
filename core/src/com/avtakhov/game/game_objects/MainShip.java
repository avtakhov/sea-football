package com.avtakhov.game.game_objects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class MainShip extends RenderObject {

    OrthographicCamera camera;
    private float x;
    private float y;
    private float rotation;
    private final float SPEED = 1.5f;

    public MainShip(Texture img, OrthographicCamera camera) {
        super(new Sprite(img));
        this.camera = camera;
    }
    @Override
    public void move() {
        setX(getX() + speedX);
        setY(getY() + speedY);
    }

    @Override
    public void act(float time) {
        super.act(time);
        speedX = SPEED * (float) Math.cos(Math.toRadians(getRotation()));
        speedY = SPEED * (float) Math.sin(Math.toRadians(getRotation()));
        move();
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