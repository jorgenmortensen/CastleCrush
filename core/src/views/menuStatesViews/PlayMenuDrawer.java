package views.menuStatesViews;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.castlecrush.game.CastleCrush;

import controllers.menuStatesControllers.PlayMenuController;
import models.states.GameStateManager;
import models.states.menuStates.MultiplayerMenu;
import models.states.menuStates.PlayMenu;
import models.states.menuStates.StartMenuScreen;
import models.states.playStates.SinglePlayerState;
import views.Drawer;

import static models.states.menuStates.StartMenuScreen.logo;

/**
 * Created by Jørgen on 17.04.2018.
 */

public class PlayMenuDrawer extends PlayMenu {

    public PlayMenuDrawer() {
        super();
    }

    public void update(float dt) {
        btnSingle.update(dt);
        btnMulti.update(dt);
        btnLocal.update(dt);

        moveBackground();
        logoCrush();
    }

    public void render(SpriteBatch batch) {
        batch.begin();

        //Draw the moving background
        batch.draw(background1, CastleCrush.xCoordBg1, 0, CastleCrush.WIDTH, CastleCrush.HEIGHT);
        batch.draw(background2, CastleCrush.xCoordBg2, 0, CastleCrush.WIDTH, CastleCrush.HEIGHT);

        batch.draw(btnSingle.getBtn(), btnSingle.getPosition().x, btnSingle.getPosition().y,
            btnSingle.getBtnWidth(), btnSingle.getBtnHeight());

        batch.draw(btnMulti.getBtn(), btnMulti.getXpos(), btnMulti.getYpos(),
                btnMulti.getBtnWidth(), btnMulti.getBtnHeight());

        batch.draw(btnLocal.getBtn(), btnLocal.getXpos(), btnLocal.getYpos(),
                btnLocal.getBtnWidth(), btnLocal.getBtnHeight());

        batch.draw(btnSettings.getBtn(), btnSettings.getXpos(), btnSettings.getYpos(),
                btnSettings.getBtnWidth(), btnSettings.getBtnHeight());

        batch.draw(logo, 0, CastleCrush.HEIGHT * 7 / 10, CastleCrush.WIDTH, 3*CastleCrush.HEIGHT/10);

        batch.draw(btnSound.getBtn(), btnSound.getXpos(), btnSound.getYpos(),btnSound.getBtnWidth(), btnSound.getBtnHeight());

        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
