package models.states.playStates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import models.states.GameStateManager;
import models.states.State;
import models.states.menuStates.PlayMenu;
import models.states.menuStates.SettingsMenu;

/**
 * Created by Jørgen on 12.03.2018.
 */

public class SuperPlayState extends State{

    protected SuperPlayState(GameStateManager gsm) {
        super(gsm);
    }

    public void gameOver(){

    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {

    }

    @Override
    public void dispose() {

    }

    public void goToMainMenu(){
        gsm.set(new PlayMenu(gsm));
    }

    public void goToSettingsMenu(){
        gsm.push(new SettingsMenu(gsm));
    }

}
