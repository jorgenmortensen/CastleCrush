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

    protected Button btnSound;
    protected Button btnSoundEffects;

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
        btnSound = new Button(CastleCrush.WIDTH / 4,
                CastleCrush.HEIGHT / 2 - CastleCrush.HEIGHT / 10,
                CastleCrush.WIDTH / 8,CastleCrush.HEIGHT * 2 / 10,
                        CastleCrush.soundOn ? new Sprite(new Texture("sound_on.png")) :
                        new Sprite(new Texture("sound_off.png")));

        btnSoundEffects = new Button(CastleCrush.WIDTH * 3 / 4 - CastleCrush.WIDTH / 8,
                CastleCrush.HEIGHT / 2 - CastleCrush.HEIGHT / 10,
                CastleCrush.WIDTH / 8,
                CastleCrush.HEIGHT * 2 / 10,
                new Sprite(new Texture("sound_effects.png")));
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
