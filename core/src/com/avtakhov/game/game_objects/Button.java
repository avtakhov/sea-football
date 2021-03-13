package com.avtakhov.game.game_objects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Button extends Actor {

    private final Texture img;

    public Button(Texture img) {
        this.img = img;
    }

    @Override
    public void act(float time) {
        getStage().getBatch().draw(img, getX(), getY(), getWidth(), getHeight());
    }

}
