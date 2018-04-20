package models.states.menuStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.castlecrush.game.CastleCrush;

import controllers.menuStatesControllers.SettingsMenuController;
import models.components.Button;
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
        initFields();
    }

    public SettingsMenu(GameStateManager gsm) {
        super(gsm);
        initFields();
        settingsDrawer = new SettingsMenuDrawer();
        settingsController = new SettingsMenuController(gsm);
    }

    public void initFields() {
        btnSound = new Button(0, 0, CastleCrush.WIDTH / 15, CastleCrush.WIDTH / 15,
                        CastleCrush.soundOn ? new Sprite(new Texture("sound_on.png")) :
                        new Sprite(new Texture("sound_off.png")));
    }

    public void setSound() {
        btnSound.setBtn(CastleCrush.soundOn ? new Sprite(new Texture("sound_on.png")) :
                        new Sprite(new Texture("sound_off.png")));
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
        settingsDrawer.update(dt);
    }

    //Send to drawer
    @Override
    public void render(SpriteBatch sb) {
        settingsDrawer.render(sb);
    }

    @Override
    public void dispose() {}
}
