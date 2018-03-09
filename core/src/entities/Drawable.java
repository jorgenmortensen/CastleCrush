package entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Jørgen on 09.03.2018.
 */

public interface Drawable {

    public void Draw(SpriteBatch batch);

    public void setX(int x);

    public int getX();

    public void setY(int y);

    public int getY();

    public void setWidth(int width);

    public int getWidth();

    public void setHeight(int height);

    public int getHeight();



}
