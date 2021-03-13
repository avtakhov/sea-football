package com.avtakhov.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class RenderObject extends Actor {
    protected final Sprite img;

    RenderObject(Sprite img) {
        this.img = img;
    }

    RenderObject(Texture texture) {
        this(new Sprite(texture));
    }

    @Override
    public void act(float time) {
        img.setRotation(getRotation());
        img.setOrigin(getOriginX() + getWidth() / 2, getOriginY() + getHeight() / 2);
        img.setSize(getWidth(), getHeight());
        img.setPosition(getX(), getY());
        img.draw(getStage().getBatch());
    }

}
