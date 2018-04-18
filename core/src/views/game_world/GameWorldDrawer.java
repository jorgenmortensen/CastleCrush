package views.game_world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
import models.entities.Projectile;
import models.states.State;
import models.states.playStates.SinglePlayerState;
import views.Drawer;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

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
    private float screenWidth = CastleCrush.WIDTH*SCALE;



    private int PTM_ratio;

    Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();


    public GameWorldDrawer(SpriteBatch batch, MockGameWorld world){
        super(batch);
        this.mockWorld = world;
        this.cannonLeft = world.getCannons().get(0);
        this.cannonRight = world.getCannons().get(1); //This is NULL as of now

        this.physicsWorld = mockWorld.getPhysicsWorld();
        SCALE = world.getSCALE();


        camera = new OrthographicCamera();
        viewport = new ExtendViewport(CastleCrush.WIDTH*SCALE, CastleCrush.HEIGHT*SCALE, camera);
        viewport.update(CastleCrush.WIDTH, CastleCrush.HEIGHT, true);
    }


    @Override
    public void render() {
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //Draw the background
        batch.draw(background, 0,0, CastleCrush.WIDTH*SCALE, CastleCrush.HEIGHT*SCALE);
        drawGround();

        //Draw the ground

        //Draw the boxes(castles)
        for (Drawable obj : mockWorld.getBoxes()) {
            if (obj instanceof Box){
                if (((Box) obj).getHit()){
                } else {
                    drawObject(obj);
                }
            }
        }

        //Draw the cannons
        /*for (Cannon cannon : mockWorld.getCannons()) {
            cannon.getCannon().draw(batch);
        }*/

//        batch.draw(cannonLeft.getCannon(), cannonLeft.getX(), cannonLeft.getY() / 2,
//                cannonLeft.getWidth() * 3 / 10, cannonLeft.getHeight() * 3 / 10,
//                cannonLeft.getWidth(), cannonLeft.getHeight(), 1, 1,
//                cannonLeft.getCannon().getRotation());



//        batch.draw(cannonLeft.getWheel(), cannonLeft.getX(), CastleCrush.HEIGHT * SCALE / 100,
//                cannonLeft.getWidth() * 2 / 5,
//                cannonLeft.getHeight());

        //mockWorld.getCannons().get(0).getCannon().draw(batch);




        //Draw the projectile
//        if (cannonLeft.isShotsFired()) {
//            if (mockWorld.getProjectile().getHasHit()) {
//            } else {
//                drawObject(mockWorld.getProjectile());
//            }
//        }
        if (mockWorld.getProjectile().isFired()){
            drawObject(mockWorld.getProjectile());
        }

        //Draw the power bar
        if (cannonLeft.getPower() > 0) {
            batch.draw(new Texture("powerBar.png"), cannonLeft.getX(),
                    cannonLeft.getY() / 2,
                    (cannonLeft.getPower() * cannonLeft.getWidth() * 4 / 5) / 100,
                    cannonLeft.getHeight() / 2);
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
        mockWorld.getGround().getDrawable().draw(batch);
    }



    @Override
    public void dispose() {
        debugRenderer.dispose();
        batch.dispose();
    }

    public int getCameraWidth() {
        return (int) (camera.viewportWidth *camera.zoom);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);

        batch.setProjectionMatrix(camera.combined);
    }


}
