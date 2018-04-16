package models.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

import java.util.Timer;
import java.util.TimerTask;

import models.MockGameWorld;

/**
 * Created by Jørgen on 09.03.2018.
 */

public class Projectile implements Drawable {

    private int x, y;
    private int width;
    private int height;
    private Body body;
    private Sprite sprite;
    private Timer timer;
    private boolean hasHit = false;
    private MockGameWorld gameWorld;
    private boolean scheduleActive = false;

    private Vector2 velocity;

    public Projectile(Body body, Sprite sprite, int width, int height, Vector2 velocity, MockGameWorld gameWorld) {
        this.width = width;
        this.height = height;
        this.body = body;
        this.sprite = sprite;
        this.velocity = velocity;
        this.gameWorld = gameWorld;
    }

    public Body getBody() {
        return body;
    }

    @Override
    public Sprite getDrawable() {
        return sprite;
    }

    public void setHasHit(boolean hasHit){this.hasHit = hasHit;}

    public void scheduleSelfDestruct (final Fixture fixture){
        if (!scheduleActive){
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // Delete ball

                    if (getSpeed(getBody().getLinearVelocity())<1){
                        setHasHit(true);
                        gameWorld.addBodyToDestroy(fixture);
                        timer.cancel();

                    }

                }
            }, 3000,500);
            this.scheduleActive = true;
        }

    }

    private double getSpeed (Vector2 vector){
        double x = vector.x;
        double y = vector.y;

        return Math.sqrt(x*x+y*y);
    }

}
