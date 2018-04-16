package models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import models.entities.Box;
import models.entities.Projectile;

/**
 * Created by erikkjernlie on 15/04/2018.
 */

public class GameCollision implements ContactListener {
    private MockGameWorld gameWorld;

    public GameCollision(MockGameWorld world){
        this.gameWorld = world;
    }

    @Override
    public void beginContact(Contact contact) {

        // We need to make a fixture first, making a body directly doesn't work
        //Body a = contact.getFixtureA().getBody();
        //Body b = contact.getFixtureA().getBody();

        // KEEP AS IS
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (contact.isTouching()){
            if (!(fa.getBody().getType().equals(BodyDef.BodyType.StaticBody))){
                if (fa.getBody().getUserData() instanceof Projectile || fb.getBody().getUserData() instanceof Projectile){
                    //System.out.println("Prosjektil");
                    if (fa.getBody().getUserData() instanceof Box) {
                        // Speed limitation
                        if (getSpeed(fb.getBody().getLinearVelocity())>5){
                            ((Box) fa.getBody().getUserData()).isHit(true);
                            gameWorld.addBodyToDestroy(fa);
                            System.out.println(getSpeed(fb.getBody().getLinearVelocity()));
                        }
                    } else if (fb.getBody().getUserData() instanceof Box) {
                        // Speed limitation
                        if (getSpeed(fa.getBody().getLinearVelocity())>5){
                            ((Box) fa.getBody().getUserData()).isHit(true);
                            gameWorld.addBodyToDestroy(fb);
                            System.out.println(getSpeed(fa.getBody().getLinearVelocity()));
                        }

                    }
                }
            }

        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private double getSpeed (Vector2 vector){
        double x = vector.x;
        double y = vector.y;

        return Math.sqrt(x*x+y*y);
    }
}
