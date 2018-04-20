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
        if (Gdx.input.justTouched() && isOnButton(btnHome)) {
            try {
                this.gsm.pop();
            } catch (Exception e) {
                CastleCrush.playServices.toast("Unable to go home");
                e.printStackTrace();
            }
        }
        else if (Gdx.input.justTouched() && isOnButton(btnSound)) {
            //Switch sound on if off and vice versa
            if (CastleCrush.soundOn) {
                CastleCrush.music.setVolume(0);
                CastleCrush.soundOn = false;
                btnSound.setBtn(new Sprite(new Texture("sound_off.png")));
            } else {
                CastleCrush.music.setVolume(0.5f);
                CastleCrush.soundOn = true;
                btnSound.setBtn(new Sprite(new Texture("sound_on.png")));
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
