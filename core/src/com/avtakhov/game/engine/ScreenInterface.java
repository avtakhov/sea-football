package com.avtakhov.game.engine;

import com.avtakhov.game.game_objects.Button;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

public interface ScreenInterface {
    default Button createButton(Stage stage, String buttonName, float x, float y, float width, float height) {
        Button button = new Button(new Texture(buttonName));
        button.setBounds(x, y, width, height);
        stage.addActor(button);
        return button;
    }
}
