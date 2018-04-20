package views;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Ludvig on 14/03/2018.
 */

public abstract class Drawer extends ApplicationAdapter {

    protected OrthographicCamera cam;
  //  private BitmapFont font = new BitmapFont();
    protected SpriteBatch batch;

    public Drawer(SpriteBatch batch) {
        try {
            this.batch = batch;
            cam = new OrthographicCamera();
        } catch (Exception e) {
            System.out.println("DRAAAAAAAAAAAAAW");
            e.printStackTrace();
        }
    }


    public void render() {
    }

    public OrthographicCamera getCam() {
        return cam;
    }

    public void dispose() {
        batch.dispose();
    }
}