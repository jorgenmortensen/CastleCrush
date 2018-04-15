package models.states.menuStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.castlecrush.game.CastleCrush;

import models.components.Button;
import models.components.GravityButton;
import models.states.GameStateManager;

import static models.states.menuStates.StartMenuScreen.startTime;

/**
 * Created by erikkjernlie on 05/04/2018.
 */

// PROBLEMS: local multiplayer button doesn't work, the rest does


public class PlayMenu extends models.states.State {

    float xMax, xCoordBg1, xCoordBg2;
    private Texture background1;
    private Texture background2;
    final int BACKGROUND_MOVE_SPEED = -30;

    private GravityButton btnSingle;
    private GravityButton btnMulti;
    private GravityButton btnLocal;
    private Texture logo;

    private Button btnSound;


    // remember to change every .PNG to .png.

    public PlayMenu(GameStateManager gsm) {
        super(gsm);
        logo = new Texture("logo.png");
        //background1 = new Texture("background.png");
        makeMovingBackground();
        makeButtons();
    }

    private void makeMovingBackground(){
        background1 = new Texture(Gdx.files.internal("loop_background_castles.png"));
        background2 = new Texture(Gdx.files.internal("loop_background_castles.png")); // identical
        xMax = CastleCrush.WIDTH;
        xCoordBg1 = xMax;
        xCoordBg2 = 0;
    }


    private void makeButtons() {
        //Single-menu button
        btnSingle = new GravityButton(CastleCrush.WIDTH / 3,
                5*CastleCrush.HEIGHT / 10,
                CastleCrush.WIDTH / 3,
                CastleCrush.HEIGHT / 10,
                new Sprite(new Texture("single.png")), CastleCrush.HEIGHT);

        btnLocal = new GravityButton(CastleCrush.WIDTH / 3,
                3*CastleCrush.HEIGHT / 10,
                CastleCrush.WIDTH / 3,
                CastleCrush.HEIGHT / 10,
                new Sprite(new Texture("multi_local.png")), CastleCrush.HEIGHT);

        btnMulti = new GravityButton(CastleCrush.WIDTH / 3,
                1*CastleCrush.HEIGHT / 10,
                CastleCrush.WIDTH / 3,
                CastleCrush.HEIGHT / 10,
                new Sprite(new Texture("multi_online.png")), CastleCrush.HEIGHT);
        btnSound = new Button(CastleCrush.WIDTH / 15, CastleCrush.WIDTH / 15,
                CastleCrush.WIDTH / 15,
                CastleCrush.HEIGHT / 15, new Sprite(new Texture("sound_on.png")));
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched() && isOnSingleBtn()) {
            gsm.set(new SingleplayerMenu(gsm));
            System.out.println("Single pressed");
            dispose();
        }
        else if (Gdx.input.justTouched() && isOnMultiBtn()) {
            gsm.set(new models.states.menuStates.MultiplayerMenu(gsm));
            System.out.println("Multi pressed");
            dispose();
        }
        else if (Gdx.input.justTouched() && isOnLocalMultiBtn()) {
            gsm.set(new models.states.menuStates.StartMenuScreen(gsm));
            System.out.println("Local pressed");
            dispose();
        } else if (Gdx.input.justTouched() && isOnSoundBtn()) {
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

    private boolean isOnSoundBtn(){
        if (((Gdx.input.getX() > btnSound.getXpos() && (Gdx.input.getX() < (btnSound.getXpos() + btnSound.getBtnWidth())))
                && ((CastleCrush.HEIGHT - Gdx.input.getY() - 1) > btnSound.getYpos()) && ((CastleCrush.HEIGHT - Gdx.input.getY() - 1) < (btnSound.getYpos() + btnSound.getBtnHeight())))){
            return true;
        }
        return false;
    }

    private boolean isOnSingleBtn() {
        if (((Gdx.input.getX() > btnSingle.getXpos() && (Gdx.input.getX() < (btnSingle.getXpos() + btnSingle.getBtnWidth())))
                && ((CastleCrush.HEIGHT - Gdx.input.getY() - 1) > btnSingle.getYpos()) && ((CastleCrush.HEIGHT - Gdx.input.getY() - 1) < (btnSingle.getYpos() + btnSingle.getBtnHeight())))){
            return true;
        }
        return false;
    }

    private boolean isOnMultiBtn() {
        if (((Gdx.input.getX() > btnMulti.getXpos()) && (Gdx.input.getX() < (btnMulti.getXpos() + btnMulti.getBtnWidth())))
                && (((CastleCrush.HEIGHT - Gdx.input.getY() - 1) > btnMulti.getYpos()) && ((CastleCrush.HEIGHT - Gdx.input.getY() - 1) < (btnMulti.getYpos() + btnMulti.getBtnHeight())))){
            return true;
        }
        return false;
    }

    private boolean isOnLocalMultiBtn() {
        if (((Gdx.input.getX() > btnLocal.getXpos()) && (Gdx.input.getX() < (btnLocal.getXpos() + btnLocal.getBtnWidth())))
                && (((CastleCrush.HEIGHT - Gdx.input.getY() - 1) > btnLocal.getYpos()) && ((CastleCrush.HEIGHT - Gdx.input.getY() - 1) < (btnLocal.getYpos() + btnLocal.getBtnHeight())))){
            return true;
        }
        return false;
    }

    @Override
    public void update(float dt) {
        handleInput();
        btnSingle.update(dt);
        btnMulti.update(dt);
        btnLocal.update(dt);

        // makes the background move to the left
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
        sb.draw(btnSingle.getBtn(), btnSingle.getXpos(),
                btnSingle.getYpos(),
                btnSingle.getBtnWidth(),
                btnSingle.getBtnHeight());



        sb.draw(btnMulti.getBtn(), btnMulti.getXpos(),
                btnMulti.getYpos(),
                btnMulti.getBtnWidth(),
                btnMulti.getBtnHeight());

        sb.draw(btnLocal.getBtn(), btnLocal.getXpos(),
                btnLocal.getYpos(),
                btnLocal.getBtnWidth(),
                btnLocal.getBtnHeight());




        sb.draw(logo, 0, CastleCrush.HEIGHT * 7 / 10, CastleCrush.WIDTH, 3*CastleCrush.HEIGHT/10);

        sb.draw(btnSound.getBtn(), btnSound.getXpos(), btnSound.getYpos(),btnSound.getBtnWidth(), btnSound.getBtnHeight());
        sb.end();
    }


    //dispose textures / buttons
    @Override
    public void dispose() {
        logo.dispose();
    }


}
