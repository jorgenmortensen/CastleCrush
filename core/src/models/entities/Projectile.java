package models.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.castlecrush.game.CastleCrush;

import java.util.Timer;

import models.GameWorld;

public class Projectile extends PhysicalGameObject {
    private static final Projectile ourInstance = new Projectile(null, null);

    public Projectile(Body body, Sprite sprite) {
        super(body, sprite);
    }

    public static Projectile getInstance() {
        return ourInstance;
    }

    private boolean isFired;
    private Sound fireSound;

    public void init(Body body,  Sprite sprite, GameWorld world) {
        this.body = body;
        this.sprite = sprite;
        this.isFired = false;
        fireSound = Gdx.audio.newSound(Gdx.files.internal("sound_cannon.ogg"));
    }

    public boolean isFired() {
        return isFired;
    }

    public void setFired(boolean fired) {
        isFired = fired;
    }

    //Fires the shot, with a given angle and power
    public void fire(Vector2 velocity) {
        body.setLinearVelocity(velocity);
        body.setAngularVelocity(0);
        if (CastleCrush.soundEffectsOn){
            fireSound.play(0.5f);
        }
        System.out.println(velocity.x + "velocity");
    }

    public double getAbsoluteSpeed(){
        float x = body.getLinearVelocity().x;
        float y = body.getLinearVelocity().y;
        return Math.sqrt(x*x+y*y);
    }


    @Override
    public String toString() {
        return "Projectile{" +
                "position=" + getBody().getPosition() +
                ", width=" + getSprite().getWidth() +
                ", height=" + getSprite().getHeight() +
                ", body=" + body +
                ", sprite=" + sprite +
                ", velocity=" + getBody().getLinearVelocity() +
                '}';
    }
}
