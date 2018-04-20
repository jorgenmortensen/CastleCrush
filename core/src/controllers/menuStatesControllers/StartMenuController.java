package controllers.menuStatesControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.TimeUtils;
import com.castlecrush.game.CastleCrush;

import models.entities.Castle;
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
            try {
                gsm.set(new PlayMenu(gsm));
            } catch (Exception e) {
                CastleCrush.playServices.toast("Unable to open the play menu!");
                e.printStackTrace();
            }
            dispose();

        } else if (Gdx.input.justTouched() && isOnButton(btnHelp)) {
            try {
                gsm.set(new TutorialState(gsm));
            } catch (Exception e) {
                CastleCrush.playServices.toast("Unable to open the tutorial!");
                e.printStackTrace();
            }
            dispose();

        } else if (Gdx.input.justTouched() && isOnButton(btnInvitationInbox)) {
            try {
                CastleCrush.playServices.showInvitationInbox();
            } catch (Exception e) {
                CastleCrush.playServices.toast("Unable to show invitations, it seems that you are" +
                        "not logged in");
                e.printStackTrace();
            }
            dispose();

        } else if (Gdx.input.justTouched() && isOnButton(btnLogOutIn)) {
            if (CastleCrush.playServices.isSignedIn()) {
                try {
                    CastleCrush.playServices.signOut();
                } catch (Exception e) {
                    CastleCrush.playServices.toast("Unable to sign out, please restart the app");
                    e.printStackTrace();
                }
            }
            else {
                try {
                    CastleCrush.playServices.signIn();
                } catch (Exception e) {
                    CastleCrush.playServices.toast("Unable to sign in, please verify that the account" +
                            "credentials are correct");
                    e.printStackTrace();
                }
            }

        } else if (Gdx.input.justTouched() && isOnButton(btnSettings)) {
            try {
                toSettingsState(gsm);
            } catch (Exception e) {
                CastleCrush.playServices.toast("Something went wrong, please try again");
                e.printStackTrace();
            }
        }
    }

    @Override
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

    @Override
    public void dispose() {
        super.dispose();
    }
}
