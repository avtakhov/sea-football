package com.avtakhov.game.engine;

import com.avtakhov.game.game_objects.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.Random;

public class GameScreen implements Screen, ScreenInterface {
    private Stage stage;
    private OrthographicCamera camera;

    MainShip main;
    RenderObject back;
    Ball ball;
    RenderObject arrow;
    BotShip bot;
    Random random;

    public GameScreen(Game aGame) {
        random = new Random();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        stage = new Stage();
        back = new RenderObject(new Texture("back.png"));
        main = new MainShip(new Texture("main.png"), camera);
        main.setBounds(camera.position.x, camera.position.y, 128, 40);
        back.setBounds(1000, 626, 2000, 1252);
        RenderObject backBack = new RenderObject(new Texture("back_back.png"));
        backBack.setBounds(0, 0, 4000, 4000);
        stage.addActor(backBack);
        stage.addActor(back);
        stage.addActor(main);
        arrow = new RenderObject(new Texture("arrow.png"));
        ball = new Ball(new Texture("ball.png"));
        ball.setBounds(1000, 626, 40, 40);
        ball.setPosition(ball.getX(), ball.getY());
        bot = new BotShip(new Texture("main.png"), ball);
        bot.setBounds(camera.position.x - 50, camera.position.y - 50, 128, 40);
        stage.addActor(bot);
        arrow.setBounds(main.getX(), main.getY(), 32, 32);
        stage.addActor(arrow);
        stage.addActor(ball);
    }

    private long start = System.currentTimeMillis();

    public void sleep(int fps) {
        if (fps > 0) {
            long diff = System.currentTimeMillis() - start;
            long targetDelay = 1000 / fps;
            if (diff < targetDelay) {
                try {
                    Thread.sleep(targetDelay - diff);
                } catch (InterruptedException ignored) {
                }
            }
            start = System.currentTimeMillis();
        }
    }

    @Override
    public void render(float delta) {
        sleep(60);
        Gdx.gl.glClearColor(0.28f, 0.45f, 0.67f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        Batch batch = stage.getBatch();
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        stage.act();
        batch.end();

        for (Actor e : stage.getActors()) {
            if (!e.equals(back) && e instanceof RenderObject && !(e instanceof Bullet)) {
                if (e instanceof Ball) {
                    checkBounds((Ball) e);
                    continue;
                }
                checkBounds((RenderObject) e);
            }
            if (e instanceof Bullet) {
                ball.collide((Bullet) e);
                checkBounds(ball);
            }
            if (e instanceof MainShip) {
                ball.collide((MainShip) e);
                checkBounds(ball);
            }
        }
        moveArrow();
        moveShip();
        moveBotShip();
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
            createBullet(90, main);
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            createBullet(-90, main);
        }
    }

    private void moveBotShip() {
        //checkBounds(bot);
        if (random.nextInt() % 27 == 0) {
            createBullet(90, bot);
            createBullet(-90, bot);
        }
        //System.out.println(((ball.getX() - bot.getX()) * bot.getSpeedX() + (ball.getY() - bot.getY()) * bot.getSpeedY()));
        // TODO Math update
        if (((ball.getX() - bot.getX()) * bot.getSpeedX() + (ball.getY() - bot.getY()) * bot.getSpeedY()) < 0 || bot.getLastX() == bot.getX() &&
                                                                                                                    bot.getLastY() == bot.getY()) {
            bot.rotateBy(10);
        }
        bot.setLastX(bot.getX());
        bot.setLastY(bot.getY());
    }


    private void moveArrow() {
        double dist = Math.sqrt(Math.pow(ball.getX() - main.getX(), 2) + Math.pow(ball.getY() - main.getY(), 2));
        double cos = (ball.getX() - main.getX()) / dist;
        double sin = (ball.getY() - main.getY()) / dist;
        arrow.setPosition((float) (main.getX() + cos * main.getHeight() * 2),
                (float) (main.getY()  + sin * main.getHeight() * 2));
        float degrees = (float) Math.toDegrees(Math.acos(cos));
        if (sin < 0) {
            degrees *= -1;
        }
        arrow.setRotation(degrees);
    }

    private void createBullet(float rotation, Ship ship) {
        Bullet bullet = new Bullet(new Texture("bullet1.png"));
        bullet.setBounds(ship.getX(), ship.getY(), 10, 10);
        bullet.setRotation(ship.getRotation() + rotation);
        stage.addActor(bullet);
    }

    private void checkBounds(RenderObject object) {
        float newX = object.getX() + object.getSpeedX();
        float newY = object.getY() + object.getSpeedY();
        if (newX >= back.getWidth() || newX <= 0 || newY >= back.getHeight() || newY <= 0) {
            object.setX(object.getX() - object.getSpeedX());
            object.setY(object.getY() - object.getSpeedY());
        }
    }

    private void checkBounds(Ball object) {
        float newX = object.getX() + object.getSpeedX();
        float newY = object.getY() + object.getSpeedY();
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