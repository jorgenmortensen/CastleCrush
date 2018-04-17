package models.components;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by erikkjernlie on 08/04/2018.
 */

public class SlidingObjectXdirection extends Button {
    private int GRAVITY_X_DIRECTION;

    private float startXpos;

    private Vector3 velocity;

    public SlidingObjectXdirection(float finalXpos, float ypos, int btnWidth, int btnHeight, Sprite btn, float startXpos, int GRAVITY_X_DIRECTION) {
        super(startXpos, ypos, btnWidth, btnHeight, btn);
        this.GRAVITY_X_DIRECTION = GRAVITY_X_DIRECTION;
        velocity = new Vector3(GRAVITY_X_DIRECTION,0,0);
        this.startXpos = startXpos;
    }


    public void update(float dt){
        if (position.x > -this.getBtnWidth()) {
            velocity.set(GRAVITY_X_DIRECTION, 0, 0);
        } else {
            this.setXpos(this.startXpos);
        }
        velocity.scl(dt);
        position.add(velocity.x,0,0);

        // reverses what we scaled earlier
        velocity.scl(1/dt);

    }


}
