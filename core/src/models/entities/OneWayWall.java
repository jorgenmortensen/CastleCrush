package models.entities;

import com.badlogic.gdx.physics.box2d.Body;

public class OneWayWall {

    private Body body;

    public OneWayWall(Body body){
        this.body = body;
    }

    public Body getBody() {
        return body;
    }
}