package views.game_world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import models.MockGameWorld;
import models.entities.Cannon;
import models.entities.Castle;
import models.entities.Drawable;
import models.entities.GameWinningObject;
import views.Drawer;

/**
 * Created by Ludvig on 14/03/2018.
 */

public class GameWorldDrawer extends Drawer {
    private Texture background = new Texture("background_without_ground.png");
    private Castle castleLeft, castleRight;
    private Cannon cannonLeft, cannonRight;
    private GameWinningObject heartLeft, heartRight;
    private MockGameWorld mockWorld;
    private World physicsWorld;
    private static OrthographicCamera camera;
    private ExtendViewport viewport;

    Box2DDebugRenderer debugRenderer;


    public GameWorldDrawer(SpriteBatch batch, MockGameWorld world){
        super(batch);
        this.mockWorld = world;
        this.physicsWorld = mockWorld.getPhysicsWorld();

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(80, 60, camera);

    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0,0);

        for (Drawable obj : mockWorld.getBoxes()) {
            drawObject(obj);
        }
        for (Drawable obj : mockWorld.getCannons()) {
            drawObject(obj);
        }

        drawObject(mockWorld.getProsjektil());

        batch.end();
       // debugRenderer.render(physicsWorld, camera.combined);

        mockWorld.getPhysicsWorld().step(1/60f, 6, 2);
    }

    private void drawObject(Drawable object) {
        batch.draw(object.getDrawable(), object.getX(), object.getY(), object.getWidth(), object.getHeight());
    }


    @Override
    public void dispose() {

      //  debugRenderer.dispose();

    }

    public static int getCameraWidth() {
        return (int) (camera.viewportWidth *camera.zoom);
    }

}
