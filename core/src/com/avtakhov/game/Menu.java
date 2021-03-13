package com.avtakhov.game;

import com.avtakhov.game.engine.GameScreen;
import com.avtakhov.game.game_objects.MainShip;
import com.avtakhov.game.game_objects.RenderObject;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class Menu implements Screen {
    private Stage stage;
    private OrthographicCamera camera;
    MainShip main;
    RenderObject back;
    Button playButton;

    public Menu(Game aGame) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 2000, 1252);
        stage = new Stage();
        back = new RenderObject(new Texture("menu.jpg"));
        back.setBounds(1000, 626, 2000, 1252);
        stage.addActor(back);
        playButton = new Button(new Texture("img.png"));
        playButton.setBounds(back.getWidth() / 2 - 200, back.getHeight() / 20, 300, 200);
        stage.addActor(playButton);
        stage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println(x + " " + y);
                if (x >= 255 && x <= 350 && y >= 29 && y <= 98) {
                    aGame.setScreen(new GameScreen(aGame));
                }
            }
        });
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        Batch batch = stage.getBatch();
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        stage.act();
        batch.end();
    }

    @Override
    public void show() {

    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
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
