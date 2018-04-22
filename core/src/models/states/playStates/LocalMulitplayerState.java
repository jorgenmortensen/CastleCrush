package models.states.playStates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.castlecrush.game.CastleCrush;

import controllers.game_world.GameWorldController;
import models.GameWorld;
import models.entities.Player;
import models.states.GameStateManager;
import models.states.menuStates.LocalGameOverMenu;
import models.states.menuStates.OnlineGameOverMenu;
import views.game_world.GameWorldDrawer;

/**
 * Created by Jørgen on 12.03.2018.
 */

public class LocalMulitplayerState extends SuperPlayState {

    private GameWorldController controller;
    private GameWorld world;
    private GameWorldDrawer gameWorldDrawer;
    static float SCALE = 0.05f;
    private float screenWidth;
    private float screenHeight;


    public LocalMulitplayerState(GameStateManager gsm) {
        super(gsm);
        screenWidth = CastleCrush.WIDTH * SCALE;
        screenHeight = CastleCrush.HEIGHT * SCALE;
        gameWorldDrawer = new GameWorldDrawer(new SpriteBatch(), screenWidth, screenHeight);
        world = new GameWorld(this, gameWorldDrawer, screenWidth, screenHeight, null);
        controller = new GameWorldController(world, this);
    }

    @Override
    protected void handleInput() {
//        controller.handleInput();
    }

    @Override
    public void gameOver () {
            gsm.set(new OnlineGameOverMenu(gsm, false));
        }

    public void gameOver(Player winningPlayer, Player loosingPLayer) {
        gsm.set(new LocalGameOverMenu(gsm, winningPlayer, loosingPLayer));
    }

    Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    @Override
    public void render (SpriteBatch sb){
        gameWorldDrawer.render();
        //debugRenderer.render(world.getPhysicsWorld(), gameWorldDrawer.getCam().combined);
    }

        @Override
    public void dispose () {
        gameWorldDrawer.dispose();
        world.dispose();
        debugRenderer.dispose();
    }

    @Override
    public void update (float dt){
        world.update(dt);
        controller.handleInput();
    }
}