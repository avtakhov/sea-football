package com.avtakhov.game.engine;

import com.avtakhov.game.game_objects.Button;
import com.avtakhov.game.game_objects.MainShip;
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


public class Menu implements Screen, ScreenInterface {
    private Stage stage;
    private OrthographicCamera camera;
    RenderObject back;
    Button playButton;
    Button musicButton;
    private static boolean isMusic = true;
    long idMusic;
    public Menu(Game aGame) {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("ostrov_sokrovishc_02.mp3"));
        idMusic = sound.play(1.0f); // play new sound and keep handle for further manipulation
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 2000, 1252);
        stage = new Stage();
        back = new RenderObject(new Texture("menu.jpg"));
        back.setBounds(1000, 626, 2000, 1252);
        stage.addActor(back);
        playButton = createButton(stage, "button.png", back.getWidth() / 3,
                                                back.getHeight() / 20, 450, 200);
        musicButton = createButton(stage, "music.jpg", back.getWidth() - 300,
                                                back.getHeight() - 200, 300, 200);
        stage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (x >= 255 && x <= 350 && y >= 29 && y <= 98) {
                    aGame.setScreen(new GameType(aGame));
                }
                if (x >= 542 && x <= 640 && y >= 402 && y <= 480) {
                    if (isMusic) {
                        sound.stop(idMusic);
                        musicButton.remove();
                        musicButton = createButton(stage, "musicoff.jpg", back.getWidth() - 300, back.getHeight() - 200, 300, 200);
                        isMusic = false;
                    } else {
                        idMusic = sound.play(1.0f);
                        musicButton.remove();
                        musicButton = createButton(stage, "music.jpg", back.getWidth() - 300, back.getHeight() - 200, 300, 200);
                        isMusic = true;
                    }
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
