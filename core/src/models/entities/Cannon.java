package models.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Jørgen and Nina on 09.03.2018.
 */


public class Cannon {

    private boolean powerActive;
    private boolean angleActive;
    private float x;
    private float y;
    private float width;
    private float height;
    private Sprite cannonSprite;
    private Sprite powerBarSprite;
    private boolean shotsFired, angleUp, powerUp;
    private float angleSpeed = 3f, powerSpeed = 4f, maxPower = 100;
    private float factor;
    private boolean hasFiredThisTurn;
    private Player player;

    private float angle;
    private float power;

    public Cannon(Player player, float x, float y, float width, float height, Sprite cannon, Sprite powerBarSprite, boolean facingRight) {
        this.player = player;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.cannonSprite = cannon;
        this.powerBarSprite = powerBarSprite;
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
        powerBarSprite.setPosition(x, y);
        powerBarSprite.setSize(width, height/3);

        if (facingRight){
            factor = 1;
        } else if (!facingRight){
            factor = -1;
            cannonSprite.setRotation(180);
        }

    }

    public void activate() {
        if (!isAngleActive()){
            switchAngleActive();
        }
        if (isPowerActive()) {
            switchPowerActive();
        }
    }

    public void deactivate() {
        if (isAngleActive()){
            switchAngleActive();
        }
        if (isPowerActive()){
            switchPowerActive();
        }
        setPower(0);
        setAngle(0);
        resetHasFiredThisTurn();
    }

    public void progressShootingSequence() {
        if (isAngleActive()) {
            switchAngleActive();
            switchPowerActive();
            System.out.println("stop cannon move");
        } else if (isPowerActive()) {
            activatePowerBar();
            switchPowerActive();
            System.out.println("power is active");
        }

        if (!isAngleActive() && !isPowerActive() && !hasFiredThisTurn) {
            hasFiredThisTurn = true;
            System.out.println(player+"     player exists");
            Vector2 velocity= new Vector2 ((float)(Math.cos(getShootingAngle()*Math.PI/180) * power/3),
                    (float)(Math.sin(getShootingAngle()*Math.PI/180) * power/3));
            player.fireCannon(velocity);
        }
    }

    public void update() {
        if (isAngleActive()) {
            updateAngle();
        } else if (isPowerActive()) {
            updatePower();
        }
        cannonSprite.setRotation(90 - factor*getAngle());
    }

    private void updateAngle(){
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

    private void updatePower(){
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

    public void updatePowerBar() {
        powerBarSprite.setSize(width * power / maxPower, powerBarSprite.getHeight());
        }

    private void switchAngleActive(){
       angleActive = !angleActive;
    }

    private void switchPowerActive(){
      powerActive = !powerActive;
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

    private float getAngle() {
        return angle;
    }

    private void setAngle(float angle) {
        this.angle = angle;
    }

    public float getPower() {
        return power;
    }

    private void setPower(float power) {
        this.power = power;
    }

    private boolean isAngleActive(){return angleActive;}

    private boolean isPowerActive() {return powerActive;}

    //Updates the game with the interval dt

    public float getShootingAngle (){
        return 90 - factor*getAngle();
    }


    private void resetHasFiredThisTurn() {
        this.hasFiredThisTurn = false;
    }

    public void activatePowerBar() {
        powerBarSprite.setPosition(x, y);
        powerBarSprite.setSize(width, height/3);
    }
}
