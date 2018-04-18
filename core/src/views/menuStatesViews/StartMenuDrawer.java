package views.menuStatesViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.castlecrush.game.CastleCrush;

import models.states.GameStateManager;
import models.states.TutorialState;
import models.states.menuStates.PlayMenu;
import models.states.menuStates.StartMenuScreen;
import views.Drawer;

import static com.castlecrush.game.CastleCrush.xCoordBg1;
import static com.castlecrush.game.CastleCrush.xCoordBg2;

/**
 * Created by Jørgen on 17.04.2018.
 */

public class StartMenuDrawer extends StartMenuScreen {

    GameStateManager gsm;

    public StartMenuDrawer(GameStateManager gsm) {
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
            super.toSettingsState(gsm);
        }
    }

    public void update(float dt) {
        handleInput();

        //makes the background move to the left
        xCoordBg1 += CastleCrush.BACKGROUND_MOVE_SPEED * Gdx.graphics.getDeltaTime();
        xCoordBg2 = xCoordBg1 - xMax;  // We move the background, not the camera
        if (CastleCrush.xCoordBg1 <= 0) {
            CastleCrush.xCoordBg1 = xMax;
            xCoordBg2 = 0;
        }
        logoCrush();
    }

    public void render(SpriteBatch batch) {
        batch.begin();

        batch.draw(background1, CastleCrush.xCoordBg1, 0, CastleCrush.WIDTH, CastleCrush.HEIGHT);
        batch.draw(background2, CastleCrush.xCoordBg2, 0, CastleCrush.WIDTH, CastleCrush.HEIGHT);

        batch.draw(btnPlay.getBtn(), btnPlay.getXpos(), btnPlay.getYpos(),
                btnPlay.getBtnWidth(), btnPlay.getBtnHeight());

        batch.draw(btnHelp.getBtn(), btnHelp.getXpos(), btnHelp.getYpos(),
                btnHelp.getBtnWidth(), btnHelp.getBtnHeight());

        batch.draw(btnSound.getBtn(), btnSound.getXpos(), btnSound.getYpos(),
                btnSound.getBtnWidth(), btnSound.getBtnHeight());

        batch.draw(btnSettings.getBtn(), btnSettings.getXpos(), btnSettings.getYpos(),
                btnSettings.getBtnWidth(), btnSettings.getBtnHeight());

        batch.draw(logo, 0, CastleCrush.HEIGHT * 7 / 10, CastleCrush.WIDTH, CastleCrush.HEIGHT * 3 / 10);

        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
