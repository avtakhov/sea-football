package com.avtakhov.game.game_objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Bullet extends RenderObject {
    protected float SPEED = 10;
    protected int MAX_DISTANCE;
    private float currentDistance;
    public boolean toDelete;

    public Bullet(Texture img) {
        super(new Sprite(img));
        toDelete = false;
        currentDistance = 0;
        MAX_DISTANCE = 400;
    }

    @Override
    public void act(float time) {
        if (toDelete) {
            return;
        }
        super.act(time);
        float moveX = SPEED * (float) Math.cos(Math.toRadians(getRotation()));
        float moveY = SPEED * (float) Math.sin(Math.toRadians(getRotation()));
        speedX = moveX;
        speedY = moveY;
        setX(getX() + moveX);
        setY(getY() + moveY);
        currentDistance += (Math.abs(moveX) + Math.abs(moveY));
        if (currentDistance >= MAX_DISTANCE) {
            toDelete = true;
        }
    }

    public boolean isToDelete() {
        return toDelete;
    }
}