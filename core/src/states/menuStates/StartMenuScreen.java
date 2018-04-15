package states.menuStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.castlecrush.game.CastleCrush;

import components.Button;
import models.states.GameStateManager;
import models.states.State;
import states.TutorialState;

/**
 * Created by Jørgen on 12.03.2018.
 */

public class StartMenuScreen extends State {

    Button btnPlay;
    Button btnHelp;
    Button btnSound;
    Button btnInvitationInbox;
    public static Texture logo;
    Button btnLogOutIn;

    float xMax, xCoordBg1, xCoordBg2;
    private Texture background1,background2,t;
    final int BACKGROUND_MOVE_SPEED = -30;

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



        if (CastleCrush.playServices.isSignedIn()) {
            System.out.println();
            //crush.playServices.toast();

        }else{
            CastleCrush.playServices.signIn();
        }
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
                new Sprite(new Texture("test_play.png")));

        //Help button
        btnHelp = new Button(CastleCrush.WIDTH * 3 / 4 - CastleCrush.WIDTH / 8,
                CastleCrush.HEIGHT / 2 - CastleCrush.HEIGHT / 10,
                CastleCrush.WIDTH / 8,
                CastleCrush.HEIGHT * 2 / 10,
                new Sprite(new Texture("test_help.png")));

        btnSound = new Button(0, 0, CastleCrush.WIDTH / 15, CastleCrush.WIDTH / 15,
                CastleCrush.soundOn ? new Sprite(new Texture("sound_on.png")) : new Sprite(new Texture("sound_off.png")));

        if (CastleCrush.playServices.isSignedIn()) {
            t = new Texture("googlesignout.png");
        }
        if (CastleCrush.playServices.isSignedIn()) {
            t = new Texture("signout.png");
        }else{
            t = new Texture("sign_in.png");
        }
        btnLogOutIn = new Button(CastleCrush.WIDTH / 3,
                1*CastleCrush.HEIGHT / 10,
                CastleCrush.WIDTH / 3,
                CastleCrush.HEIGHT / 10,
                new Sprite(t));

        btnInvitationInbox = new Button(CastleCrush.WIDTH/7 ,
                CastleCrush.HEIGHT *2/9,
                CastleCrush.WIDTH / 8,
                CastleCrush.HEIGHT * 2 / 10,
                new Sprite(new Texture("test_play.png")));

    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched() && isOnPlayBtn()) {
            gsm.set(new PlayMenu(gsm));
            dispose();
        } else if (Gdx.input.justTouched() && isOnHelpBtn()) {
            gsm.set(new TutorialState(gsm));
            dispose();
        } else if (Gdx.input.justTouched() && isOnInvitationBtn()) {
            CastleCrush.playServices.showInvitationInbox();
            dispose();
        } else if (Gdx.input.justTouched() && isOnLogOffInBtn()) {

            if (CastleCrush.playServices.isSignedIn()) {
                CastleCrush.playServices.signOut();
                btnLogOutIn.setBtn(new Sprite(new Texture("googlesignin.png")));
                if (CastleCrush.playServices.isSignedIn()) {
                    CastleCrush.playServices.signOut();
                    btnLogOutIn.setBtn(new Sprite(new Texture("sign_in.png")));
                } else {
                    CastleCrush.playServices.signIn();
                    btnLogOutIn.setBtn(new Sprite(new Texture("googlesignout.png")));
                    CastleCrush.playServices.signIn();
                    btnLogOutIn.setBtn(new Sprite(new Texture("signout.png")));
                }
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
    private boolean isOnLogOffInBtn() {
        if (((CastleCrush.HEIGHT - Gdx.input.getY()) > btnLogOutIn.getYpos()) &&
                ((CastleCrush.HEIGHT - Gdx.input.getY()) < (btnLogOutIn.getYpos() + btnLogOutIn.getBtnHeight())) &&
                (Gdx.input.getX() > btnLogOutIn.getXpos()) && (Gdx.input.getX() < (btnLogOutIn.getXpos() + btnLogOutIn.getBtnWidth()))) {
            return true;
        }
        return false;
    }

    private boolean isOnInvitationBtn() {
        if (((CastleCrush.HEIGHT - Gdx.input.getY()) > btnInvitationInbox.getYpos()) &&
                ((CastleCrush.HEIGHT - Gdx.input.getY()) < (btnInvitationInbox.getYpos() + btnInvitationInbox.getBtnHeight())) &&
                (Gdx.input.getX() > btnInvitationInbox.getXpos()) && (Gdx.input.getX() < (btnInvitationInbox.getXpos() + btnInvitationInbox.getBtnWidth()))) {
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
        if (time > 3500 && little_crushed) {
            logo = new Texture("logo_little_crushed.png");;;
            little_crushed = false;
        } else if (time > 7000 && crushed) {
            logo = new Texture("logo_crushed.png");;
            crushed = false;
        } else if (time > 10000  && without_castle) {
            logo = new Texture("logo_without_castle.png");;
            without_castle = false;
        } else if (time > 13000 && with_u) {
            logo = new Texture("logo_with_u.png");;
            with_u = false;
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
        sb.draw(btnLogOutIn.getBtn(), btnLogOutIn.getXpos(),
                btnLogOutIn.getYpos(),
                btnLogOutIn.getBtnWidth(), btnLogOutIn.getBtnHeight());
        sb.draw(btnInvitationInbox.getBtn(), btnInvitationInbox.getXpos(),
                btnInvitationInbox.getYpos(),
                btnInvitationInbox.getBtnWidth(), btnInvitationInbox.getBtnHeight());

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
