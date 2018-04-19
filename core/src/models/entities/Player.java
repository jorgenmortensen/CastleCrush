package models.entities;

/**
 * Created by Jørgen on 09.03.2018.
 */

public class Player {

    private boolean angleActive, powerActive;

    private String id;
    private GameWinningObject gameWinningObject;


    private Cannon cannon;

    public Player(String id, Cannon cannon) {
//        moved to cannon
//        angleActive = true;
//        powerActive = false;
        this.id = id;
        this.cannon = cannon;

    }

    public void setGameWinningObject(GameWinningObject gameWinningObject){
        this.gameWinningObject = gameWinningObject;
    }

    public GameWinningObject getGameWinningObject(){
        return gameWinningObject;
    }
    //    public void switchAngleActive(){
    //        if (angleActive==true){
    //            angleActive=false;
    //        } else if(angleActive==false){
    //            angleActive=true;
    //        }
//moved to cannon
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
