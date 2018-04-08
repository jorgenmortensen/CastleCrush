package entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.util.List;

/**
 * Created by Jørgen on 09.03.2018.
 */

public class Castle implements Drawable {

    private int x;
    private int y;
    private int width;
    private int height;
    private List<Box> boxes;
    private Player player;


    @Override
    public void Draw(SpriteBatch batch) {

    }

    @Override
    public void setPosition(Vector3 position) {

    }

    @Override
    public Vector3 getPosition() {
        return null;
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
