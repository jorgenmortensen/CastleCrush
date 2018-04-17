package models.entities;

/**
 * Created by Jørgen on 09.03.2018.
 */

public class Player {

    public Player() {
        angleActive = true;
        powerActive = false;
    }

    private boolean angleActive, powerActive;

    private String id;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAngleActive(){return angleActive;}

    public boolean isPowerActive() {return powerActive;}

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
}
