package models.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
//import com.sun.xml.internal.ws.client.sei.ResponseBuilder;

/**
 * Created by Jørgen on 09.03.2018.
 */

public class GameWinningObject implements Drawable {

    private float x;
    private float y;
    private float width;
    private float height;
    private Sprite sprite;
    private Body body;

    private boolean isHit;

    public GameWinningObject(Body body, Sprite sprite, float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
        this.body = body;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public boolean isHit() {
        return isHit;
    }

    public void isHit(boolean isHit){
        this.isHit = isHit;
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
