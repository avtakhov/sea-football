package com.avtakhov.game.engine;

import com.avtakhov.game.game_objects.Button;
import com.avtakhov.game.game_objects.RenderObject;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameType implements Screen, ScreenInterface {
    private Stage stage;
    private OrthographicCamera camera;
    RenderObject back;
    private static boolean isMusic = true;
    long idMusic;
    public GameType(Game aGame) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 2000, 1252);
        stage = new Stage();
        back = new RenderObject(new Texture("menunew.png"));
        back.setBounds(1000, 626, 2000, 1252);
        stage.addActor(back);
        Button singleGame = createButton(stage, "single.png", back.getWidth() / 3 + 100,
                back.getHeight() / 3, 450, 200);
        Button multiPlayerGame = createButton(stage, "multi.png", back.getWidth() / 3 + 100,
                back.getHeight() / 8, 450, 200);
        stage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println(x + " " + y);
                if (x >= 245 && x <= 390 && y >= 160 && y <= 235) {
                    aGame.setScreen(new GameScreen(aGame, true));
                }
                if (x >= 245 && x <= 390 && y >= 60 && y <= 136) {
                    aGame.setScreen(new GameScreen(aGame, false));
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
