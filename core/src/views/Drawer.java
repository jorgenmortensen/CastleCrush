package views;

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

public abstract class Drawer {

    protected OrthographicCamera cam;
  //  private BitmapFont font = new BitmapFont();
    protected SpriteBatch batch;

    public Drawer() {
        batch = new SpriteBatch();
        cam = new OrthographicCamera();
    }

    //vi tenker at:
    //en slags while-løkke i render funksjonen der det er en
    // definert rekkefølge på hva som skal tegnes først

    public void render() {
    //   Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    //    batch.begin();
    //    batch.end();
    }

    public void dispose() {
        batch.dispose();
    }
}