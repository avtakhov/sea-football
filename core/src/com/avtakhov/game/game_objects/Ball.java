
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
                this.speedX += speedX;
                this.speedY += speedY;
            }
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