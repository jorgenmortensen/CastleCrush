package models.states.menuStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.castlecrush.game.CastleCrush;

import models.states.GameStateManager;
import models.states.State;

/**
 * Created by Jørgen on 09.03.2018.
 */

public class LogInMenu extends State {

    private Button signInBtn;
    private CastleCrush crush;
    Texture t;

    public LogInMenu(GameStateManager gsm, CastleCrush crush) {
        super(gsm);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        this.crush=crush;
        System.out.println("Hei");
        t = new Texture("helpBtn.png");

        gsm.set(new StartMenuScreen(gsm));
        dispose();



    }

    private void signIn() {
        crush.playServices.signIn();
        gsm.set(new StartMenuScreen(gsm));
        dispose();
    }



    @Override
    protected void handleInput() {
        if (signInBtn.isPressed()){
            crush.playServices.signIn();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
    }

    @Override
    public void dispose() {
    }
}
