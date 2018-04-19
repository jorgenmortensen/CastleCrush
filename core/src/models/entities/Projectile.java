package models.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Timer;

import models.GameWorld;

public class Projectile implements Drawable {
    private static final Projectile ourInstance = new Projectile();

    public static Projectile getInstance() {
        return ourInstance;
    }

    private int x, y;
    private float width;
    private float height;
    private Vector2 position;

    private Body body;
    private Timer timer;
    private Sprite sprite;
    private boolean isFired;
    private boolean hasHit = false;
    private World physicsWorld;
    private boolean scheduleActive = false;



    private Vector2 velocity;

    public void init(Body body,  Sprite sprite, GameWorld world) {
        this.body = body;
        this.position = position;
        this.sprite = sprite;
        this.width = width;
        this.height = height;
        this.body = body;
        this.sprite = sprite;
        this.velocity = velocity;
        this.physicsWorld = physicsWorld;
        this.isFired = false;
    }

    public void setHasHit(boolean hasHit){this.hasHit = hasHit;}


//    flytte til world
//    public void scheduleSelfDestruct (final Fixture fixture){
//        if (!scheduleActive){
//            if (getSpeed(getBody().getLinearVelocity()) < 3) {
//                setHasHit(true);
//                gameWorld.addBodyToDestroy(fixture);
//            }
//            /*timer = new Timer();
//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    // Delete ball
//
//                    if (getSpeed(getBody().getLinearVelocity()) < 3){
//                        setHasHit(true);
//                        gameWorld.addBodyToDestroy(fixture);
//                        timer.cancel();
//                        stat.switchPlayer();
//                        Cannon cannon = gameWorld.getCannons().get(0);
//                        gameWorld.createProjectile(cannon.getX(),cannon.getY(), gameWorld.getScreenWidth()/40, stat);
//
//                    }
//
//                }
//            }, 3000,500);*/
//            this.scheduleActive = true;
//        }



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
        return "OldProjectile{" +
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

    public double getAbsoluteSpeed(){
        float x = body.getLinearVelocity().x;
        float y = body.getLinearVelocity().y;
        return Math.sqrt(x*x+y*y);
    }





}
