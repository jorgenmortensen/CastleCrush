package models.entities;

import com.badlogic.gdx.physics.box2d.Body;

public class OneWayWall {
//    Class used for invisible walls that prevent objects other than a Projectile to move past it
    private Body body;

    public OneWayWall(Body body){
        this.body = body;
    }

    public Body getBody() {
        return body;
    }
}