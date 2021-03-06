package com.avtakhov.game.game_objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class RenderObject extends Actor {
    protected final Sprite img;
    protected Sprite shadow = null;
    protected float speedX;
    protected float speedY;

    public RenderObject(Sprite img) {
        this.img = img;
        //  this.SPEED = SPEED;
        speedX = 0;
        speedY = 0;
    }

    public RenderObject(Texture texture) {
        this(new Sprite(texture));
    }

    public void move() {
        setX(getX() + speedX);
        setY(getY() + speedY);
    }

    public void setShadow(Texture shadow) {
        this.shadow = new Sprite(shadow);
    }

    private void absorbImage(Sprite img) {
        img.setRotation(getRotation());
        img.setOrigin(getOriginX() + getWidth() / 2, getOriginY() + getHeight() / 2);
        img.setSize(getWidth(), getHeight());
        img.setPosition(getX() - getWidth() / 2, getY() - getHeight() / 2);
        img.draw(getStage().getBatch());

    }

    @Override
    public void act(float time) {
        if (shadow != null) {
            absorbImage(shadow);
        }
        absorbImage(img);
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