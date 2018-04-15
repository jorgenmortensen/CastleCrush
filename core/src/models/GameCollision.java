package models;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import models.entities.Box;
import models.entities.Projectile;

/**
 * Created by erikkjernlie on 15/04/2018.
 */

public class GameCollision implements ContactListener {

    public GameCollision(){

    }

    @Override
    public void beginContact(Contact contact) {
        //System.out.println("Contact");

        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureA().getBody();
        if (contact.isTouching()){

            if (a.getUserData() instanceof Box && b.getUserData() instanceof Box){

            } else {
                System.out.println(a.getUserData());
                System.out.println(b.getUserData());
            }
        }

        //String aaa = (String) a.getUserData();
        //String bbb = (String) b.getUserData();

        if (a.getUserData() instanceof Projectile){
            //System.out.println("Prosjektil");
        }
        if (b.getUserData() instanceof Projectile){
           // System.out.println("Prosjektil");
        }

        if (a.getUserData() instanceof Box && b.getUserData() instanceof Box){
            //System.out.println("Bokser");
        } else {
            //System.out.println(a.getUserData());
            //System.out.println(b.getUserData());
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
