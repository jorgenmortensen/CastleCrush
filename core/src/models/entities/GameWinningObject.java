package models.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
//import com.sun.xml.internal.ws.client.sei.ResponseBuilder;

/**
 * Created by Jørgen on 09.03.2018.
 */

public class GameWinningObject implements Drawable {

    private int x;
    private int y;
    private int width;
    private int height;
    private Sprite sprite;
    private Body body;

    private boolean isHit;

    @Override
    public Sprite getDrawable() {
        return null;
    }

    @Override
    public Body getBody() {
        return body;
    }

}
