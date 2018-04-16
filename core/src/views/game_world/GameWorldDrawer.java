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

    private Texture gwo1;
    private Texture gwo2;
    private Texture gwo3;


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
        System.out.println(CastleCrush.WIDTH*SCALE +" "+ CastleCrush.HEIGHT*SCALE);
        batch.setProjectionMatrix(camera.combined);
        gwo1 = new Texture("gwo1.png");
        gwo2 = new Texture("gwo2.png");
        gwo3 = new Texture("gwo3.png");
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0,0, CastleCrush.WIDTH*SCALE, CastleCrush.HEIGHT*SCALE);
        drawObject(mockWorld.getGround());
        batch.draw(gwo1, 0,0, 5,5);
        batch.draw(gwo2, 6,0, 5,5);
        batch.draw(gwo3, 12,0, 5,5);
        // If the object has been hit, it doesn't have to be drawn

        for (Drawable obj : mockWorld.getBoxes()) {
            if (obj instanceof Box){
                if (((Box) obj).getHit()){
                } else {
                    drawObject(obj);
                }
            }
        }


        for (Drawable obj : mockWorld.getCannons()) {

            drawObject(obj);
        }

        if (mockWorld.getProjectile().getHasHit()){

        } else {
            drawObject(mockWorld.getProjectile());
        }


        batch.end();
        debugRenderer.render(physicsWorld,camera.combined);

        mockWorld.getPhysicsWorld().step(1/60f, 6, 2);
        //mock DELETE BODIES
        if (!physicsWorld.isLocked()){
            mockWorld.destroy((ArrayList<Fixture>) mockWorld.getBodiesToDestroy());

        }

    }




    private void drawObject(Drawable object) {
        Vector2 position = object.getBody().getPosition();
        float degrees = (float) Math.toDegrees(object.getBody().getAngle());
        object.getDrawable().setPosition(position.x, position.y);
        object.getDrawable().setRotation(degrees);
        object.getDrawable().draw(batch);
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
