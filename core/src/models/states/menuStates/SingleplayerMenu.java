package models.states.menuStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.castlecrush.game.CastleCrush;

import models.components.GravityButton;
import models.states.GameStateManager;
import models.states.State;
import models.components.GravityButton;

/**
 * Created by Jørgen on 12.03.2018.
 */

public class SingleplayerMenu extends models.states.State {

    private GravityButton gravityButton;

    public SingleplayerMenu(models.states.GameStateManager gsm) {
        super(gsm);
        gravityButton = new GravityButton(CastleCrush.WIDTH/2-CastleCrush.WIDTH/10, 0, CastleCrush.WIDTH/5, CastleCrush.WIDTH/5, new Sprite(new Texture("feather.png")), CastleCrush.HEIGHT);

    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            //gsm.set(new PlayMenu(gsm));
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
        sb.draw(gravityButton.getBtn(), gravityButton.getXpos(), gravityButton.getYpos(),
                gravityButton.getBtnWidth(), gravityButton.getBtnHeight());
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
