package models.states.menuStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import models.entities.Player;
import models.states.GameStateManager;

public class LocalGameOverMenu extends OnlineGameOverMenu {
    private Texture endScreen;
    private BitmapFont font;
    private String string;
    float xPosStart= Gdx.graphics.getWidth()*0.05f*0.2f;
    float yPosStart= Gdx.graphics.getHeight()*0.05f*0.5f;


    public LocalGameOverMenu(GameStateManager gsm, Player winningPlayer, Player loosingPLayer) {
        super(gsm, true);
        string = winningPlayer.getId()+" wins!!!!!\n"+loosingPLayer.getId()+" looses.....";
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        font = new BitmapFont();
        sb.begin();

        font.draw(sb, string, xPosStart, yPosStart);

        sb.end();
    }

    @Override
    public void dispose() {

    }
}
