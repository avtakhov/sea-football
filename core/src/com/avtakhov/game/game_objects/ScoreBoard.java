package com.avtakhov.game.game_objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class ScoreBoard extends RenderObject {
    private int LEFT_SCORE;
    private int RIGHT_SCORE;
    private final BitmapFont FONT = new BitmapFont();

    public ScoreBoard(Texture img) {
        super(img);
        LEFT_SCORE = RIGHT_SCORE = 0;
    }

    @Override
    public void act(float time) {
        super.act(time);
        FONT.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        FONT.getData().setScale(4);
        FONT.draw(getStage().getBatch(), LEFT_SCORE + " : " + RIGHT_SCORE, getX() - 75, getY());
    }

    public int getLEFT_SCORE() {
        return LEFT_SCORE;
    }

    public int getRIGHT_SCORE() {
        return RIGHT_SCORE;
    }

    public void setLEFT_SCORE(int LEFT_SCORE) {
        this.LEFT_SCORE = LEFT_SCORE;
    }

    public void setRIGHT_SCORE(int RIGHT_SCORE) {
        this.RIGHT_SCORE = RIGHT_SCORE;
    }
}
