package com.avtakhov.game.desktop;

import com.avtakhov.game.Menue;
import com.avtakhov.game.engine.GameScreen;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class MyGame extends Game {

	@Override
	public void create() {
		this.setScreen(new Menue(this));
	}

	public void render () {
		super.render();
	}

	public void dispose () {
	}
}
