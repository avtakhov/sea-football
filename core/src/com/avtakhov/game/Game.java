package com.avtakhov.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

public class Game extends ApplicationAdapter {
	private Texture img;
	private Stage stage;
	private OrthographicCamera camera;
	MainShip main;
	RenderObject back;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 600,  600);
		img = new Texture("badlogic.jpg");
		stage = new Stage();
		back = new RenderObject(new Texture("back.jpg"));
		main = new MainShip(new Texture("main.png"), camera);
		Actor kek = new RenderObject(img);
		main.setBounds(camera.position.x - 50,camera.position.y - 50, 50, 150);
		kek.setBounds(0, 0, 100, 100);
		back.setBounds(0 , 0, 1000, 1000);
		stage.addActor(back);
		stage.addActor(kek);
		stage.addActor(main);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		Batch batch = stage.getBatch();
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		stage.act();
		batch.end();

		for (Actor e : stage.getActors()) {
			if (e == main || e == back) {
				continue;
			}
			e.setX(e.getX() + Gdx.graphics.getDeltaTime() * 8);
		}
		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			main.setX(main.getX() - Gdx.input.getDeltaX());
			main.setY(main.getY() + Gdx.input.getDeltaY());
		}
	}
	
	@Override
	public void dispose () {
		img.dispose();
	}
}
