package states.menuStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import states.GameStateManager;
import states.State;

/**
 * Created by Jørgen on 12.03.2018.
 */

public class SingleplayerMenu extends State{

    Sprite btnSingle;

    public SingleplayerMenu(GameStateManager gsm) {
        super(gsm);
        btnSingle = new Sprite(new Texture("singleBtn.PNG"));
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            gsm.set(new PlayMenu(gsm));
            dispose();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(btnSingle, 0, 0);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
