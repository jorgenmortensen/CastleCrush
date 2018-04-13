package views.game_world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.castlecrush.game.CastleCrush;

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


    private int PTM_ratio;

    Box2DDebugRenderer debugRenderer;


    public GameWorldDrawer(SpriteBatch batch, MockGameWorld world){
        super(batch);
        this.mockWorld = world;
        this.physicsWorld = mockWorld.getPhysicsWorld();
        PTM_ratio = mockWorld.getPTM_RATIO();

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(CastleCrush.WIDTH, CastleCrush.HEIGHT, camera);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0,0, CastleCrush.WIDTH, CastleCrush.HEIGHT);

        for (Drawable obj : mockWorld.getBoxes()) {
            drawObject(obj);
        }
        for (Drawable obj : mockWorld.getCannons()) {
            drawObject(obj);
        }

        drawObject(mockWorld.getProsjektil());

        batch.end();

        mockWorld.getPhysicsWorld().step(1/60f, 6, 2);
    }

    private void drawObject(Drawable object) {
        //batch.draw(object.getDrawable(), object.getBody().getPosition().x, object.getBody().getPosition().y, object.getWidth(), object.getHeight());
        object.getDrawable().setPosition(object.getBody().getPosition().x*PTM_ratio, object.getBody().getPosition().y*PTM_ratio);
        object.getDrawable().draw(batch);
    }


    @Override
    public void dispose() {

      //  debugRenderer.dispose();

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
