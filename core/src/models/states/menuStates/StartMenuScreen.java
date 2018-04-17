package models.states.menuStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.castlecrush.game.CastleCrush;

import models.components.Button;
import models.states.GameStateManager;
import models.states.TutorialState;

import static com.castlecrush.game.CastleCrush.xCoordBg1;
import static com.castlecrush.game.CastleCrush.xCoordBg2;

/**
 * Created by Jørgen on 12.03.2018.
 */

public class StartMenuScreen extends models.states.State {

    Button btnPlay;
    Button btnHelp;
    Button btnSound;

    public static Texture logo;

    float xMax = CastleCrush.WIDTH;

    private Texture background1;
    private Texture background2;

    public static long startTime;
    public static boolean little_crushed = true;
    public static boolean crushed = true;
    public static boolean without_castle = true;
    public static boolean with_u = true;
    public static boolean changed_logo;

    public StartMenuScreen(GameStateManager gsm) {
        super(gsm);
        // the normal logo will appear when the user gets back to this menu, which is the intention
        logo = new Texture("logo.png");
        if (changed_logo) {
            little_crushed = false;
            crushed = false;
            without_castle = false;
            with_u = false;
        }

        this.startTime = TimeUtils.millis();
        makeButtons();
        makeMovingBackground();
    }

    private void makeMovingBackground(){
        background1 = new Texture(Gdx.files.internal("loop_background_castles.png"));
        background2 = new Texture(Gdx.files.internal("loop_background_castles.png")); // identical

    }

    private void makeButtons() {
        //Play button
        btnPlay = new Button(CastleCrush.WIDTH / 4,
                CastleCrush.HEIGHT / 2 - CastleCrush.HEIGHT / 10,
                CastleCrush.WIDTH / 8,
                CastleCrush.HEIGHT * 2 / 10,
                new Sprite(new Texture("test_play.png")));

        //Help button
        btnHelp = new Button(CastleCrush.WIDTH * 3 / 4 - CastleCrush.WIDTH / 8,
                CastleCrush.HEIGHT / 2 - CastleCrush.HEIGHT / 10,
                CastleCrush.WIDTH / 8,
                CastleCrush.HEIGHT * 2 / 10,
                new Sprite(new Texture("test_help.png")));

        btnSound = new Button(0, 0, CastleCrush.WIDTH / 15, CastleCrush.WIDTH / 15,
                CastleCrush.soundOn ? new Sprite(new Texture("sound_on.png")) : new Sprite(new Texture("sound_off.png")));

    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched() && isOnButton(btnPlay)) {
            gsm.set(new PlayMenu(gsm));
            dispose();
        }
        else if (Gdx.input.justTouched() && isOnButton(btnHelp)) {
            gsm.set(new TutorialState(gsm));
            dispose();
        }
        else if (Gdx.input.justTouched() && isOnButton(btnSound)) {
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

    @Override
    public void update(float dt) {
        handleInput();
        //makes the background move to the left
        xCoordBg1 += CastleCrush.BACKGROUND_MOVE_SPEED * Gdx.graphics.getDeltaTime();
        xCoordBg2 = xCoordBg1 - xMax;  // We move the background, not the camera
        if (CastleCrush.xCoordBg1 <= 0) {
            CastleCrush.xCoordBg1 = xMax;
            xCoordBg2 = 0;
        }
        long time = TimeUtils.timeSinceMillis(startTime);
        if (time > 3500 && little_crushed) {
            logo = new Texture("logo_little_crushed.png");
            little_crushed = false;
        } else if (time > 7000 && crushed) {
            logo = new Texture("logo_crushed.png");
            crushed = false;
        } else if (time > 10000  && without_castle) {
            logo = new Texture("logo_without_castle.png");
            without_castle = false;
        } else if (time > 13000 && with_u) {
            logo = new Texture("logo_with_u.png");;
            with_u = false;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();

        sb.draw(background1, CastleCrush.xCoordBg1, 0, CastleCrush.WIDTH, CastleCrush.HEIGHT);
        sb.draw(background2, CastleCrush.xCoordBg2, 0, CastleCrush.WIDTH, CastleCrush.HEIGHT);

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
        background1.dispose();
        background2.dispose();
    }
}
