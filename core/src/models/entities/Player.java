package models.entities;

/**
 * Created by Jørgen on 09.03.2018.
 */

public class Player {

    private boolean angleActive, powerActive;

    private String id;

    private Cannon cannon;

    public Player(Cannon cannon) {
        angleActive = true;
        powerActive = false;
        this.cannon = cannon;
    }

    public void switchAngleActive(){
        if (angleActive==true){
            angleActive=false;
        } else if(angleActive==false){
            angleActive=true;
        }
    }

    public void switchPowerActive(){
        if (powerActive==true){
            powerActive=false;
        } else if (powerActive==false){
            powerActive=true;
        }
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAngleActive(){return angleActive;}

    public boolean isPowerActive() {return powerActive;}

    public Cannon getCannon() {
        return cannon;
    }
}
