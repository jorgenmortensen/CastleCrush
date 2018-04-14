package models.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
//import com.sun.xml.internal.ws.client.sei.ResponseBuilder;

/**
 * Created by Jørgen on 09.03.2018.
 */

// Fundamental blocks for the castle

public class Box implements Drawable {


    private Sprite sprite;
    private Body body;

    private boolean isHit;

    public Box(Body body, Sprite sprite) {
        this.body = body;
        this.sprite = sprite;
    }

    public Body getBody() {
        return body;
    }

    @Override
    public Sprite getDrawable() {
        return sprite;
    }
}
