package com.avtakhov.game.desktop;

import com.avtakhov.game.engine.Menu;
import com.badlogic.gdx.Game;

public class MyGame extends Game {

    @Override
    public void create() {
        this.setScreen(new Menu(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
    }
}
