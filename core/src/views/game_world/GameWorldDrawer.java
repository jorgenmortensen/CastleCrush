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
import com.sun.org.apache.xpath.internal.SourceTree;

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



    public GameWorldDrawer(SpriteBatch batch, float screenWidth, float screenHeight) {
        super(batch);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        viewport = new ExtendViewport(screenWidth, screenHeight, cam);
        viewport.update(CastleCrush.WIDTH, CastleCrush.HEIGHT, true);
        spriteList = new ArrayList<Sprite>();
    }

    @Override
    public void render() {
        System.out.println("Render method in GameWorldDrawer");
        batch.setProjectionMatrix(cam.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        //Draw the background
        batch.draw(background, 0, 0, screenWidth, screenHeight);
//        draw every sprite on screen
        for (Sprite sprite : spriteList) {
            System.out.println("Draw sprite on screen");
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
}
