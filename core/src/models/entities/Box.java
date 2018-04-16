package models.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
//import com.sun.xml.internal.ws.client.sei.ResponseBuilder;

/**
 * Created by Jørgen on 09.03.2018.
 */

// Fundamental blocks for the castle

public class Box implements Drawable {


    private Sprite sprite;
    private Body body;
    private int x, y;
    private float width;
    private float height;
    private float density;
    private boolean isHit = false;

    public Box(Body body, Sprite sprite, float width, float height, float density) {
        this.body = body;
        this.sprite = sprite;
        this.width = width;
        this.height = height;
        this.density = density;
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

    public void isHit(boolean isHit){
        this.isHit = isHit;
    }

    public float getDensity() {
        return density;
    }
}
