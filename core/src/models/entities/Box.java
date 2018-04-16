package models.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
//import com.sun.xml.internal.ws.client.sei.ResponseBuilder;

/**
 * Created by Jørgen on 09.03.2018.
 */

// Fundamental blocks for the castle

public class Box implements Drawable {


    private Sprite sprite;
    private Body body;
    private float width;
    private float height;

    private boolean isHit = false;

    public Box(Body body, Sprite sprite, float width, float height) {
        this.body = body;
        this.sprite = sprite;
        this.width = width;
        this.height = height;
    }

    public Body getBody() {
        return body;
    }

    @Override
    public Sprite getDrawable() {
        return sprite;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public boolean getHit(){
        return this.isHit;
    }

    public void isHit(boolean isHit){
        this.isHit = isHit;
    }
}
