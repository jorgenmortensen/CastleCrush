package models.states.playStates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import models.MockGameWorld;
import models.states.GameStateManager;
import models.states.State;
import views.game_world.GameWorldDrawer;

/**
 * Created by Jørgen on 12.03.2018.
 */

public class SinglePlayerState extends State {

    private GameStateManager gsm;
    private MockGameWorld world;
    private GameWorldDrawer drawer;

    public SinglePlayerState(GameStateManager gsm){
        super(gsm);
        world = new MockGameWorld();
        drawer = new GameWorldDrawer(new SpriteBatch(), world);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        drawer.render();
    }

    @Override
    public void dispose() {
        drawer.dispose();
    }

}
