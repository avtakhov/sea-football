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
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("ostrov_sokrovishc_02.mp3"));
        idMusic = sound.play(1.0f); // play new sound and keep handle for further manipulation
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 2000, 1252);
        stage = new Stage();
        back = new RenderObject(new Texture("menunew.png"));
        back.setBounds(1000, 626, 2000, 1252);
        stage.addActor(back);
        Button singleGame = createButton(stage, "button.png", back.getWidth() / 3,
                back.getHeight() / 5, 450, 200);
        Button multiPlayerGame = createButton(stage, "button.png", back.getWidth() / 3,
                back.getHeight() / 2, 450, 200);
        stage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println(x + " " + y);
                if (x >= 211 && x <= 355 && y >= 241 && y <= 316) {
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
