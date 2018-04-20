package views.menuStatesViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
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

    public void handleInput() {
        if (Gdx.input.justTouched() && isOnButton(btnLogOutIn)) {
            if (CastleCrush.playServices.isSignedIn()) {
                btnLogOutIn.setBtn(new Sprite(new Texture("sign_in.png")));
            } else {
                btnLogOutIn.setBtn(new Sprite(new Texture("signout.png")));
            }
        }
    }

    public void update(float dt) {
        handleInput();
        //makes the background move to the left
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

    public void render(SpriteBatch sb) {
        sb.begin();

        sb.draw(background1, xCoordBg1, 0, CastleCrush.WIDTH, CastleCrush.HEIGHT);
        sb.draw(background2, xCoordBg2, 0, CastleCrush.WIDTH, CastleCrush.HEIGHT);

        sb.draw(btnPlay.getBtn(), btnPlay.getXpos(),
                btnPlay.getYpos(),
                btnPlay.getBtnWidth(), btnPlay.getBtnHeight());

        sb.draw(btnHelp.getBtn(), btnHelp.getXpos(),
                btnHelp.getYpos(),
                btnHelp.getBtnWidth(), btnHelp.getBtnHeight());

        sb.draw(btnLogOutIn.getBtn(), btnLogOutIn.getXpos(),
                btnLogOutIn.getYpos(),
                btnLogOutIn.getBtnWidth(), btnLogOutIn.getBtnHeight());

        sb.draw(btnInvitationInbox.getBtn(), btnInvitationInbox.getXpos(),
                btnInvitationInbox.getYpos(),
                btnInvitationInbox.getBtnWidth(), btnInvitationInbox.getBtnHeight());

        sb.draw(btnSettings.getBtn(), btnSettings.getXpos(), btnSettings.getYpos(),
                btnSettings.getBtnWidth(), btnSettings.getBtnHeight());

        sb.draw(logo, 0, CastleCrush.HEIGHT * 7 / 10, CastleCrush.WIDTH, CastleCrush.HEIGHT * 3 / 10);

        sb.end();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
