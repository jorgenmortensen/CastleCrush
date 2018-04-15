package states.menuStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.castlecrush.game.CastleCrush;

import components.Button;
import components.GravityButton;
import states.GameStateManager;
import states.State;

import static states.menuStates.StartMenuScreen.changed_logo;
import static states.menuStates.StartMenuScreen.crushed;
import static states.menuStates.StartMenuScreen.little_crushed;
import static states.menuStates.StartMenuScreen.logo;
import static states.menuStates.StartMenuScreen.startTime;
import static states.menuStates.StartMenuScreen.with_u;
import static states.menuStates.StartMenuScreen.without_castle;

/**
 * Created by erikkjernlie on 05/04/2018.
 */

// PROBLEMS: local multiplayer button doesn't work, the rest does


public class PlayMenu extends State {

    float xMax;
    private Texture background1;
    private Texture background2;

    private GravityButton btnSingle;
    private GravityButton btnMulti;
    private GravityButton btnLocal;
    private Button btnSound;


    // remember to change every .PNG to .png.

    public PlayMenu(GameStateManager gsm) {
        super(gsm);
        makeMovingBackground();
        xMax = CastleCrush.WIDTH;
        makeButtons();
    }

    private void makeMovingBackground(){
        background1 = new Texture(Gdx.files.internal("loop_background_castles.png"));
        background2 = new Texture(Gdx.files.internal("loop_background_castles.png")); // identical
    }


    private void makeButtons() {
        //Single-menu button
        btnSingle = new GravityButton(CastleCrush.WIDTH / 3,
                5*CastleCrush.HEIGHT / 10,
                CastleCrush.WIDTH / 3,
                CastleCrush.HEIGHT / 10,
                new Sprite(new Texture("single.png")), CastleCrush.HEIGHT);

        //Local multiplayer button
        btnLocal = new GravityButton(CastleCrush.WIDTH / 3,
                3*CastleCrush.HEIGHT / 10,
                CastleCrush.WIDTH / 3,
                CastleCrush.HEIGHT / 10,
                new Sprite(new Texture("multi_local.png")), CastleCrush.HEIGHT);

        btnMulti = new GravityButton(CastleCrush.WIDTH / 3,
                1*CastleCrush.HEIGHT / 10,
                CastleCrush.WIDTH / 3,
                CastleCrush.HEIGHT / 10,
                new Sprite(new Texture("invite_friends.png")), CastleCrush.HEIGHT);

        btnSound = new Button(0, 0, CastleCrush.WIDTH / 15, CastleCrush.WIDTH / 15,
                CastleCrush.soundOn ? new Sprite(new Texture("sound_on.png")) : new Sprite(new Texture("sound_off.png")));

    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched() && isOnButton(btnSingle)) {
            gsm.set(new SingleplayerMenu(gsm));
            System.out.println("Single pressed");
            dispose();
        }
        else if (Gdx.input.justTouched() && isOnButton(btnMulti)) {
            gsm.set(new MultiplayerMenu(gsm));
            System.out.println("Multi pressed");
            dispose();
        }
        else if (Gdx.input.justTouched() && isOnButton(btnLocal)) {
            gsm.set(new StartMenuScreen(gsm));
            System.out.println("Local pressed");
            dispose();
        } else if (Gdx.input.justTouched() && isOnButton(btnSound)) {
            //Turn off sound if already on and vice versa
            if (CastleCrush.soundOn) {
                CastleCrush.music.setVolume(0);
                CastleCrush.soundOn = false;
                btnSound.setBtn(new Sprite(new Texture("sound_off_2.png")));
            } else {
                CastleCrush.music.setVolume(0.5f);
                CastleCrush.soundOn = true;
                btnSound.setBtn(new Sprite(new Texture("sound_on_2.png")));
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        btnSingle.update(dt);
        btnMulti.update(dt);
        btnLocal.update(dt);

        // makes the background move to the left
        CastleCrush.xCoordBg1 += CastleCrush.BACKGROUND_MOVE_SPEED * Gdx.graphics.getDeltaTime();
        CastleCrush.xCoordBg2 = CastleCrush.xCoordBg1 - xMax;  // We move the background, not the camera
        if (CastleCrush.xCoordBg1 <= 0) {
            CastleCrush.xCoordBg1 = xMax;
            CastleCrush.xCoordBg2 = 0;
        }
        logoCrush();
    }


    //Make the castle in the logo crush and tranforming it to a U
    public void logoCrush() {
        long time = TimeUtils.timeSinceMillis(startTime);
        if (time > 3500 && little_crushed) {
            logo = new Texture("logo_little_crushed.png");;;
            little_crushed = false;
            changed_logo = true;
        } else if (time > 7000 && crushed) {
            logo = new Texture("logo_crushed.png");;
            crushed = false;
            changed_logo = true;
        } else if (time > 10000  && without_castle) {
            logo = new Texture("logo_without_castle.png");;
            without_castle = false;
            changed_logo = true;
        } else if (time > 13000 && with_u) {
            logo = new Texture("logo_with_u.png");;
            with_u = false;
            changed_logo = true;
        }
    }

    @Override
    public void render(SpriteBatch sb) {

        sb.begin();
        sb.draw(background1, CastleCrush.xCoordBg1, 0, CastleCrush.WIDTH, CastleCrush.HEIGHT);
        sb.draw(background2, CastleCrush.xCoordBg2, 0, CastleCrush.WIDTH, CastleCrush.HEIGHT);
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

    }


}
