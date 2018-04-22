package models.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Bruker on 22.04.2018.
 */

public abstract class PhysicalGameObject {

    protected Body body;
    protected Sprite sprite;

    public PhysicalGameObject (Body body, Sprite sprite) {
        this.body = body;
        this.sprite = sprite;
    }

    public Body getBody() {
        return body;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
