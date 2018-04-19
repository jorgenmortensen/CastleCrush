package models.states.menuStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.castlecrush.game.CastleCrush;

import controllers.menuStatesControllers.StartMenuController;
import models.components.Button;
import models.states.GameStateManager;
import models.states.State;
import models.states.TutorialState;
import views.menuStatesViews.StartMenuDrawer;

import static com.castlecrush.game.CastleCrush.xCoordBg1;
import static com.castlecrush.game.CastleCrush.xCoordBg2;

/**
 * Created by Jørgen on 12.03.2018.
 */

public class StartMenuScreen extends State {

    protected Button btnPlay;
    protected Button btnHelp;

    public static Texture logo;

    protected float xMax = CastleCrush.WIDTH;

    public static long startTime;
    public static boolean little_crushed = true;
    public static boolean crushed = true;
    public static boolean without_castle = true;
    public static boolean with_u = true;
    public static boolean changed_logo;

    protected StartMenuDrawer startMenuDrawer;
    protected StartMenuController startMenuController;

    public StartMenuScreen() {
        super();
        initFields();
    }

    public StartMenuScreen(GameStateManager gsm) {
        super(gsm);
        initFields();
        startMenuDrawer = new StartMenuDrawer();
        startMenuController = new StartMenuController(gsm);
    }

    private void initFields() {
        logo = new Texture("logo.png");
        if (changed_logo) {
            little_crushed = false;
            crushed = false;
            without_castle = false;
            with_u = false;
        }

        this.startTime = TimeUtils.millis();
        makeButtons();
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
        }

    //Send to controller
    @Override
    protected void handleInput() {
        startMenuController.handleInput();
    }

    //Send to drawer
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
        logoCrush();
    }

    //Send to drawer
    @Override
    public void render(SpriteBatch sb) {
        startMenuDrawer.render(sb);
    }

    public long getStartTime(){
        return this.startTime;
    }

    @Override
    public void dispose() {
        try {
            startMenuController.dispose();
            startMenuDrawer.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
