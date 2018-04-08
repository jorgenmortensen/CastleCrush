package entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Jørgen on 09.03.2018.
 */


public class Cannon implements Drawable{

    private int x;
    private int y;
    private int width;
    private int height;

    private float angle;
    private float power;

    Sprite cannon;
    Sprite wheel;

    public Cannon(int x, int y, int width, int height, float angle, float power, Sprite cannon, Sprite wheel) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.angle = angle;
        this.power = power;
        this.cannon = cannon;
        this.wheel = wheel;
    }

    @Override
    public void Draw(SpriteBatch batch) {

    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getY() {
        return y;
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

    public Sprite getCannon() {
        return cannon;
    }

    public void setCannon(Sprite cannon) {
        this.cannon = cannon;
    }

    public Sprite getWheel() {
        return wheel;
    }

    public void setWheel(Sprite wheel) {
        this.wheel = wheel;
    }

    //Fires the shot, with a given angle and power
    public void Fire() {

    }

    //Updates the game with the interval dt
    public void update(float dt) {

    }



}
