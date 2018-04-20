package models.states.menuStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.castlecrush.game.CastleCrush;

import models.components.Button;
import models.states.GameStateManager;
import models.states.State;
import models.states.playStates.SinglePlayerState;

/**
 * Created by Jørgen on 17.04.2018.
 */

public class GameOverMenu extends State {

    private Texture background;
    private Texture winner;
    private Texture loser;
    private Button rematchBtn;
    private boolean winnerScreen;
    private boolean isHost;

    public GameOverMenu(GameStateManager gsm, boolean winnerScreen, boolean isHost) {
        super(gsm);
        background = new Texture(Gdx.files.internal("loop_background_castles.png"));
        this.winnerScreen = winnerScreen;
        this.winner = new Texture("winner.png");
        this.loser = new Texture("loser.png");
        this.isHost = isHost;
        //Initialize buttons
        initButtons();
    }

    private void initButtons() {
        rematchBtn = new Button(CastleCrush.WIDTH / 5, 0,
                CastleCrush.WIDTH / 10, CastleCrush.WIDTH / 10,
                new Sprite(new Texture("rematchBtn.png")));
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            if (isOnButton(btnHome)) {
                goToMainMenu();
            }
            else if (isOnButton(rematchBtn) && isHost) {
                gsm.set(new SinglePlayerState(gsm));
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();

        sb.draw(background, 0, 0, CastleCrush.WIDTH, CastleCrush.HEIGHT);

        if (winnerScreen) {
            sb.draw(winner, CastleCrush.WIDTH * 0.1f, CastleCrush.HEIGHT * 0.1f,
                    CastleCrush.WIDTH * 0.8f, CastleCrush.HEIGHT * 0.8f);
        }
        else if (!winnerScreen) {
            sb.draw(loser, CastleCrush.WIDTH * 0.1f, CastleCrush.HEIGHT * 0.1f,
                    CastleCrush.WIDTH * 0.8f, CastleCrush.HEIGHT * 0.8f);
        }

        sb.draw(btnHome.getBtn(), btnHome.getXpos(), btnHome.getYpos(),
            btnHome.getBtnWidth(), btnHome.getBtnHeight());

        if (isHost) {
            sb.draw(rematchBtn.getBtn(), rematchBtn.getXpos(), rematchBtn.getYpos(),
                    rematchBtn.getBtnWidth(), rematchBtn.getBtnHeight());
        }

        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        winner.dispose();
    }
}
