package models.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Jørgen on 09.03.2018.
 */


public class Cannon implements Drawable{

    private int x;
    private int y;
    private int width;
    private int height;
    private Sprite sprite;
    private Body body;

    private float angle;
    private float power;

    public Cannon(int x, int y, int width, int height, Sprite sprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
        this.body = body;
    }

    @Override
    public Sprite getDrawable() {
        return sprite;
    }

    @Override
    public Body getBody() {
        return body;
    }



    //Fires the shot, with a given angle and power
    public void Fire() {

    }

    //Updates the game with the interval dt
    public void update(float dt) {

    }



}
