package states.menuStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.castlecrush.game.CastleCrush;

import components.Button;
import states.GameStateManager;
import states.TutorialState;

/**
 * Created by Jørgen on 12.03.2018.
 */

public class StartMenuScreen extends states.State {

    Button btnPlay;
    Button btnHelp;
    Button btnSound;
    Texture logo;

    float xMax, xCoordBg1, xCoordBg2;
    private Texture background1;
    private Texture background2;
    final int BACKGROUND_MOVE_SPEED = -30;

    public StartMenuScreen(GameStateManager gsm) {
        super(gsm);
        logo = new Texture("logo.png");
        makeButtons();
        makeMovingBackground();
    }

    private void makeMovingBackground(){
        background1 = new Texture(Gdx.files.internal("loop_background_castles.png"));
        background2 = new Texture(Gdx.files.internal("loop_background_castles.png")); // identical
        xMax = CastleCrush.WIDTH;
        xCoordBg1 = xMax;
        xCoordBg2 = 0;
    }

    private void makeButtons() {
        //Play button
        btnPlay = new Button(CastleCrush.WIDTH / 4,
                CastleCrush.HEIGHT / 2 - CastleCrush.HEIGHT / 10,
                CastleCrush.WIDTH / 8,
                CastleCrush.HEIGHT * 2 / 10,
                new Sprite(new Texture("playBtn.png")));

        //Help button
        btnHelp = new Button(CastleCrush.WIDTH * 3 / 4 - CastleCrush.WIDTH / 8,
                CastleCrush.HEIGHT / 2 - CastleCrush.HEIGHT / 10,
                CastleCrush.WIDTH / 8,
                CastleCrush.HEIGHT * 2 / 10,
                new Sprite(new Texture("helpBtn.png")));

        //Settings button
        btnSound = new Button(CastleCrush.WIDTH / 15, CastleCrush.WIDTH / 15,
                CastleCrush.WIDTH / 15,
                CastleCrush.HEIGHT / 15,
                new Sprite(new Texture("sound_on.png")));
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched() && isOnPlayBtn()) {
            gsm.set(new PlayMenu(gsm));
            dispose();
        }
        else if (Gdx.input.justTouched() && isOnHelpBtn()) {
            gsm.set(new TutorialState(gsm));
            dispose();
        }
        else if (Gdx.input.justTouched() && isOnSoundBtn()) {
            //Turn off sound if already on and vice versa
            if (CastleCrush.soundOn) {
                CastleCrush.music.setVolume(0);
                CastleCrush.soundOn = false;
                btnSound.setBtn(new Sprite(new Texture("sound_off.png")));
            } else {
                CastleCrush.music.setVolume(0.5f);
                CastleCrush.soundOn = true;
                btnSound.setBtn(new Sprite(new Texture("sound_on.png")));
            }
        }
    }

    private boolean isOnPlayBtn() {
        if (((CastleCrush.HEIGHT - Gdx.input.getY()) > btnPlay.getYpos()) &&
                ((CastleCrush.HEIGHT - Gdx.input.getY()) < (btnPlay.getYpos() + btnPlay.getBtnHeight())) &&
                (Gdx.input.getX() > btnPlay.getXpos()) && (Gdx.input.getX() < (btnPlay.getXpos() + btnPlay.getBtnWidth()))) {
            return true;

        }
        return false;
    }

    private boolean isOnHelpBtn() {
        if (((CastleCrush.HEIGHT - Gdx.input.getY()) > btnHelp.getYpos()) &&
                ((CastleCrush.HEIGHT - Gdx.input.getY()) < (btnHelp.getYpos() + btnHelp.getBtnHeight())) &&
                (Gdx.input.getX() > btnHelp.getXpos()) && (Gdx.input.getX() < (btnHelp.getXpos() + btnHelp.getBtnWidth()))) {
            return true;
        }
        return false;
    }

    private boolean isOnSoundBtn() {
        if (((CastleCrush.HEIGHT - Gdx.input.getY()) > btnSound.getYpos()) &&
                ((CastleCrush.HEIGHT - Gdx.input.getY()) < (btnSound.getYpos() + btnSound.getBtnHeight())) &&
                (Gdx.input.getX() > btnSound.getXpos()) && (Gdx.input.getX() < (btnSound.getXpos() + btnSound.getBtnWidth()))) {
            return true;
        }
        return false;
    }

    @Override
    public void update(float dt) {
        handleInput();
        //makes the background move to the left
        xCoordBg1 += BACKGROUND_MOVE_SPEED * Gdx.graphics.getDeltaTime();
        xCoordBg2 = xCoordBg1 - xMax;  // We move the background, not the camera
        if (xCoordBg1 <= 0) {
            xCoordBg1 = xMax;
            xCoordBg2 = 0;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();

        sb.draw(background1, xCoordBg1, 0, CastleCrush.WIDTH, CastleCrush.HEIGHT);
        sb.draw(background2, xCoordBg2, 0, CastleCrush.WIDTH, CastleCrush.HEIGHT);

        sb.draw(btnPlay.getBtn(), btnPlay.getXpos(),
                btnPlay.getYpos(),
                btnPlay.getBtnWidth(), btnPlay.getBtnHeight());

        sb.draw(btnHelp.getBtn(), btnHelp.getXpos(),
                btnHelp.getYpos(),
                btnHelp.getBtnWidth(), btnHelp.getBtnHeight());

        sb.draw(btnSound.getBtn(), btnSound.getXpos(),
                btnSound.getYpos(),
                btnSound.getBtnWidth(), btnSound.getBtnHeight());

        sb.draw(logo, 0, CastleCrush.HEIGHT * 7 / 10, CastleCrush.WIDTH, CastleCrush.HEIGHT * 3 / 10);

        sb.end();
    }

    @Override
    public void dispose() {
    }
}
