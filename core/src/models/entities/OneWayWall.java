package models.entities;

import com.badlogic.gdx.physics.box2d.Body;

public class OneWayWall {

    private Body body;
    private boolean letThroughRight;

    public OneWayWall(Body body, boolean letThroughRight){
        this.body = body;
        this.letThroughRight = letThroughRight;
    }


    public Body getBody() {
        return body;
    }

    public boolean isLetThroughRight() {
        return letThroughRight;
    }
}
