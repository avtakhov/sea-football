
package com.avtakhov.game.game_objects;

import com.badlogic.gdx.graphics.Texture;

public class Ball extends RenderObject {
    private float z;
    private float speedZ;
    private int rotDir;

    public Ball(Texture texture) {
        super(texture);
        z = 0;
        speedZ = 0;
        rotDir = 0;
    }

    public void collide(Bullet bullet) {
        if (!bullet.isToDelete()) {
            float speedX = bullet.getSpeedX();
            float speedY = bullet.getSpeedY();
            float x = bullet.getX();
            float y = bullet.getY();
            float xDiff = (x - getX()) * (x - getX());
            float yDiff = (y - getY()) * (y - getY());
            float rad2 = getWidth() * getWidth() / 4;
            if (xDiff + yDiff < rad2) {
                this.speedX += speedX / 2;
                this.speedY += speedY / 2;
                bullet.toDelete = true;
                this.speedZ = 0.5f;
            }
        }
    }

    public void collide(MainShip ship) {
        float speedX = ship.getSpeedX();
        float speedY = ship.getSpeedY();
        float x = ship.getX();
        float y = ship.getY();
        float xDiff = (x - getX()) * (x - getX());
        float yDiff = (y - getY()) * (y - getY());
        float rad2 = ship.getWidth() * ship.getWidth() / 6;
        if (xDiff + yDiff < rad2) {
            this.speedX += speedX;
            this.speedY += speedY;
            if (z < 1) {
                rotDir = (int) (Math.random() * 3) - 1;
                this.speedZ = (Math.abs(speedX) + Math.abs(speedY)) / 3;
            }

        }
    }

    public float getZ() {
        return z;
    }

    public float getSpeedZ() {
        return speedZ;
    }

    public void setSpeedZ(float speedZ) {
        this.speedZ = speedZ;
    }

    public void setZ(float z) {
        this.z = z;
    }

    @Override
    public void act(float time) {
        img.setScale(1 + z / 10);
        super.act(time);
        speedX *= 0.99f;
        speedY *= 0.99f;
        z += speedZ;
        if (z > 0) {
            setRotation(getRotation() + rotDir * 3);
            speedZ -= 0.05f;
        } else {
            speedZ = 0;
            z = 0;
        }
        move();
    }
}