package controllers.menuStatesControllers;

import com.badlogic.gdx.Gdx;
import com.castlecrush.game.CastleCrush;

import models.states.GameStateManager;
import models.states.TutorialState;
import models.states.menuStates.PlayMenu;
import models.states.menuStates.StartMenuScreen;

import static com.castlecrush.game.CastleCrush.xCoordBg1;
import static com.castlecrush.game.CastleCrush.xCoordBg2;

/**
 * Created by Jørgen on 19.04.2018.
 */

public class StartMenuController extends StartMenuScreen {

    GameStateManager gsm;

    public StartMenuController(GameStateManager gsm) {
        super();
        this.gsm = gsm;
    }

    public void handleInput() {
        if (Gdx.input.justTouched() && isOnButton(btnPlay)) {
            gsm.set(new PlayMenu(gsm));
        }
        else if (Gdx.input.justTouched() && isOnButton(btnHelp)) {
            gsm.set(new TutorialState(gsm));
            dispose();
        }
        else if (Gdx.input.justTouched() && isOnButton(btnSound)) {
            //Turn off sound if already on and vice versa
            soundSwitch();
        }
        else if (Gdx.input.justTouched() && isOnButton(btnSettings)) {
            System.out.println(gsm);
            toSettingsState(gsm);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
