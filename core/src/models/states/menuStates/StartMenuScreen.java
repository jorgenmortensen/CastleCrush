package models.states.menuStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.castlecrush.game.CastleCrush;

import models.components.Button;
import models.states.State;

/**
 * Created by Jørgen on 12.03.2018.
 */

public class StartMenuScreen extends State {

    Button btnPlay;
    Button btnHelp;
    Button btnSound;
    Texture logo;

    float xMax, xCoordBg1, xCoordBg2;
    private Texture background1;
    private Texture background2;
    final int BACKGROUND_MOVE_SPEED = -30;

    public static long startTime;

    public StartMenuScreen(models.states.GameStateManager gsm) {
        super(gsm);
        logo = new Texture("logo.png");
        this.startTime = TimeUtils.millis();
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
                CastleCrush.WIDTH / 15,
                new Sprite(new Texture("sound_on.png")));
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched() && isOnPlayBtn()) {
            gsm.set(new PlayMenu(gsm));
            dispose();
        }
        else if (Gdx.input.justTouched() && isOnHelpBtn()) {
            gsm.set(new models.states.TutorialState(gsm));
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
        long time = TimeUtils.timeSinceMillis(startTime);
        if (time > 3500 && time < 7000) {
            logo = new Texture("logo_little_crushed.png");
        } else if (time > 7000 && time < 10000) {
            logo = new Texture("logo_crushed.png");
        } else if (time > 10000 && time < 13000) {
            logo = new Texture("logo_without_castle.png");
        } else if (time > 13000) {
            logo = new Texture("logo_with_u.png");
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

    public long getStartTime(){

        return this.startTime;
    }

    @Override
    public void dispose() {
    }
}
