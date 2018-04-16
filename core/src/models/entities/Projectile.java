package models.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Jørgen on 09.03.2018.
 */

public class Projectile implements Drawable{

    private Vector2 position;
    private int width;
    private int height;

    private Body body;
    private Sprite sprite;
    private Vector2 velocity;

    public Projectile(Body body, Vector2 position, Sprite sprite, int width, int height, Vector2 velocity) {
        if (this.body == null) {
            this.body = body;
            this.position = position;
            this.width = width;
            this.height = height;
            this.sprite = sprite;
            this.velocity = velocity;
        }
    }

    public Body getBody() {
        return body;
    }

    public Sprite getDrawable() {
        return sprite;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

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

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public String toString() {
        return "Projectile{" +
                "position=" + position +
                ", width=" + width +
                ", height=" + height +
                ", body=" + body +
                ", sprite=" + sprite +
                ", velocity=" + velocity +
                '}';
    }

    //Fires the shot, with a given angle and power
    public void fire(float angle, float power) {
        setVelocity(new Vector2((float)Math.cos(angle*Math.PI/180) * power, (float)Math.sin(angle*Math.PI/180) * power));
    }

    public void update(float dt) {
        velocity.scl(dt);
        position.add(velocity.x, velocity.y);
        velocity.scl(1/dt);
    }
}
