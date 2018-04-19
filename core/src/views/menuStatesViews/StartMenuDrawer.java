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

    public StartMenuDrawer() {
        super();
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
