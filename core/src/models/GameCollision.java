package models;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import models.entities.Box;
import models.entities.GameWinningObject;
import models.entities.OldProjectile;
import models.entities.OneWayWall;

/**
 * Created by erikkjernlie on 15/04/2018.
 */

public class GameCollision implements ContactListener {
    private GameWorld gameWorld;
    private Sound sound;
    private boolean hasTurnedOfContact;

    public GameCollision(GameWorld world) {
        this.gameWorld = world;
        this.hasTurnedOfContact = false;
    }

    @Override
    public void beginContact(Contact contact) {

        // We need to make a fixture first, making a body directly doesn't work
        //Body a = contact.getFixtureA().getBody();
        //Body b = contact.getFixtureA().getBody();

        // KEEP AS IS
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (contact.isTouching()) {
            if (!(fa.getBody().getType().equals(BodyDef.BodyType.StaticBody))) {
                if (fa.getBody().getUserData() instanceof OldProjectile || fb.getBody().getUserData() instanceof OldProjectile) {
                    if (fa.getBody().getUserData() instanceof GameWinningObject) {
                        ((GameWinningObject) fa.getBody().getUserData()).isHit(true);
                        gameWorld.addBodyToDestroy(fa);
                        System.out.println("COllided.");
                    } else if (fb.getBody().getUserData() instanceof GameWinningObject) {
                        ((GameWinningObject) fa.getBody().getUserData()).isHit(true);
                        gameWorld.addBodyToDestroy(fb);
                        System.out.println("COllided.");
                    } else if (fa.getBody().getUserData() instanceof Box) {
                        // Speed limitation
                        if (getSpeed(fb.getBody().getLinearVelocity()) > 10) {
                            ((Box) fa.getBody().getUserData()).isHit(true);
                            gameWorld.addBodyToDestroy(fa);

                        }
                        if (fb.getBody().getUserData() instanceof OldProjectile) {
                            ((OldProjectile) fb.getBody().getUserData()).scheduleSelfDestruct(fb);
                        }
                    } else if (fb.getBody().getUserData() instanceof Box) {
                        // Speed limitation
                        if (getSpeed(fa.getBody().getLinearVelocity()) > 10) {
                            ((Box) fa.getBody().getUserData()).isHit(true);
                            gameWorld.addBodyToDestroy(fb);

                        }
                        if (fa.getBody().getUserData() instanceof OldProjectile) {
                            ((OldProjectile) fa.getBody().getUserData()).scheduleSelfDestruct(fa);
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

        // One way wall logic
        if (fa.getBody().getUserData() instanceof OneWayWall || fb.getBody().getUserData() instanceof OneWayWall) {
            if (fa.getBody().getUserData() instanceof OneWayWall) {
                if (fb.getBody().getUserData() instanceof OldProjectile) {
                    contact.setEnabled(false);
                    hasTurnedOfContact = true;
                }
            } else if (fb.getBody().getUserData() instanceof OneWayWall) {
                if (fa.getBody().getUserData() instanceof OldProjectile) {
                    contact.setEnabled(false);
                    hasTurnedOfContact = true;
                }
            }
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
