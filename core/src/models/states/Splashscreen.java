package models.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.castlecrush.game.CastleCrush;

import models.states.menuStates.StartMenuScreen;
import models.states.menuStates.PlayMenu;
import models.states.menuStates.StartMenuScreen;

/**
 * Created by erikkjernlie on 06/04/2018.
 */


public class Splashscreen extends State {

    private AssetManager am;
    // long uses 64-bit, int uses 32-bit -> long can hold more numbers
    private long startTime;
    private Texture t;

    public Splashscreen(GameStateManager gsm) {
        super(gsm);
        System.out.println("Splashscreen");
        this.am = new AssetManager();
        this.startTime = TimeUtils.millis();
        t = new Texture("splashscreen.png");
        System.out.println("Splashscreen2");
    }

    @Override
    protected void handleInput() {}

    @Override
    public void update(float dt) {
        handleInput();
        System.out.println(TimeUtils.timeSinceMillis(startTime));
        if(am.update() && TimeUtils.timeSinceMillis(startTime) > 4000){
            try {
                System.out.println("StartMenuCall");
                gsm.set(new StartMenuScreen(gsm, true));
                System.out.println("SSS2");
            } catch (Exception e) {
                CastleCrush.playServices.toast("Something went wrong, unable to start the app");
                e.printStackTrace();
            }
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
