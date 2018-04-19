package controllers.menuStatesControllers;

import com.badlogic.gdx.Gdx;
import com.castlecrush.game.CastleCrush;

import models.states.GameStateManager;
import models.states.TutorialState;
import models.states.menuStates.MultiplayerMenu;
import models.states.menuStates.PlayMenu;
import models.states.menuStates.StartMenuScreen;
import models.states.playStates.SinglePlayerState;

import static com.castlecrush.game.CastleCrush.xCoordBg1;
import static com.castlecrush.game.CastleCrush.xCoordBg2;

/**
 * Created by Jørgen on 19.04.2018.
 */

public class PlayMenuController extends PlayMenu{

    GameStateManager gsm;

    public PlayMenuController(GameStateManager gsm) {
        super();
        this.gsm = gsm;
    }

    public void handleInput() {
        System.out.println("Controller.handleInput kjorer");
        if (Gdx.input.justTouched() && isOnButton(btnSingle)) {
            System.out.println("single");
            gsm.set(new SinglePlayerState(gsm));
            System.out.println("Single pressed");
            dispose();
        }
        else if (Gdx.input.justTouched() && isOnButton(btnMulti)) {
            System.out.println("multi");
            gsm.set(new MultiplayerMenu(gsm));
            System.out.println("Multi pressed");
            dispose();
        }
        else if (Gdx.input.justTouched() && isOnButton(btnLocal)) {
            System.out.println("local");
            gsm.set(new StartMenuScreen(gsm));
            System.out.println("Local pressed");
            dispose();
        } else if (Gdx.input.justTouched() && isOnButton(btnSound)) {
            System.out.println("sound");
            //Turn off sound if already on and vice versa
            soundSwitch();
        } else if (Gdx.input.justTouched() && isOnButton(btnSettings)) {
            System.out.println("settings");
            toSettingsState(gsm);
        }
    }

    public void update(float dt) {
        handleInput();
        btnSingle.update(dt);
        btnMulti.update(dt);
        btnLocal.update(dt);

        moveBackground();
        logoCrush();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
