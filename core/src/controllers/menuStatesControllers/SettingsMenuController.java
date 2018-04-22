package controllers.menuStatesControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.castlecrush.game.CastleCrush;

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
        if (Gdx.input.justTouched() && isOnButton(returnBtn)) {
            try {
                this.gsm.pop();
            } catch (Exception e) {
                CastleCrush.playServices.toast("Unable to go home");
                e.printStackTrace();
            }
        }
        else if (Gdx.input.justTouched() && isOnButton(btnMusic)) {
            //Switch sound on if off and vice versa
            if (CastleCrush.musicOn) {
                CastleCrush.music.setVolume(0);
                CastleCrush.musicOn = false;
                setMusic();
            } else {
                CastleCrush.music.setVolume(0.5f);
                CastleCrush.musicOn = true;
                setMusic();
            }
        } else if (Gdx.input.justTouched() && isOnButton(btnSound)) {
            //Switch sound on if off and vice versa
            if (CastleCrush.soundEffectsOn) {
                CastleCrush.soundEffectsOn = false;
                setSound();
            } else {
                CastleCrush.soundEffectsOn = true;
                setSound();
            }
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
