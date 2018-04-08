package entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Jørgen on 09.03.2018.
 */

public interface Drawable {

    public void Draw(SpriteBatch batch);

    public void setPosition(Vector3 position);

    public Vector3 getPosition();

    public void setWidth(int width);

    public int getWidth();

    public void setHeight(int height);

    public int getHeight();



}
