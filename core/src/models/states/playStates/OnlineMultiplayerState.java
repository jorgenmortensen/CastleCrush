package models.states.playStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.castlecrush.game.CastleCrush;

import java.nio.ByteBuffer;
import java.util.List;

import models.components.MessageCodes;
import googleServices.PlayServices;
import models.entities.OnlinePlayer;
import models.states.GameStateManager;
import views.game_world.GameWorldDrawer;

import controllers.game_world.GameWorldController;
import models.GameWorld;
import models.OnlineMultiplayerWorld;
import models.states.menuStates.OnlineGameOverMenu;

/**
 * Created by Jørgen on 12.03.2018.
 */

public class OnlineMultiplayerState extends SuperPlayState implements PlayServices.NetworkListener{

    private static final String TAG = OnlineMultiplayerState.class.getSimpleName();
    List<OnlinePlayer> players;

    GameWorld world;
    GameWorldDrawer drawer;
    SpriteBatch batch;
    static float SCALE = 0.05f;
    private float screenWidth;
    private float screenHeight;
    private GameWorldController controller;

    public OnlineMultiplayerState(GameStateManager gsm, SpriteBatch batch) {
        super(gsm);
        System.out.println("OnlineMultiPlayerState started");
        CastleCrush.playServices.setNetworkListener(this);

        screenHeight = CastleCrush.HEIGHT * SCALE;
        screenWidth = CastleCrush.WIDTH * SCALE;
        this.batch=batch;
        drawer = new GameWorldDrawer(batch, screenWidth, screenHeight);

        world = new OnlineMultiplayerWorld(this, drawer, screenWidth, screenHeight, players);
        controller = new GameWorldController(world);


    }


    public void gameOver(boolean win){
        gsm.set(new OnlineGameOverMenu(gsm, win));
    }


    @Override
    protected void handleInput() {
        controller.handleInput();
    }

    @Override
    public void update(float dt) {
        world.update(dt);
    }


    @Override
    public void render(SpriteBatch sb) {
        drawer.render();
    }

    @Override
    public void dispose() {
        drawer.dispose();
    }

    public void broadcastShotData(Vector2 shotVelocity){
        ByteBuffer buffer = ByteBuffer.allocate(2*4+1); //capacity = 9, why?
        buffer.put(MessageCodes.CANNON);
        buffer.putFloat(shotVelocity.x);
        buffer.putFloat(shotVelocity.y);

        CastleCrush.playServices.sendUnreliableMessageToOthers(buffer.array());
    }


    public void broadcastRematch(){
        ByteBuffer buffer = ByteBuffer.allocate(2*4+1);
        buffer.put(MessageCodes.PLAY_AGAIN);
        CastleCrush.playServices.sendReliableMessage(buffer.array());
    }

    public void broadcastGameOver(){
        ByteBuffer buffer = ByteBuffer.allocate(2*4+1);
        buffer.put(MessageCodes.GAME_OVER);
        CastleCrush.playServices.sendReliableMessage(buffer.array());
    }

    @Override
    public void onReliableMessageReceived(String senderParticipantId, int describeContents, byte[] messageData) {
        Gdx.app.debug(TAG, "onReliableMessageReceived: " + senderParticipantId + "," + describeContents);

        ByteBuffer buffer = ByteBuffer.wrap(messageData);
        byte messageType = buffer.get();

        switch (messageType) {
            case MessageCodes.GAME_OVER:
                System.out.println("PLAY AGAIN MESSAGE RECEIVED");
                CastleCrush.playServices.sendOutRematch();
                break;
        }
    }

    @Override
    public void onUnreliableMessageReceived(String senderParticipantId, int describeContents, byte[] messageData) {
        System.out.println("onReliableMessageReceived: " + senderParticipantId + "," + describeContents);

        ByteBuffer buffer = ByteBuffer.wrap(messageData);
        byte messageType = buffer.get();

        switch (messageType) {
            case MessageCodes.CANNON:
                System.out.println("CANNON MESSAGE RECEIVED");
                //XXXX HENT UT FRA BUFFER
                //world.fireProjectile(velocity);
                break;
        }
    }

    @Override
    public void onRoomReady(List<OnlinePlayer> players) {
        System.out.println("onRoomReady");
        this.players = players;
    }
}