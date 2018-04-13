package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.castlecrush.game.CastleCrush;

import components.Button;

/**
 * Created by Jørgen on 24.01.2018.
 */

public abstract class State {

    protected OrthographicCamera cam;
    protected Vector3 mouse;
    protected GameStateManager gsm;

    protected State(GameStateManager gsm) {
        this.gsm = gsm;
        cam = new OrthographicCamera();
        mouse = new Vector3();
    }

    protected boolean isOnButton(Button button) {
        if (((CastleCrush.HEIGHT - Gdx.input.getY()) > button.getYpos()) &&
                ((CastleCrush.HEIGHT - Gdx.input.getY()) < (button.getYpos() + button.getBtnHeight())) &&
                (Gdx.input.getX() > button.getXpos()) && (Gdx.input.getX() < (button.getXpos() + button.getBtnWidth()))) {
            return true;
        }
        return false;
    }

    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();



}
