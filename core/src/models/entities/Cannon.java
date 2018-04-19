package models.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Jørgen on 09.03.2018.
 */


public class Cannon {

    private boolean powerActive;
    private boolean angleActive;
    private float x;
    private float y;
    private float width;
    private float height;
    private Sprite wheelSprite;
    private Sprite cannonSprite;
    private Body body;
    private boolean shotsFired, angleUp, powerUp;
    private float angleSpeed = 3f, powerSpeed = 4f, maxPower = 100;
    private float factor;
    private boolean hasFiredThisTurn;
    private Player player;

    private float angle;
    private float power;

    public Cannon(Player player, float x, float y, float width, float height, Sprite cannon, boolean facingRight) {
        this.player = player;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.cannonSprite = cannon;
        this.shotsFired = true;
        this.angleUp = true;
        this.powerUp = false;
        this.angle = 90;
        angleActive = true;
        powerActive = false;
        hasFiredThisTurn = false;

        cannonSprite.setPosition(x, y);
        cannonSprite.setSize(width, height);
        cannonSprite.setOriginCenter();

        if (facingRight){
            factor = 1;
        } else if (!facingRight){
            factor = -1;
            cannonSprite.setRotation(180);
        }

    }

    public void progressShootingSequence() {
        if (isAngleActive()) {
            switchAngleActive();
            switchPowerActive();

        } else if (isPowerActive()) {
            isPowerActive();

        }

        if (!isAngleActive() && !isPowerActive() && !hasFiredThisTurn) {
            hasFiredThisTurn = true;
            player.fireCannon();
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
        if (getPower() >= maxPower) {
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


    public void switchAngleActive(){
       angleActive = !angleActive;
    }

    public void switchPowerActive(){
      powerActive = !powerActive;
    }



    public Sprite getCannonSprite() {
        return cannonSprite;
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
    public boolean isAngleActive(){return angleActive;}

    public boolean isPowerActive() {return powerActive;}

    //Updates the game with the interval dt
    public void update(float dt) {
        cannonSprite.setRotation(90 - factor*getAngle());
    }

    public float getShootingAngle (){
        return 90 - factor*getAngle();
    }

    public float getMaxPower() {
        return maxPower;
    }

    public boolean isHasFiredThisTurn() {
        return hasFiredThisTurn;
    }

    public void resetHasFiredThisTurn() {
        this.hasFiredThisTurn = false;
    }
}
