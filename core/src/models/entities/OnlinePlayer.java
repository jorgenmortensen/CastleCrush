package models.entities;

import com.badlogic.gdx.math.Vector2;
import com.sun.org.apache.xpath.internal.SourceTree;

import models.GameWorld;
import models.OnlineMultiplayerWorld;


public class OnlinePlayer extends Player {
    public String participantId = "";
    public String playerId = "";
    public String displayName = "";
    private boolean isSelf = false;
    private boolean isHost = false;


  /*  public OnlinePlayer(String id, GameWorld world *//**//*, boolean isYou*//**//*) {
        super(id, world);
    }
*/

    public OnlinePlayer(String participantId, String displayName) {
        super();
        this.participantId = participantId;
        this.displayName = displayName;
    }

    public OnlinePlayer(String participantId, String displayName, boolean isSelf) {
        this(participantId, displayName);
        this.isSelf = isSelf;
    }

    public OnlinePlayer(String playerId, String participantId, String displayName) {
        this(participantId, displayName);
        this.playerId = playerId;
    }


    @Override
    public void fireCannon(Vector2 velocity){
        if (this.isSelf()){
            System.out.println("velocity: " + velocity);
            ((OnlineMultiplayerWorld) world).fireProjectileAndMessage(velocity);
        }else{
            System.out.println("Fireprojectile skal kalles");
            ((OnlineMultiplayerWorld) world).fireProjectile(velocity);
        }
    }

    public static boolean equals(String currentPlayerId, String playerId) {
        return playerId.equals(currentPlayerId);
    }
    public String getPlayerID(){
        return playerId;
    }

    public void setWorld(OnlineMultiplayerWorld w) {
        super.setWorld(w);
    }

    public void setHost(boolean host) {
        isHost = host;
    }

    public boolean isHost() {
        return isHost;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean self) {
        isSelf = self;
    }

}
