package models.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Jørgen on 09.03.2018.
 */

public class GameWinningObject implements Drawable {

    private int x;
    private int y;
    private int width;
    private int height;
    private Sprite sprite;
    private Body body;

    private boolean isHit;

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return this.height;
    }

    @Override
    public Sprite getDrawable() {
        return sprite;
    }

    @Override
    public Body getBody() {
        return body;
    }
}
