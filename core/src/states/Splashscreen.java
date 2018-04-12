package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.castlecrush.game.CastleCrush;

import entities.Castle;
import states.menuStates.LogInMenu;
import states.menuStates.StartMenuScreen;

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
    private CastleCrush crush;


    public Splashscreen(GameStateManager gsm, CastleCrush crush) {
        super(gsm);
        this.am = new AssetManager();
        this.startTime = TimeUtils.millis();
        t = new Texture("splashscreen.png");
        this.crush=crush;
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        handleInput();
        if(am.update() && TimeUtils.timeSinceMillis(startTime) > 4000){
            gsm.set(new StartMenuScreen(gsm,crush));
        }

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(t,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
