package models.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Jørgen on 09.03.2018.
 */

public class PowerUp implements Drawable {

    private int x;
    private int y;
    private int width;
    private int height;
    private Sprite sprite;
    private Body body;

    private Vector3 velocity;
    private Image image;


    @Override
    public Sprite getDrawable() {
        return sprite;
    }

    @Override
    public Body getBody() {
        return body;
    }

    public void move(int dx, int dy) {

    }

    public void update(float dt) {

    }




}
