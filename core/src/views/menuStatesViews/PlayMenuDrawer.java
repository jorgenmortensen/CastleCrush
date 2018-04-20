package views.menuStatesViews;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.castlecrush.game.CastleCrush;

import controllers.menuStatesControllers.PlayMenuController;
import models.states.GameStateManager;
import models.states.menuStates.MultiplayerMenu;
import models.states.menuStates.PlayMenu;
import models.states.menuStates.StartMenuScreen;
import models.states.playStates.SinglePlayerState;
import views.Drawer;

import static models.states.menuStates.StartMenuScreen.changed_logo;
import static models.states.menuStates.StartMenuScreen.crushed;
import static models.states.menuStates.StartMenuScreen.little_crushed;
import static models.states.menuStates.StartMenuScreen.logo;
import static models.states.menuStates.StartMenuScreen.startTime;
import static models.states.menuStates.StartMenuScreen.with_u;
import static models.states.menuStates.StartMenuScreen.without_castle;

/**
 * Created by Jørgen on 17.04.2018.
 */

public class PlayMenuDrawer extends PlayMenu {

    public PlayMenuDrawer() {
        super();
    }

    public void update(float dt) {
        btnLocal.update(dt);
        btnMulti.update(dt);
        btnBack.update(dt);

        //Makes the background move to the left
        xCoordBg1 += BACKGROUND_MOVE_SPEED * Gdx.graphics.getDeltaTime();
        xCoordBg2 = xCoordBg1 - xMax;  // We move the background, not the camera
        if (xCoordBg1 <= 0) {
            xCoordBg1 = xMax;
            xCoordBg2 = 0;
        }

        long time = TimeUtils.timeSinceMillis(startTime);
        if (time > 3500 && little_crushed) {
            logo = new Texture("logo_little_crushed.png");;;
            little_crushed = false;
        } else if (time > 7000 && crushed) {
            logo = new Texture("logo_crushed.png");;
            crushed = false;
        } else if (time > 10000  && without_castle) {
            logo = new Texture("logo_without_castle.png");;
            without_castle = false;
        } else if (time > 13000 && with_u) {
            logo = new Texture("logo_with_u.png");;
            with_u = false;
        }
    }

    public void render(SpriteBatch batch) {
        batch.begin();

        //Draw the moving background
        batch.draw(background1, xCoordBg1, 0, CastleCrush.WIDTH, CastleCrush.HEIGHT);
        batch.draw(background2, xCoordBg2, 0, CastleCrush.WIDTH, CastleCrush.HEIGHT);

        batch.draw(btnLocal.getBtn(), btnLocal.getPosition().x, btnLocal.getPosition().y,
            btnLocal.getBtnWidth(), btnLocal.getBtnHeight());

        batch.draw(btnMulti.getBtn(), btnMulti.getXpos(), btnMulti.getYpos(),
                btnMulti.getBtnWidth(), btnMulti.getBtnHeight());

        batch.draw(btnBack.getBtn(), btnBack.getXpos(), btnBack.getYpos(),
                btnBack.getBtnWidth(), btnBack.getBtnHeight());

        batch.draw(btnSettings.getBtn(), btnSettings.getXpos(), btnSettings.getYpos(),
                btnSettings.getBtnWidth(), btnSettings.getBtnHeight());

        batch.draw(btnInvitationInbox.getBtn(), btnInvitationInbox.getXpos(),
                btnInvitationInbox.getYpos(), btnInvitationInbox.getBtnWidth(),
                btnInvitationInbox.getBtnHeight());

        batch.draw(logo, 0, CastleCrush.HEIGHT * 7 / 10, CastleCrush.WIDTH, 3*CastleCrush.HEIGHT/10);

        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
