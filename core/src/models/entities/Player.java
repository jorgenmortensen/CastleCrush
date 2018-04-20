package models.entities;

/**
 * Created by Jørgen on 09.03.2018.
 */

public class Player {

    private String id;
    private GameWinningObject gameWinningObject;

    public Player(String id) {
        this.id = id;
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
}
