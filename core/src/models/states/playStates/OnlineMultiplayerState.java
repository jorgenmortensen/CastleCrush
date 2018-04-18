package models.states.playStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.castlecrush.game.CastleCrush;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import models.components.Button;
import models.components.MessageCodes;
import googleplayservice.PlayServices;
import googleplayservice.PlayerData;
import models.MockGameWorld;
import models.states.State;
import models.states.GameStateManager;
import views.game_world.GameWorldDrawer;

/**
 * Created by Jørgen on 12.03.2018.
 */

public class OnlineMultiplayerState extends State implements PlayServices.NetworkListener{

    private static final String TAG = OnlineMultiplayerState.class.getSimpleName();
    Button clickBtn;
    int count, opponent;
    BitmapFont font;
    List players;

    MockGameWorld world;
    GameWorldDrawer drawer;
    SpriteBatch batch;


    public OnlineMultiplayerState(GameStateManager gsm, SpriteBatch batch) {
        super(gsm);
        System.out.println("OnlineMultiPlayerState started");

        this.batch=batch;
        world = new MockGameWorld();
        drawer = new GameWorldDrawer(batch, world);
        CastleCrush.playServices.setNetworkListener(this);

    }


    @Override
    protected void handleInput() {}

    @Override
    public void update(float dt) {
        handleInput();
    }


    @Override
    public void render(SpriteBatch sb) {
        drawer.render();
    }

    @Override
    public void dispose() {}

    public void broadcastShotData(){
        ByteBuffer buffer = ByteBuffer.allocate(2*4+1); //capacity = 9, why?
        buffer.put(MessageCodes.CANNON);
        //buffer.putFloat(shotvelocity.x);
        //buffer.putFloat(shotvelocity.y);
        CastleCrush.playServices.sendUnreliableMessageToOthers(buffer.array());

    }

    @Override
    public void onReliableMessageReceived(String senderParticipantId, int describeContents, byte[] messageData) {
        Gdx.app.debug(TAG, "onReliableMessageReceived: " + senderParticipantId + "," + describeContents);

        ByteBuffer buffer = ByteBuffer.wrap(messageData);
        byte messageType = buffer.get();

        switch (messageType) {
            case MessageCodes.GAME_OVER:
                System.out.println("GAME OVER MESSAGE RECEIVED");
                //pop-up window????
                //gsm.set(new GameOverState(gsm));
                //evnt. draw GameOver text, og to knapper: end og rematch (aka Flappy)

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

                break;

        }
    }

    @Override
    public void onRoomReady(List<PlayerData> players) {
        System.out.println("onRoomReady");
        this.players = players;
        System.out.println(players);
    }
}
