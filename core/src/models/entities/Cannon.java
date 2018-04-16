package models.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Jørgen on 09.03.2018.
 */


public class Cannon implements Drawable{

    private int x;
    private int y;
    private int width;
    private int height;
    private Sprite wheel;
    private Sprite cannon;
    private Body body;
    private boolean shotsFired;

    private float angle;
    private float power;

    public Cannon(int x, int y, int width, int height, Sprite cannon, Sprite wheel, Body body) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.wheel = wheel;
        this.cannon = cannon;
        this.body = body;
        this.shotsFired = false;
    }

    @Override
    public Sprite getDrawable() {
        return cannon;
    }

    @Override
    public Body getBody() {
        return body;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Sprite getWheel() {
        return wheel;
    }

    public void setWheel(Sprite sprite) {
        this.wheel = sprite;
    }

    public Sprite getCannon() {
        return cannon;
    }

    public void setCannon(Sprite cannon) {
        this.cannon = cannon;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }

    public boolean isShotsFired() {
        return shotsFired;
    }

    public void setShotsFired(boolean shotsFired) {
        this.shotsFired = shotsFired;
    }

    //Fires the shot, with a given angle and power
    public void Fire() {
    }

    //Updates the game with the interval dt
    public void update(float dt) {
        cannon.setRotation(getAngle());
    }



}
