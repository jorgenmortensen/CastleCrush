package states.playStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.castlecrush.game.CastleCrush;

import java.util.ArrayList;
import java.util.List;

import components.Button;
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

        clickBtn = new Button(CastleCrush.WIDTH / 4,
                CastleCrush.HEIGHT / 2 - CastleCrush.HEIGHT / 10,
                CastleCrush.WIDTH / 8,
                CastleCrush.HEIGHT * 2 / 10,
                new Sprite(new Texture("test_play.png")));

        font = new BitmapFont();
    }


    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched() && isOnClickBtn()) {
            count+=1;
            dispose();
        }

    }


    private boolean isOnClickBtn() {
        if (((CastleCrush.HEIGHT - Gdx.input.getY()) > clickBtn.getYpos()) &&
                ((CastleCrush.HEIGHT - Gdx.input.getY()) < (clickBtn.getYpos() + clickBtn.getBtnHeight())) &&
                (Gdx.input.getX() > clickBtn.getXpos()) && (Gdx.input.getX() < (clickBtn.getXpos() + clickBtn.getBtnWidth()))) {
            return true;
        }
        return false;
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();

        sb.draw(new Texture("logo.png"), 0, 0, CastleCrush.WIDTH, CastleCrush.HEIGHT);
        sb.draw(clickBtn.getBtn(), clickBtn.getXpos(),
                clickBtn.getYpos(),
                clickBtn.getBtnWidth(), clickBtn.getBtnHeight());

        System.out.println("AAAAAAAAAAAA");
        font.setColor((float) 0.5,(float)0.5,(float)0.5,1);
        font.draw(sb, "You: " + count + "       Opponent: " + opponent, CastleCrush.WIDTH/3, CastleCrush.HEIGHT*3/4);
        font.getData().setScale(5);
        sb.end();
    }

    @Override
    public void dispose() {
    }

    @Override
    public void onReliableMessageReceived(String senderParticipantId, int describeContents, byte[] messageData) {
        Gdx.app.debug(TAG, "onReliableMessageReceived: " + senderParticipantId + "," + describeContents);

    }

    @Override
    public void onUnreliableMessageReceived(String senderParticipantId, int describeContents, byte[] messageData) {

    }

    @Override
    public void onRoomReady(List<PlayerData> players) {
        System.out.println("onRoomReady");
        this.players = players;
        System.out.println(players);
    }
}
