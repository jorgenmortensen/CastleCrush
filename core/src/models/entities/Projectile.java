package models.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Jørgen on 09.03.2018.
 */

public class Projectile implements Drawable {

    private int x, y;
    private int width;
    private int height;
    private Body body;
    private Sprite sprite;

    private Vector2 velocity;

    public Projectile(Body body, Sprite sprite, int width, int height, Vector2 velocity) {
        this.width = width;
        this.height = height;
        this.body = body;
        this.sprite = sprite;
        this.velocity = velocity;
    }

    public Body getBody() {
        return body;
    }

    @Override
    public Sprite getDrawable() {
        return sprite;
    }

}
