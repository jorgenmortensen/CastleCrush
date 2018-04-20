package models.entities;

import models.GameWorld;


public class OnlinePlayer extends Player {
    public String participantId = "";
    public String playerId = "";
    public String displayName = "";
    private boolean isSelf = false;
    private boolean isHost = false;


    GameWorld world;

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
    public int hashCode() {
        return participantId.hashCode();
    }

    public static boolean equals(String currentPlayerId, String playerId) {
        return playerId.equals(currentPlayerId);
    }
    public String getPlayerID(){
        return playerId;
    }

    public void setWorld(GameWorld w) {
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
