package states.menuStates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.sun.corba.se.spi.orbutil.fsm.State;

import states.GameStateManager;

/**
 * Created by Jørgen on 12.03.2018.
 */

public class MenuScreen extends states.State {

    //Located in the center of the screen
    private int btnSingle;
    private int btnMultiOnline;
    private int btnMultiLocal;
    private int btnTutorial;

    //Located in the one of the corners
    private int btnSettings;

    public MenuScreen(GameStateManager gsm) {
        super(gsm);
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
}
