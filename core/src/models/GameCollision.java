package models;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Created by erikkjernlie on 15/04/2018.
 */

public class GameCollision implements ContactListener {

    public GameCollision(){

    }

    @Override
    public void beginContact(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureA().getBody();
        if (a.getUserData() == "Prosjektil" || b.getUserData() == "Prosjektil"){
            if (contact.isTouching()){
                System.out.println("Prosjektilet har truffet noe");
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
}
