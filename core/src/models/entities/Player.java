package models.entities;

/**
 * Created by Jørgen on 09.03.2018.
 */

public class Player {

    private boolean angleActive, powerActive;

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

    private Cannon cannon;

    public Player(Cannon cannon) {
//        moved to cannon
//        angleActive = true;
//        powerActive = false;
        this.cannon = cannon;
    }
//moved to cannon
//    public void switchAngleActive(){
//        if (angleActive==true){
//            angleActive=false;
//        } else if(angleActive==false){
//            angleActive=true;
//        }
//    }
//
//    public void switchPowerActive(){
//        if (powerActive){
//            powerActive=false;
//        } else if (powerActive){
//            powerActive=true;
//        }
//    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
//moved to cannon
//    public boolean isAngleActive(){return angleActive;}
//
//    public boolean isPowerActive() {return powerActive;}

    public Cannon getCannon() {
        return cannon;
    }
}
