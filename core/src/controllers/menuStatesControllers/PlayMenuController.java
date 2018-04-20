package controllers.menuStatesControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.castlecrush.game.CastleCrush;

import models.states.GameStateManager;
import models.states.TutorialState;
import models.states.menuStates.MultiplayerMenu;
import models.states.menuStates.PlayMenu;
import models.states.menuStates.StartMenuScreen;
import models.states.playStates.OnlineMultiplayerState;
import models.states.playStates.SinglePlayerState;

import static com.castlecrush.game.CastleCrush.playServices;
import static com.castlecrush.game.CastleCrush.xCoordBg1;
import static com.castlecrush.game.CastleCrush.xCoordBg2;
import static models.states.menuStates.StartMenuScreen.changed_logo;
import static models.states.menuStates.StartMenuScreen.crushed;
import static models.states.menuStates.StartMenuScreen.little_crushed;
import static models.states.menuStates.StartMenuScreen.startTime;
import static models.states.menuStates.StartMenuScreen.with_u;
import static models.states.menuStates.StartMenuScreen.without_castle;

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
        if (Gdx.input.justTouched() && isOnButton(btnLocal)) {
            System.out.println("Single pressed");
            try {
                gsm.set(new OnlineMultiplayerState(gsm, new SpriteBatch()));
            } catch (Exception e) {
                CastleCrush.playServices.toast("Unable to open online multiplayer!");
                e.printStackTrace();
            }
            dispose();
        }
        else if (Gdx.input.justTouched() && isOnButton(btnMulti)) {
            if (!CastleCrush.playServices.isSignedIn()){
                try {
                    CastleCrush.playServices.signIn();
                } catch (Exception e) {
                    CastleCrush.playServices.toast("Unable to sign in to your account, please" +
                            "go back to main menu and try again");
                    e.printStackTrace();
                    gsm.set(new StartMenuScreen(gsm));
                }
            }
            CastleCrush.playServices.startSelectOpponents();
            System.out.println("Multi pressed");
            dispose();
        }
        else if (Gdx.input.justTouched() && isOnButton(btnBack)) {
            try {
                gsm.set(new StartMenuScreen(gsm));
            } catch (Exception e) {
                CastleCrush.playServices.toast("Unable to go back to the main menu");
                e.printStackTrace();
            }
            System.out.println("Local pressed");
            dispose();
        }
        else if (Gdx.input.justTouched() && isOnButton(btnInvitationInbox)) {
            try {
                CastleCrush.playServices.showInvitationInbox();
            } catch (Exception e) {
                CastleCrush.playServices.toast("Unable to show invitations");
                e.printStackTrace();
            }
            dispose();
        }
        else if (Gdx.input.justTouched() && isOnButton(btnSettings)) {
            try {
                toSettingsState(gsm);
            } catch (Exception e) {
                CastleCrush.playServices.toast("Unable to show settings");
                e.printStackTrace();
            }
        }
    }

    public void update(float dt) {
        handleInput();
        btnLocal.update(dt);
        btnMulti.update(dt);
        btnBack.update(dt);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
