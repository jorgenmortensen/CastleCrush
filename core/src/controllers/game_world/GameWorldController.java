package controllers.game_world;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;

import controllers.Controller;
import models.entities.Box;
import models.entities.Castle;
import views.game_world.GameWorldDrawer;

/**
 * Created by Ludvig on 07/04/2018.
 */

public class GameWorldController extends Controller {


@Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            if (activePlayer.isAngleActive()) {
                float angle = activeCannon.getAngle();

                activePlayer.switchAngleActive();
                activePlayer.switchPowerActive();

            } else if (activePlayer.isPowerActive()) {
                float power = activeCannon.getPower();

                activePlayer.switchPowerActive();

            }
        }


        if (!activePlayer.isAngleActive() && !activePlayer.isPowerActive()) {
            if (!world.getProjectile().isFired()) {
                fire();
            }
        }
    }

}
