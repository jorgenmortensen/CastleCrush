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

    protected Button returnBtn;

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
        btnMusic = new Button(CastleCrush.WIDTH/2 - CastleCrush.WIDTH/8, CastleCrush.HEIGHT/3, CastleCrush.WIDTH / 4, CastleCrush.WIDTH / 15,
                        CastleCrush.musicOn ? new Sprite(new Texture("music_on.png")) :
                        new Sprite(new Texture("music_off.png")));
        btnSound = new Button(CastleCrush.WIDTH/2 - CastleCrush.WIDTH/8, CastleCrush.HEIGHT*2/3, CastleCrush.WIDTH / 4, CastleCrush.WIDTH / 15,
                CastleCrush.soundEffectsOn ? new Sprite(new Texture("sounds_on.png")) :
                        new Sprite(new Texture("sounds_off.png")));
        returnBtn = new Button(CastleCrush.WIDTH * 14 / 15 - CastleCrush.WIDTH/100, CastleCrush.WIDTH/100,
                CastleCrush.WIDTH / 15, CastleCrush.WIDTH / 15,
                new Sprite(new Texture(("returnBtn.png"))));
    }

    public void setMusic(){
        btnMusic.setBtn(CastleCrush.musicOn ? new Sprite(new Texture("music_on.png")) :
                new Sprite(new Texture("music_off.png")));
    }

    public void setSound() {
        btnSound.setBtn(CastleCrush.soundEffectsOn ? new Sprite(new Texture("sounds_on.png")) :
                        new Sprite(new Texture("sounds_off.png")));
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
