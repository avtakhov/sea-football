package com.avtakhov.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Game extends ApplicationAdapter {
    private Stage stage;
    private OrthographicCamera camera;
    MainShip main;
    RenderObject back;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        stage = new Stage();
        back = new RenderObject(new Texture("back.png"));
        main = new MainShip(new Texture("main.png"), camera);
        main.setBounds(camera.position.x - 50, camera.position.y - 50, 128, 40);
        back.setBounds(0, 0, 2000, 1252);
        stage.addActor(back);
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
        moveShip();
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            main.setX(main.getX() - Gdx.input.getDeltaX());
            main.setY(main.getY() + Gdx.input.getDeltaY());
        }
    }

    private void moveShip() {
    	main.move();
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            main.move();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
        	main.rotateBy(-1);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			main.rotateBy(1);
		}
    }

    @Override
    public void dispose() {
    }
}
