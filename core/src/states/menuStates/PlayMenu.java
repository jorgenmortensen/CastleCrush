package states.menuStates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import states.GameStateManager;
import states.State;

/**
 * Created by Jørgen on 05.04.2018.
 */

public class PlayMenu extends State {

    Sprite btnSingle;


    public PlayMenu(GameStateManager gsm) {
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
        sb.begin();
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
