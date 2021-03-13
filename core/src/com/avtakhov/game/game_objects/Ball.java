
package com.avtakhov.game.game_objects;

import com.badlogic.gdx.graphics.Texture;

public class Ball extends RenderObject {
    public Ball(Texture texture) {
        super(texture);
    }

    public void collide(Bullet bullet) {
        if (!bullet.isToDelete()) {
            float speedX = bullet.getSpeedX();
            float speedY = bullet.getSpeedY();
            float x = bullet.getX();
            float y = bullet.getY();
            float xDiff = (x - getCenterX()) * (x - getCenterX());
            float yDiff = (y - getCenterY()) * (y - getCenterY());
            float rad2 = getWidth() * getWidth() / 4;
            if (xDiff + yDiff < rad2) {
                this.speedX += speedX / 2;
                this.speedY += speedY / 2;
                bullet.toDelete = true;
            }
        }
    }

    public void collide(MainShip ship) {
        float speedX = ship.getSpeedX();
        float speedY = ship.getSpeedY();
        float x = ship.getCenterX();
        float y = ship.getCenterY();
        float xDiff = (x - getCenterX()) * (x - getCenterX());
        float yDiff = (y - getCenterY()) * (y - getCenterY());
        float rad2 = ship.getWidth() * ship.getWidth() / 6;
        if (xDiff + yDiff < rad2) {
            this.speedX += speedX / 2;
            this.speedY += speedY / 2;
        }
    }

    @Override
    public void act(float time) {
        super.act(time);
        speedX *= 0.99f;
        speedY *= 0.99f;
        move();
    }
}