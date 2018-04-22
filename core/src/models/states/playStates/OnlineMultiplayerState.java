package models.states.playStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
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
    private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    public OnlineMultiplayerState(GameStateManager gsm, SpriteBatch batch) {
        super(gsm);
        System.out.println("OnlineMultiPlayerState started");
        CastleCrush.playServices.setNetworkListener(this);

        screenHeight = CastleCrush.HEIGHT * SCALE;
        screenWidth = CastleCrush.WIDTH * SCALE;
        this.batch=batch;

        drawer = new GameWorldDrawer(batch, screenWidth, screenHeight);
        //world = new OnlineMultiplayerWorld(this, drawer, screenWidth, screenHeight, players);
        //world = new OnlineMultiplayerWorld();
        //controller = new GameWorldController(world);

    }


    public void gameOver(boolean win){
        gsm.set(new OnlineGameOverMenu(gsm, win));
    }


    @Override
    protected void handleInput() {}

    @Override
    public void update(float dt) {
        world.update(dt);
        controller.handleInput();
    }


    @Override
    public void render(SpriteBatch sb) {
        System.out.println("render method");
        drawer.render();
        debugRenderer.render(world.getPhysicsWorld(), cam.combined);
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
        System.out.println("velocity x sendt: " + shotVelocity.x);
        System.out.println("velocity y sendt: " + shotVelocity.y);


        CastleCrush.playServices.sendUnreliableMessageToOthers(buffer.array());
        System.out.println("111");
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
        System.out.println("onUNReliableMessageReceived: " + senderParticipantId + "," + describeContents);

        ByteBuffer buffer = ByteBuffer.wrap(messageData);
        byte messageType = buffer.get();

        switch (messageType) {
            case MessageCodes.CANNON:
                System.out.println("CANNON MESSAGE RECEIVED");
                float x = buffer.getFloat();
                //buffer.flip();
                System.out.println("velocity x received: " + x);
                float y = buffer.getFloat();
                System.out.println("velocity y received: " + y);
                Vector2 velocity = new Vector2(x,y);
                world.fireProjectile(velocity);
                break;
        }
    }

    @Override
    public void onRoomReady(List<OnlinePlayer> players) {
        System.out.println("onRoomReady");
        this.players = players;
        world = new OnlineMultiplayerWorld(this, drawer, screenWidth, screenHeight, players);
        for (OnlinePlayer p: players){
            p.setWorld(world);
        }
        controller = new GameWorldController(world, this);

    }
}