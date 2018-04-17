package models.components;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Erik on 06.04.2018.
 */

public class GravityButton extends Button {

    private static final int GRAVITY = -15;

    private float finalYpos;

    private int vel = 500;

    private Vector3 velocity;

    public GravityButton(float xpos, float finalYpos, int btnWidth, int btnHeight, Sprite btn, float startYpos) {
        super(xpos, startYpos, btnWidth, btnHeight, btn);
        velocity = new Vector3(0,0,0);
        this.finalYpos = finalYpos;
    }


    public void update(float dt){
        if (position.y > finalYpos) {
            velocity.add(0, GRAVITY, 0);
        }
        velocity.scl(dt);
        position.add(0,velocity.y,0);

        // reverses what we scaled earlier
        velocity.scl(1/dt);
        if (position.y < finalYpos ) {
            bump(vel);
        }

    }

    // when the button hits the desired position, it makes a little bump
    // when numberOfBumps =
    public void bump(int vel){
        velocity.y = vel;
        if (position.y < finalYpos && vel > 10){
            this.vel -= 100;
        } else {
            this.vel = 0;
        }
    }
}
