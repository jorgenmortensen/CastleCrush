package controllers.game_world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import controllers.Controller;
import models.GameWorld;
import models.entities.Box;
import models.entities.Castle;
import views.game_world.GameWorldDrawer;

/**
 * Created by Ludvig on 07/04/2018.
 */

public class GameWorldController extends Controller {

private GameWorld world;

    public GameWorldController(GameWorld world) {
        this.world = world;
    }

    public void handleOnlineMessage(/*PUT MESSAGE HERE*/) {
//        INSERT MESSAGEHANDELING HERE TO SET VELOCITY


//        UNCOMMENT AND INITAITE VELOCITY WITH VALUE FROM OTHER PLAYER
//        Vector2 velocity;
//        world.fireProjectile(velocity);


    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
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
