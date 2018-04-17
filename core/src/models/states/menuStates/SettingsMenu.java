package models.states.menuStates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.castlecrush.game.CastleCrush;

import models.states.GameStateManager;
import models.states.State;

/**
 * Created by Jørgen on 17.04.2018.
 */

public class SettingsMenu extends State{

    public SettingsMenu(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    protected void handleInput() {
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(new Texture("background_without_ground.png"), 0, 0, CastleCrush.WIDTH, CastleCrush.HEIGHT);
        sb.end();
    }

    @Override
    public void dispose() {
    }
}
