package states.menuStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.castlecrush.game.CastleCrush;

import components.GravityButton;
import states.GameStateManager;
import states.State;

/**
 * Created by Jørgen on 12.03.2018.
 */

public class SingleplayerMenu extends State{

    private GravityButton gravityButton;

    public SingleplayerMenu(GameStateManager gsm) {
        super(gsm);
        gravityButton = new GravityButton(CastleCrush.WIDTH/2-CastleCrush.WIDTH/60, 0, CastleCrush.WIDTH/30, CastleCrush.WIDTH/30, new Sprite(new Texture("sound.png")), CastleCrush.HEIGHT);

    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            gsm.set(new PlayMenu(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        gravityButton.update(dt);

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(gravityButton.getBtn(), gravityButton.getXpos(), gravityButton.getYpos(),CastleCrush.WIDTH/30, CastleCrush.WIDTH/30);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
