package models.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

import java.util.Timer;
import java.util.TimerTask;

import models.MockGameWorld;
import models.states.playStates.SinglePlayerState;

/**
 * Created by Jørgen on 09.03.2018.
 */

public class Projectile implements Drawable {

    private int x, y;
    private float width;
    private float height;
    private Vector2 position;

    private Body body;
    private Timer timer;
    private Sprite sprite;
    private boolean isFired;
    private boolean hasHit = false;
    private MockGameWorld gameWorld;
    private boolean scheduleActive = false;
    private SinglePlayerState state;



    private Vector2 velocity;

    public Projectile(Body body, Vector2 position, Sprite sprite,
                      float width, float height, MockGameWorld world, SinglePlayerState state) {
        this.body = body;
        this.position = position;
        this.sprite = sprite;
        this.width = width;
        this.height = height;
        this.body = body;
        this.sprite = sprite;
        this.velocity = velocity;
        this.gameWorld = world;
        this.isFired = false;
        this.state = state;
    }

    public void setHasHit(boolean hasHit){this.hasHit = hasHit;}

    public void scheduleSelfDestruct (final Fixture fixture){
        if (!scheduleActive){
            final SinglePlayerState stat = state;
            if (getSpeed(getBody().getLinearVelocity()) < 3) {
                setHasHit(true);
                gameWorld.addBodyToDestroy(fixture);
                Cannon cannon = gameWorld.getCannons().get(0);
                gameWorld.createProjectile(cannon.getX(), cannon.getY(), gameWorld.getScreenWidth() / 40, stat);
            }
            /*timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // Delete ball

                    if (getSpeed(getBody().getLinearVelocity()) < 3){
                        setHasHit(true);
                        gameWorld.addBodyToDestroy(fixture);
                        timer.cancel();
                        stat.switchPlayer();
                        Cannon cannon = gameWorld.getCannons().get(0);
                        gameWorld.createProjectile(cannon.getX(),cannon.getY(), gameWorld.getScreenWidth()/40, stat);

                    }

                }
            }, 3000,500);*/
            this.scheduleActive = true;
        }

    }

    public Body getBody() {
        return body;
    }

    @Override
    public Sprite getDrawable() {
        return sprite;
    }

    public boolean getHasHit() {
        return hasHit;
    }

    private double getSpeed (Vector2 vector){
        double x = vector.x;
        double y = vector.y;

        return Math.sqrt(x*x+y*y);
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

    public float getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getHeight() {
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


    public boolean isFired() {
        return isFired;
    }

    public void setFired(boolean fired) {
        isFired = fired;
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
