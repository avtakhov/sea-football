package com.avtakhov.game.game_objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Goal extends RenderObject {
    int counter;

    public Goal(Texture texture) {
        super(texture);
        counter = 0;

    }

    public boolean toDestroy() {
        return counter > 50;
    }

    @Override
    public void act(float time) {
        super.act(time);
        counter++;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public Goal(Sprite img) {
        super(img);
    }
}
