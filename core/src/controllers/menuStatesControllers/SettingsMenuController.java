package controllers.menuStatesControllers;

import com.badlogic.gdx.Gdx;

import models.states.GameStateManager;
import models.states.menuStates.SettingsMenu;

/**
 * Created by Jørgen on 19.04.2018.
 */

public class SettingsMenuController extends SettingsMenu {

    GameStateManager gsm;

    public SettingsMenuController(GameStateManager gsm) {
        super();
        this.gsm = gsm;
    }

    public void handleInput() {
        if (Gdx.input.justTouched() && isOnButton(backBtn)) {
            this.gsm.pop();
        }
    }

    public void update(float dt) {
        handleInput();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
