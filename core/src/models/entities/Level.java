package models.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jørgen on 09.03.2018.
 */

public class Level {

    private int x;
    private int y;
    private int width;
    private int height;

    private Castle castleLeft, castleRight;
    private Cannon cannonLeft, cannonRight;
    private Projectile projectile;

    public Level(List<Castle> castles/*, List<Cannon> cannons Projectile projectile */ ) {
        //this.powerUps = powerUps;
        //this.castles = castles;
        //this.cannons = cannons;
        //this.projectile = projectile;
    }

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
}
