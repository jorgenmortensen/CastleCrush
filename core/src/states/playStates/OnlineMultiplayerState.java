package states.playStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.castlecrush.game.CastleCrush;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import components.Button;
import components.MessageCodes;
import components.TextView;
import googleplayservice.PlayServices;
import googleplayservice.PlayerData;
import states.GameStateManager;
import states.State;
import states.menuStates.PlayMenu;

/**
 * Created by Jørgen on 12.03.2018.
 */

public class OnlineMultiplayerState extends State implements PlayServices.NetworkListener{

    private static final String TAG = OnlineMultiplayerState.class.getSimpleName();
    Button clickBtn;
    int count, opponent;
    BitmapFont font;
    List players;

    public OnlineMultiplayerState(GameStateManager gsm) {
        super(gsm);

        CastleCrush.playServices.setNetworkListener(this);

        font = new BitmapFont();
    }


    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {

            dispose();
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();

        sb.draw(new Texture("logo.png"), 0, 0, CastleCrush.WIDTH, CastleCrush.HEIGHT);

        font.setColor((float) 0.5,(float)0.5,(float)0.5,1);
        font.draw(sb, "You: " + count + "       Opponent: " + opponent, CastleCrush.WIDTH/3, CastleCrush.HEIGHT*3/4);
        font.getData().setScale(5);
        sb.end();
    }

    @Override
    public void dispose() {
    }

    public void broadcastShotData(){
        ByteBuffer buffer = ByteBuffer.allocate(2*4+1); //capacity = 9, why?
        buffer.put(MessageCodes.CANNON);
        //buffer.putFloat();
        //buffer.putFloat();
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
