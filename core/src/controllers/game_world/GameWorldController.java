package controllers.game_world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.castlecrush.game.CastleCrush;

import java.util.ArrayList;
import java.util.List;

import controllers.Controller;
import models.GameWorld;
import models.states.playStates.SuperPlayState;
import views.game_world.GameWorldDrawer;

/**
 * Created by Ludvig on 07/04/2018.
 */

public class GameWorldController extends Controller {

    private GameWorld world;
    private SuperPlayState state;

    public GameWorldController(GameWorld world, SuperPlayState state) {
        this.world = world;
        this.state = state;
    }

    public void handleOnlineMessage(/*PUT MESSAGE HERE*/) {
//        INSERT MESSAGEHANDELING HERE TO SET VELOCITY


//        UNCOMMENT AND INITAITE VELOCITY WITH VALUE FROM OTHER PLAYER
//        Vector2 velocity;
//        world.fireProjectile(velocity);


    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched() && world.isOnButton(world.homeButton)){
            try {
                state.goToMainMenu();
            } catch (Exception e) {
                CastleCrush.playServices.toast("Unable to show settings");
                e.printStackTrace();
            }
        } else if (Gdx.input.justTouched() && world.isOnButton(world.settingsButton)) {
            try {
                state.goToSettingsMenu();
            } catch (Exception e) {
                CastleCrush.playServices.toast("Unable to show settings");
                e.printStackTrace();
            }
        } else if (Gdx.input.justTouched()) {

            world.input();
            System.out.println("press screeen");

//            if (activePlayer.isAngleActive()) {
//                float angle = activeCannon.getAngle();
//
//                activePlayer.switchAngleActive();
//                activePlayer.switchPowerActive();
//
//            } else if (activePlayer.isPowerActive()) {
//                float power = activeCannon.getPower();
//
//                activePlayer.switchPowerActive();
//
//            }
//        }
//
//
//        if (!activePlayer.isAngleActive() && !activePlayer.isPowerActive()) {
//            if (!world.getOldProjectile().isFired()) {
//                fire();
//            }
        }
    }

}
