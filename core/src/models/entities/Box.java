package models.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Jørgen on 09.03.2018.
 */

// Fundamental blocks for the castle

public class Box implements Drawable {

    private int x;
    private int y;
    private int width;
    private int height;

    private boolean isHit;

    @Override
    public void Draw(SpriteBatch batch) {

    }

    @Override
    public void setPosition(Vector3 position) {

    }

    @Override
    public Vector3 getPosition() {
        return null;
    }


    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int getHeight() {
        return this.height;
    }
}
