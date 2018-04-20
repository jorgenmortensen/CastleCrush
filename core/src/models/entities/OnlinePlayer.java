package models.entities;

import models.GameWorld;


public class OnlinePlayer extends Player {

    private boolean isYou;

    public OnlinePlayer(String id, GameWorld world /*, boolean isYou*/) {
        super(id, world);
        this.isYou = isYou;
    }

    public boolean isYou() {
        return isYou;
    }
//    ADD OR OVERRIDE METHODS YOU NEED
//    IF YOU OVERRIDE REMEMBER TO SET FIELDS AS PROTECTED, NOT PRIVATE!!!

}
