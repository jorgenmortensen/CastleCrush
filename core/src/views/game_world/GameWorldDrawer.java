package views.game_world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.castlecrush.game.CastleCrush;

import java.util.ArrayList;

import models.MockGameWorld;
import models.entities.Box;
import models.entities.Cannon;
import models.entities.Castle;
import models.entities.Drawable;
import models.entities.GameWinningObject;
import models.states.GameStateManager;
import models.states.State;
import models.states.menuStates.GameOverMenu;
import views.Drawer;

/**
 * Created by Ludvig on 14/03/2018.
 */

public class GameWorldDrawer extends Drawer {

    private Texture background = new Texture("background_without_ground.png");
    //private Sprite background;
    private Castle castleLeft, castleRight;
    private Cannon cannonLeft, cannonRight;
    private GameWinningObject heartLeft, heartRight;
    private MockGameWorld mockWorld;
    private World physicsWorld;
    private OrthographicCamera camera;
    private ExtendViewport viewport;
    private float SCALE;
    private float screenWidth;
    private float screenHeight;
    GameStateManager gsm;


    private int PTM_ratio;

    Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();


    public GameWorldDrawer(SpriteBatch batch, MockGameWorld mockWorld, GameStateManager gsm){
        super(batch);
        this.gsm = gsm;
        this.mockWorld = mockWorld;
        this.cannonLeft = mockWorld.getCannons().get(0);
        this.cannonRight = mockWorld.getCannons().get(1); //This is NULL as of now

        this.physicsWorld = mockWorld.getPhysicsWorld();
        SCALE = mockWorld.getSCALE();
        screenWidth = CastleCrush.WIDTH*SCALE;
        screenHeight = CastleCrush.HEIGHT*SCALE;

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(CastleCrush.WIDTH*SCALE, CastleCrush.HEIGHT*SCALE, camera);
        viewport.update(CastleCrush.WIDTH, CastleCrush.HEIGHT, true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void render() {
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        //Draw the background
        batch.draw(background, 0,0, screenWidth, screenHeight);
        drawGround();

        //Draw the ground


// -> model
        //Check if game is over, if so, the gameOverMenu becomes active
        if (mockWorld.getPlayer1().getGameWinningObject().getHit()){
            final State gameOverMenu = new GameOverMenu(gsm, false, true);
            //TODO, Opponent wins, isHost MUST BE CHANGED WHEN MERGED WITH GPS!!
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    gsm.set(gameOverMenu);

                }
            });

        } else if (mockWorld.getPlayer2().getGameWinningObject().getHit()){
            final State gameOverMenu = new GameOverMenu(gsm, true, false);
            //TODO, You win, isHost MUST BE CHANGED WHEN MERGED WITH GPS!!
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    gsm.set(gameOverMenu);
                }
            });

        } else {
            for (Drawable obj : mockWorld.getBoxes()) {
                if (obj instanceof Box) {
                    if (!(((Box) obj).getHit())) {
                        drawObject(obj);
                    }
                }
            }
          //  for (Drawable obj : mockWorld.getCannons()) {
           //     drawObject(obj);
           // }

            if (mockWorld.getProjectile().isFired()) {
                drawObject(mockWorld.getProjectile());
            }
        }
// bli i viewet
        // debugRenderer.render(physicsWorld,camera.combined);
        //Draw the power bar
        if (cannonLeft.getPower() > 0) {
            batch.draw(new Texture("powerBar.png"), cannonLeft.getX() + cannonLeft.getWidth(),
                    cannonLeft.getY() + cannonLeft.getHeight(),
                    (100 * cannonLeft.getWidth() * 4 / 5) / 100,
                    cannonLeft.getHeight() / 2);
            // - cannonLeft.getHeight() / 8 is to get the marker correct
            batch.draw(new Texture("marker.png"),cannonLeft.getX() + cannonLeft.getWidth() + (cannonLeft.getPower() * cannonLeft.getWidth() * 4 / 5) / 100 - cannonLeft.getHeight() / 8,
                    cannonLeft.getY()+cannonLeft.getHeight() + cannonLeft.getHeight() / 2,
                    cannonLeft.getHeight() / 4,cannonLeft.getHeight() / 4);

        }
        if (cannonRight.getPower() > 0) {
            batch.draw(new Texture("powerBar.png"), cannonRight.getX(),
                    cannonRight.getY() / 2,
                    (cannonRight.getPower() * cannonRight.getWidth() * 4 / 5) / 100,
                    cannonRight.getHeight() / 2);
        }



        cannonLeft.getDrawable().draw(batch);
        cannonLeft.getWheel().draw(batch);
        cannonRight.getDrawable().draw(batch);
        cannonRight.getWheel().draw(batch);

        batch.end();

        //Should be placed after bacth.end():
        //mock DELETE BODIES

//        -> model
        if (!physicsWorld.isLocked()){
            mockWorld.destroy((ArrayList<Fixture>) mockWorld.getBodiesToDestroy());

        }
        mockWorld.getPhysicsWorld().step(1/60f, 6, 2);
        debugRenderer.render(mockWorld.getPhysicsWorld(), camera.combined);
    }

    private void drawObject(Drawable object) {
       // batch.draw(object.getDrawable(), object.getBody().getLocalCenter().x, object.getBody().getLocalCenter().y, object.getWidth(), object.getHeight());
        Vector2 position = object.getBody().getPosition();
        float xPos = object.getBody().getPosition().x - object.getDrawable().getWidth()/2;
        float yPos = object.getBody().getPosition().y - object.getDrawable().getHeight()/2;
        float degrees = (float) Math.toDegrees(object.getBody().getAngle());
        object.getDrawable().setPosition(xPos, yPos);
        object.getDrawable().setRotation(degrees);
        object.getDrawable().draw(batch);
    }

    private void drawGround() {
        if (mockWorld.getGround() != null){
            mockWorld.getGround().getDrawable().draw(batch);
        }
    }



    @Override
    public void dispose() {
        background.dispose();
        debugRenderer.dispose();
        physicsWorld.dispose();
        batch.dispose();
    }

    public int getCameraWidth() {
        return (int) (camera.viewportWidth *camera.zoom);
    }
}
