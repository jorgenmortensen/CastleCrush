package views.game_world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.castlecrush.game.CastleCrush;
import com.sun.org.apache.xpath.internal.SourceTree;

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
        this.physicsWorld = mockWorld.getPhysicsWorld();
        SCALE = world.getSCALE();
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(CastleCrush.WIDTH*SCALE, CastleCrush.HEIGHT*SCALE, camera);
        viewport.update(CastleCrush.WIDTH, CastleCrush.HEIGHT, true);
    }

    public void render() {
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, CastleCrush.WIDTH * SCALE, CastleCrush.HEIGHT * SCALE);
        drawGround();
        for (Drawable obj : mockWorld.getBoxes()) {
            drawObject(obj);
        }
        for (Drawable obj : mockWorld.getCannons()) {
            drawObject(obj);
        }



        drawObject(mockWorld.getProjectile());
        System.out.println("HHHH");
        mockWorld.getPhysicsWorld().step(1 / 60f, 6, 2);

        batch.end();
    }

    private void drawObject(Drawable object) {
        float xPos = object.getBody().getPosition().x - object.getDrawable().getWidth()/2;
        float yPos = object.getBody().getPosition().y - object.getDrawable().getHeight()/2;
        float degrees = (float) Math.toDegrees(object.getBody().getAngle());
        object.getDrawable().setPosition(xPos, yPos);
        object.getDrawable().setRotation(degrees);
        System.out.println("AAA");
        object.getDrawable().draw(batch);
        System.out.println("BBBB");
    }

    private void drawGround() {
        mockWorld.getGround().getDrawable().draw(batch);
    }



    @Override
    public void dispose() {

        debugRenderer.dispose();

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
