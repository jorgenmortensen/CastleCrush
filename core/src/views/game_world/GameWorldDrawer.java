package views.game_world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.castlecrush.game.CastleCrush;

import java.util.ArrayList;
import java.util.List;

import models.GameWorld;
import models.entities.Cannon;
import models.entities.Drawable;
import models.states.GameStateManager;
import views.Drawer;

/**
 * Created by Ludvig on 14/03/2018.
 */

public class GameWorldDrawer extends Drawer {

    private Texture background = new Texture("background_without_ground.png");
    private float screenWidth;
    private float screenHeight;
    private ExtendViewport viewport;
    private List<Sprite> spriteList;



    public GameWorldDrawer(SpriteBatch batch, float screenWidth, float screenHeight){
        super(batch);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        viewport = new ExtendViewport(screenWidth, screenHeight, cam);
        viewport.update(CastleCrush.WIDTH, CastleCrush.HEIGHT, true);
        batch.setProjectionMatrix(cam.combined);
        spriteList = new ArrayList<Sprite>();



    }

    @Override
    public void render() {
        batch.setProjectionMatrix(cam.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        //Draw the background
        batch.draw(background, 0, 0, screenWidth, screenHeight);
//        draw every sprite on screen
        for (Sprite sprite : spriteList) {
            sprite.draw(batch);
        }

        batch.end();
    }



    public void addSprite(Sprite sprite) {
        if (!spriteList.contains(sprite))
            spriteList.add(sprite);
    }

    public void removeSprite(Sprite sprite) {
        if (spriteList.contains(sprite))
        spriteList.remove(sprite);
    }



    @Override
    public void dispose() {
        background.dispose();
        batch.dispose();
        }




    //  moved to update() in  world
    //        if (mockWorld.getPlayer1().getGameWinningObject().getHit()){
    //        //Check if game is over, if so, the gameOverMenu becomes active
    public void addSprite(Sprite sprite, float xPos, float yPos, float width, float height, float rotation) {
        sprite.setBounds(xPos, yPos, width, height);
        sprite.setRotation(rotation);
        spriteList.add(sprite);
    }
//    private void drawObject(Drawable object) {
//        float xPos = object.getBody().getPosition().x - object.getDrawable().getWidth()/2;
//        float yPos = object.getBody().getPosition().y - object.getDrawable().getHeight()/2;
//        float degrees = (float) Math.toDegrees(object.getBody().getAngle());
//        object.getDrawable().setPosition(xPos, yPos);
//        object.getDrawable().setRotation(degrees);
//        object.getDrawable().draw(batch);
//    }


    //            final State gameOverMenu = new GameOverMenu(gsm, false, true);
    //    private void drawGround() {
//        if (mockWorld.getGround() != null){
//            mockWorld.getGround().getDrawable().draw(batch);
//        }
//    }



//
//var i render()
//            //TODO, Opponent wins, isHost MUST BE CHANGED WHEN MERGED WITH GPS!!
//            Gdx.app.postRunnable(new Runnable() {
//                @Override
//                public void run() {
//                    gsm.set(gameOverMenu);
//
//                }
//            });
//
//        } else if (mockWorld.getPlayer2().getGameWinningObject().getHit()){
//            final State gameOverMenu = new GameOverMenu(gsm, true, false);
//            //TODO, You win, isHost MUST BE CHANGED WHEN MERGED WITH GPS!!
//            Gdx.app.postRunnable(new Runnable() {
//                @Override
//                public void run() {
//                    gsm.set(gameOverMenu);
//                }
//            });
//
//        } else {
//            for (Drawable obj : mockWorld.getBoxes()) {
//                if (obj instanceof Box) {
//                    if (!(((Box) obj).getHit())) {
//                        drawObject(obj);
//                    }
//                }
//            }

//        moved to update() in world
//            if (mockWorld.getOldProjectile().isFired()) {
//                drawObject(mockWorld.getOldProjectile());
//            }

// bli i viewet
        //Draw the power bar



//        cannonLeft.getDrawable().draw(batch);
//        cannonLeft.getWheel().draw(batch);
//        cannonRight.getDrawable().draw(batch);
//        cannonRight.getWheel().draw(batch);


        //Should be placed after bacth.end():
        //mock DELETE BODIES

//        moved to update() in world
//        if (!physicsWorld.isLocked()){
//            mockWorld.destroy((ArrayList<Fixture>) mockWorld.getBodiesToDestroy());
//
//        }
//        mockWorld.getPhysicsWorld().step(1/60f, 6, 2);



//        this.gsm = gsm;
//        this.mockWorld = world;
//        this.cannonSprite = world
//        this.cannonLeft = world.getCannons().get(0);
//        this.cannonRight = world.getCannons().get(1); //This is NULL as of now
//
//        this.physicsWorld = world.getPhysicsWorld();
//        SCALE = world.getSCALE();
//        screenWidth = CastleCrush.WIDTH*SCALE;
//        screenHeight = CastleCrush.HEIGHT*SCALE;
//
//        camera = new OrthographicCamera();
}
