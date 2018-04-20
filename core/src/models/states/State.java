package models.states;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.castlecrush.game.CastleCrush;

import models.components.Button;
import models.states.menuStates.SettingsMenu;
import models.states.menuStates.StartMenuScreen;

import static models.states.menuStates.StartMenuScreen.changed_logo;
import static models.states.menuStates.StartMenuScreen.crushed;
import static models.states.menuStates.StartMenuScreen.little_crushed;
import static models.states.menuStates.StartMenuScreen.logo;
import static models.states.menuStates.StartMenuScreen.startTime;
import static models.states.menuStates.StartMenuScreen.with_u;
import static models.states.menuStates.StartMenuScreen.without_castle;

/**
 * Created by Jørgen on 24.01.2018.
 */

public abstract class State {

    protected OrthographicCamera cam;
    protected Vector3 mouse;
    protected GameStateManager gsm;
    protected Button btnSettings;
    protected Button btnHome;
    protected Button btnSound;
    protected Button btnInvitationInbox;
    protected Texture background1;
    protected Texture background2;

    protected State() {
        cam = new OrthographicCamera();
        mouse = new Vector3();
        initButtons();
        makeMovingBackground();
    }

    protected State(GameStateManager gsm) {
        this.gsm = gsm;
        cam = new OrthographicCamera();
        mouse = new Vector3();
        initButtons();
        makeMovingBackground();
    }

    //Initialize all the buttons
    private void initButtons() {
        btnSettings = new Button(CastleCrush.WIDTH * 14 / 15, 0,
                CastleCrush.WIDTH / 15, CastleCrush.WIDTH / 15,
                new Sprite(new Texture("settingsBtn.png")));
        btnHome = new Button(CastleCrush.WIDTH * 14 / 15, 0,
                CastleCrush.WIDTH / 15, CastleCrush.WIDTH / 15,
                new Sprite(new Texture(("homeBtn.png"))));
        btnSound = new Button(0, 0, CastleCrush.WIDTH / 15, CastleCrush.WIDTH / 15,
                CastleCrush.soundOn ? new Sprite(new Texture("sound_on.png")) : new Sprite(new Texture("sound_off.png")));

        btnInvitationInbox = new Button(0, 0,
                CastleCrush.WIDTH / 15, CastleCrush.WIDTH / 15,
                new Sprite(new Texture("invitationBtn.png")));
    }

    protected void makeMovingBackground(){
        background1 = new Texture(Gdx.files.internal("loop_background_castles.png"));
        background2 = new Texture(Gdx.files.internal("loop_background_castles.png")); // identical
    }

    //True if one clicks the given button, false otherwise
    protected boolean isOnButton(Button button) {
        if (((CastleCrush.HEIGHT - Gdx.input.getY()) > button.getYpos()) &&
                ((CastleCrush.HEIGHT - Gdx.input.getY()) < (button.getYpos() + button.getBtnHeight())) &&
                (Gdx.input.getX() > button.getXpos()) && (Gdx.input.getX() < (button.getXpos() + button.getBtnWidth()))) {
            return true;
        }
        return false;
    }

    //Push the settingsState to the stack
    protected void toSettingsState(GameStateManager gsm) {
        gsm.push(new SettingsMenu(gsm));
    }

    //Sets the startMenuScreen to the active state in the stack
    protected void goToMainMenu() {
        gsm.push(new StartMenuScreen(gsm));
    }

    //Make the castle in the logo crush and tranforming it to an U
    protected void logoCrush() {
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

    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();

}
