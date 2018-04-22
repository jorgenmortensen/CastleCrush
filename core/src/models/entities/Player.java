package models.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import models.GameWorld;

/**
 * Created by Jørgen on 09.03.2018.
 */

public class Player {

    private boolean angleActive, powerActive;

    private String id;
    private GameWinningObject gameWinningObject;
    protected GameWorld world;

    private Cannon cannon;

    public Player(){
    }

    public Player(String id, GameWorld world) {
        this.id = id;
        this.world = world;

    }

    public void setGameWinningObject(GameWinningObject gameWinningObject){
        this.gameWinningObject = gameWinningObject;
    }

    public GameWinningObject getGameWinningObject(){
        return gameWinningObject;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cannon getCannon() {
        return cannon;
    }

    public void fireCannon(Vector2 velocity) {
        world.fireProjectile(velocity);
    }

    public void setCannon(Cannon cannon) {
        this.cannon = cannon;
    }

    public void setWorld (GameWorld w){
        world = w;

    }

}
