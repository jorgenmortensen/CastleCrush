package models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import models.entities.Box;
import models.entities.OneWayWall;
import models.entities.Projectile;

/**
 * Created by erikkjernlie on 15/04/2018.
 */

/* IMPORTANT COMMENTS
 * We need to make a fixture first, making a body directly doesn't work (weird bug):
 * Body a = contact.getFixtureA().getBody(); // not working
 * Body b = contact.getFixtureA().getBody(); //not working
 * Object a = contact.getFixtureA().getUserData() - seeems to be differant than:
 * Object b = contact.getFixtureA().getBody().getUserData() - even though they both return an object.
 */

public class GameCollision implements ContactListener {

    private GameWorld gameWorld;
    private boolean hasTurnedOfContact;

    public GameCollision(GameWorld world) {
        this.gameWorld = world;
        this.hasTurnedOfContact = false;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        Body bodyA = fa.getBody();
        Body bodyB = fb.getBody();
        BodyDef.BodyType objectAtype = bodyA.getType();
        BodyDef.BodyType objectBtype = bodyB.getType();
        BodyDef.BodyType staticBody = BodyDef.BodyType.StaticBody;
        Object objectA = bodyA.getUserData();
        Object objectB = bodyB.getUserData();

        if (contact.isTouching()) {
            if ((!(objectAtype.equals(staticBody))) && (!(objectBtype.equals(staticBody)))) {
                if (objectA instanceof Projectile || objectB instanceof Projectile) {
                    if (objectA instanceof Box) {
                        // Speed limitation. The value is set based on the gravity (which is 10 m/s^2).
                        if (getSpeed(bodyB.getLinearVelocity()) > 10) {
                            ((Box) objectA).isHit(true);
                            gameWorld.addBodyToDestroy(fa);
                            Sprite sprite = ((Box) objectA).getDrawable();
                            gameWorld.removeFromRenderlist(sprite);
                        }
                    } else if (objectB instanceof Box) {
                        // Speed limitation. The value is set based on the gravity (which is 10 m/s^2).
                        if (getSpeed(bodyA.getLinearVelocity()) > 10) {
                            ((Box) objectA).isHit(true);
                            gameWorld.addBodyToDestroy(fb);
                            Sprite sprite = ((Box) objectB).getDrawable();
                            gameWorld.removeFromRenderlist(sprite);

                        }
                    }
                }
            }

        }
    }


    private double getSpeed(Vector2 vector) {
        double x = vector.x;
        double y = vector.y;

        return Math.sqrt(x * x + y * y);
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        Object objectA = fa.getBody().getUserData();
        Object objectB = fb.getBody().getUserData();
        // One way wall logic
        if (objectA instanceof OneWayWall && objectB instanceof Projectile){
            contact.setEnabled(false);
            hasTurnedOfContact = true;
        } else if (objectB instanceof OneWayWall && objectA instanceof Projectile) {
            contact.setEnabled(false);
            hasTurnedOfContact = true;
        }
    }


    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        if (hasTurnedOfContact){
            contact.setEnabled(true);
            hasTurnedOfContact = false;
        }
    }
}