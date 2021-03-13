package com.avtakhov.game.engine;

import com.avtakhov.game.game_objects.Ball;
import com.avtakhov.game.game_objects.Bullet;
import com.avtakhov.game.game_objects.MainShip;
import com.avtakhov.game.game_objects.RenderObject;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameScreen implements Screen {
    private Stage stage;
    private OrthographicCamera camera;

    MainShip main;
    RenderObject back;
    Ball ball;

    public GameScreen(Game aGame) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        stage = new Stage();
        back = new RenderObject(new Texture("back.png"));
        main = new MainShip(new Texture("main.png"), camera);
        main.setBounds(camera.position.x - 50, camera.position.y - 50, 128, 40);
        back.setBounds(0, 0, 2000, 1252);
        RenderObject backBack = new RenderObject(new Texture("back_back.png"));
        backBack.setBounds(-1000, -1000, 4000, 4000);
        stage.addActor(backBack);
        stage.addActor(back);
        stage.addActor(main);
        ball = new Ball(new Texture("ball.png"));
        ball.setBounds(1000, 626, 40, 40);

        stage.addActor(ball);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.28f, 0.45f, 0.67f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        Batch batch = stage.getBatch();
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        stage.act();
        batch.end();

        for (Actor e : stage.getActors()) {
            if (!e.equals(back) && e instanceof RenderObject) {
                if (e instanceof Ball) {
                    checkBounds((Ball) e);
                    continue;
                }
                checkBounds((RenderObject) e);
            }
            if (e instanceof Bullet) {
                ball.collide((Bullet) e);
            }
        }
        moveShip();
    }

    private void moveShip() {
        checkBounds(main);
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            main.move();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            main.rotateBy(-1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            main.rotateBy(1);
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            createBullet(90);
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            createBullet(-90);
        }
    }

    private void createBullet(float rotation) {
        Bullet bullet = new Bullet(new Texture("bullet1.png"));
        bullet.setBounds(main.getX() + main.getWidth() / 2, main.getY() + main.getHeight() / 2, 10, 10);
        bullet.setRotation(main.getRotation() + rotation);
        stage.addActor(bullet);
    }

    private void checkBounds(RenderObject object) {
        float newX = object.getCenterX() + object.getSpeedX();
        float newY = object.getCenterY() + object.getSpeedY();
        if (newX >= back.getWidth() || newX <= 0 || newY >= back.getHeight() || newY <= 0) {
            object.setX(object.getX() - object.getSpeedX());
            object.setY(object.getY() - object.getSpeedY());
        }
    }

    private void checkBounds(Ball object) {
        float newX = object.getCenterX() + object.getSpeedX();
        float newY = object.getCenterY() + object.getSpeedY();
        if (newX >= back.getWidth() || newX <= 0 || newY >= back.getHeight() || newY <= 0) {
            if (newX >= back.getWidth() || newX <= 0) {
                object.setSpeedX(-object.getSpeedX() / 2);
                object.setSpeedY(object.getSpeedY() / 2);
            } else {
                object.setSpeedX(object.getSpeedX() / 2);
                object.setSpeedY(-object.getSpeedY() / 2);
            }
        }
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}
