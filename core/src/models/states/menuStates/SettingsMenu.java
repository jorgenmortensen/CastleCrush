package models.states.menuStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.castlecrush.game.CastleCrush;

import controllers.menuStatesControllers.SettingsMenuController;
import models.states.GameStateManager;
import models.states.State;
import views.menuStatesViews.SettingsMenuDrawer;

/**
 * Created by Jørgen on 17.04.2018.
 */

public class SettingsMenu extends State{

    protected SettingsMenuDrawer settingsDrawer;
    protected SettingsMenuController settingsController;

    public SettingsMenu() {
        super();
    }

    public SettingsMenu(GameStateManager gsm) {
        super(gsm);
        settingsDrawer = new SettingsMenuDrawer();
        settingsController = new SettingsMenuController(gsm);
    }

    //Send to controller
    @Override
    protected void handleInput() {
        settingsController.handleInput();
    }

    //Send to controller
    @Override
    public void update(float dt) {
        settingsController.update(dt);
    }

    //Send to drawer
    @Override
    public void render(SpriteBatch sb) {
        settingsDrawer.render(sb);
    }

    @Override
    public void dispose() {
        try {
            settingsDrawer.dispose();
            settingsController.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
