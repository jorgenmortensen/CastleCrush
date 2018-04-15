package models.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Jørgen on 09.03.2018.
 */

public class Projectile implements Drawable {

    Vector3 position;
    private int width;
    private int height;

    Vector3 velocity;
    private Sprite sprite;

    public Projectile(Vector3 position, int width, int height, Vector3 velocity, Sprite sprite) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.velocity = velocity;
        this.sprite = sprite;
    }

    @Override
    public void Draw(SpriteBatch batch) {

    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
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

    public Vector3 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3 velocity) {
        this.velocity = velocity;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    //Fires the shot, with a given angle and power
    public void fire(float angle, float power) {
        setVelocity(new Vector3((float)Math.cos(angle*Math.PI/180) * power, (float)Math.sin(angle*Math.PI/180) * power, (float)0));
    }

    public void update(float dt) {
        velocity.scl(dt);
        position.add(velocity.x, velocity.y, 0);
        velocity.scl(1/dt);
    }
}
