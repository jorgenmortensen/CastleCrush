package models.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Jørgen on 09.03.2018.
 */

// Fundamental blocks for the castle

public class Box implements Drawable{

    private int x;
    private int y;
    private float width;
    private float height;
    private Sprite sprite;
    private Body body;

    private boolean isHit = false;

    public Box(Body body, Sprite sprite, float width, float height) {
        this.body = body;
        this.sprite = sprite;
        this.width = width;
        this.height = height;
    }

    @Override
    public Sprite getDrawable() {
        return sprite;
    }

    @Override
    public Body getBody() {
        return body ;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    public boolean getHit(){
        return this.isHit;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
