package models.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jørgen on 09.03.2018.
 */

public class Castle {

    private int x;
    private int y;
    private int width;
    private int height;
    private List<Box> boxes;
    private Player player;
    private Cannon cannon;

    public Castle(int x, int y, List boxes, Cannon cannon) {
        this.x = x;
        this.y = y;
        this.boxes = boxes;
        this.cannon = cannon;
    }

    public List<Box> getBoxes () {return boxes;}

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return this.x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return this.height;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void removeBoxes(Box box) {
        this.boxes.remove(box);
    }

}
