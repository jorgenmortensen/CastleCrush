package models.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Jørgen on 09.03.2018.
 */


public class Cannon implements Drawable{

    private float x;
    private float y;
    private float width;
    private float height;
    private Sprite wheelSprite;
    private Sprite cannonSprite;
    private Body body;
    private boolean shotsFired, angleUp, powerUp;
    private float angleSpeed = (float)3, powerSpeed = (float)4;
    private float factor;

    private float angle;
    private float power;

    public Cannon(float x, float y, float width, float height, Sprite cannon, Sprite wheel, Body body, boolean facingRight) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.wheelSprite = wheel;
        this.cannonSprite = cannon;
        this.body = body;
        this.shotsFired = true;
        this.angleUp = true;
        this.powerUp = false;
        this.angle = 90;

        cannonSprite.setPosition(x, y);
        cannonSprite.setSize(width, height);
        cannonSprite.setOriginCenter();
        wheelSprite.setPosition(x,y);
        wheelSprite.setSize(width/2, height/2);

        if (facingRight){
            factor = 1;
        } else if (!facingRight){
            factor = -1;
            cannonSprite.setRotation(180);
        }

    }

    public void updateAngle(){
        if (getAngle() >= 90) {
            angleUp = false;
        } else if (getAngle() <= 0) {
            angleUp = true;
        }
        if (angleUp) {
            setAngle(getAngle() + angleSpeed);
        } else if (!angleUp) {
            setAngle(getAngle() - angleSpeed);
        }
    }

    public void updatePower(){
        if (getPower() >= 100) {
            powerUp = false;
        } else if (getPower() <= 0) {
            powerUp = true;
        }
        if (powerUp) {
            setPower(getPower() + powerSpeed);
        } else if (!powerUp) {
            setPower(getPower() - powerSpeed);
        }
    }

    @Override
    public Sprite getDrawable() {
        return cannonSprite;
    }

    @Override
    public Body getBody() {
        return body;
    }

    public float getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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

    public Sprite getWheel() {
        return wheelSprite;
    }

    public void setWheel(Sprite sprite) {
        this.wheelSprite = sprite;
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
        cannonSprite.setRotation(90 - factor*getAngle());
    }



}
