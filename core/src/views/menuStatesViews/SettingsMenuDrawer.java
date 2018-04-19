package views.menuStatesViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.castlecrush.game.CastleCrush;

import models.states.GameStateManager;
import models.states.menuStates.SettingsMenu;

/**
 * Created by Jørgen on 19.04.2018.
 */

public class SettingsMenuDrawer extends SettingsMenu {

    public SettingsMenuDrawer() {
        super();
    }

    public void render(SpriteBatch batch) {
        batch.begin();

        batch.draw(new Texture("background_without_ground.png"), 0, 0, CastleCrush.WIDTH, CastleCrush.HEIGHT);

        batch.draw(backBtn.getBtn(), backBtn.getXpos(), backBtn.getYpos(),
                backBtn.getBtnWidth(), backBtn.getBtnHeight());

        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
