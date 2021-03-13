package com.avtakhov.game.game_objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class RenderObject extends Actor {
    protected final Sprite img;
    protected float speedX;
    protected float speedY;

    public RenderObject(Sprite img) {
        this.img = img;
        speedX = 0;
        speedY = 0;
    }

    public void move() {
        setX(getX() + speedX);
        setY(getY() + speedY);
    }

    public RenderObject(Texture texture) {
        this(new Sprite(texture));
    }

    public float getCenterX() {
        return getX() + getWidth() / 2;
    }

    public float getCenterY() {
        return getY() + getHeight() / 2;
    }

    @Override
    public void act(float time) {
        img.setRotation(getRotation());
        img.setOrigin(getOriginX() + getWidth() / 2, getOriginY() + getHeight() / 2);
        img.setSize(getWidth(), getHeight());
        img.setPosition(getX(), getY());
        img.draw(getStage().getBatch());
    }

    public float getSpeedX() {
        return speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }
}
