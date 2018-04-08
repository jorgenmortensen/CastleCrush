package models.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Jørgen on 09.03.2018.
 */


public class Cannon implements Drawable{

    private int x;
    private int y;
    private int width;
    private int height;
    private Sprite sprite;

    private float angle;
    private float power;

    public Cannon(int x, int y, int width, int height, Sprite sprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
    }

    @Override
    public Sprite getDrawable() {
        return sprite;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int getHeight() {
        return this.height;
    }


    //Fires the shot, with a given angle and power
    public void Fire() {

    }

    //Updates the game with the interval dt
    public void update(float dt) {

    }



}
