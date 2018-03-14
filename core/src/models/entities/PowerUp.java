package entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Jørgen on 09.03.2018.
 */

public class PowerUp implements Drawable {

    private int x;
    private int y;
    private int width;
    private int height;

    private Vector3 velocity;
    private Image image;

    @Override
    public void Draw(SpriteBatch batch) {

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

    public void move(int dx, int dy) {

    }

    public void update(float dt) {

    }




}
