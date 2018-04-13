package googleplayservice;

import java.util.List;

/**
 * Created by Bruker on 07.04.2018.
 */

public interface PlayServices {

    void signIn();
    void signOut();
    boolean isSignedIn();
    void startQuickGame();
    void toast();
    String getDisplayName();
    void sendUnreliableMessageToOthers(byte[] messageData);

    void startSelectOpponents();

    void setGameListener(GameListener gameListener);
    void setNetworkListener(NetworkListener networkListener);

    void quitGame();

    interface GameListener {
        void onMultiplayerGameStarting();
    }

    interface NetworkListener {
        void onReliableMessageReceived(String senderParticipantId, int describeContents, byte[] messageData);
        void onUnreliableMessageReceived(String senderParticipantId, int describeContents, byte[] messageData);
        void onRoomReady(List<PlayerData> players);
    }


}
