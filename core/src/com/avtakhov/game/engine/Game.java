package com.avtakhov.game.engine;

import com.avtakhov.game.game_objects.Ball;
import com.avtakhov.game.game_objects.Bullet;
import com.avtakhov.game.game_objects.MainShip;
import com.avtakhov.game.game_objects.RenderObject;
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
        Ball ball = new Ball(new Texture("ball.png"));
        ball.setBounds(1000, 626, 40, 40);
        stage.addActor(ball);
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
            //e.setX(e.getX() + Gdx.graphics.getDeltaTime() * 8);
        }
        moveShip();
    }

    private void moveShip() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            main.move();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            main.rotateBy(-1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            main.rotateBy(1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            Bullet bullet = new Bullet(new Texture("bullet1.png"));
            bullet.setBounds(main.getX() + main.getWidth() / 2, main.getY() + main.getHeight() / 2, 10, 10);
            bullet.setRotation(main.getRotation() + 90);
            stage.addActor(bullet);
        }
    }

    @Override
    public void dispose() {
    }
}
