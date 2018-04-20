package views.menuStatesViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.castlecrush.game.CastleCrush;

import models.components.Button;
import models.states.GameStateManager;
import models.states.menuStates.SettingsMenu;

/**
 * Created by Jørgen on 19.04.2018.
 */

public class SettingsMenuDrawer extends SettingsMenu {

    public SettingsMenuDrawer() {
        super();
    }

    @Override
    public void update(float dt) {
        if (Gdx.input.justTouched() && isOnButton(btnSound)) {
            //Switch sound on if off and vice versa
            if (CastleCrush.soundOn) {
                btnSound.setBtn(new Sprite(new Texture("sound_off.png")));
            } else {
                btnSound.setBtn(new Sprite(new Texture("sound_on.png")));
            }
        }
    }

    public void render(SpriteBatch batch) {
        batch.begin();

        batch.draw(new Texture("background_without_ground.png"), 0, 0, CastleCrush.WIDTH, CastleCrush.HEIGHT);

        //Home button in bottom right corner
        batch.draw(btnHome.getBtn(), btnHome.getXpos(), btnHome.getYpos(),
                btnHome.getBtnWidth(), btnHome.getBtnHeight());

        //Sound button in bottom left corner
        batch.draw(btnSound.getBtn(), btnSound.getXpos(), btnSound.getYpos(),
                btnSound.getBtnWidth(), btnSound.getBtnHeight());

        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
