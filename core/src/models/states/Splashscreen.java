package models.states;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.castlecrush.game.CastleCrush;

/**
 * Created by erikkjernlie on 06/04/2018.
 */

// Help from:
// https://stackoverflow.com/questions/37448355/libgdx-how-to-create-a-splash-screen

public class Splashscreen extends State {

    private AssetManager am;
    // long uses 64-bit, int uses 32-bit -> long can hold more numbers
    private long startTime;
    private Texture t;


    public Splashscreen(models.states.GameStateManager gsm) {
        super(gsm);
        this.am = new AssetManager();
        this.startTime = TimeUtils.millis();
        t = new Texture("splashscreen.png");
    }

    @Override
    protected void handleInput() {
    }

    @Override
    public void update(float dt) {
        handleInput();
        if(am.update() && TimeUtils.timeSinceMillis(startTime) > 1000){
            gsm.set(new models.states.menuStates.StartMenuScreen(gsm));
            dispose();
        }

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(t,0,0, CastleCrush.WIDTH, CastleCrush.HEIGHT);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
