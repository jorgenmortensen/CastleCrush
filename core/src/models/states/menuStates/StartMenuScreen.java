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
import views.menuStatesViews.StartMenuDrawer;

/**
 * Created by Jørgen on 12.03.2018.
 */

public class StartMenuScreen extends State {

    protected Button btnPlay;
    protected Button btnHelp;
    public static Texture logo;
    protected Button btnLogOutIn;

    protected float xMax, xCoordBg1, xCoordBg2;
    protected Texture background1,background2,t;
    protected final int BACKGROUND_MOVE_SPEED = -30;

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
        System.out.println("startmenuscreeen ruun");
        startMenuDrawer = new StartMenuDrawer();
        startMenuController = new StartMenuController(gsm);
    }

    public StartMenuScreen(GameStateManager gsm, boolean v) {
        super(gsm);
        initFields();
        startMenuDrawer = new StartMenuDrawer();
        startMenuController = new StartMenuController(gsm);

        if (CastleCrush.playServices.isSignedIn()) {
            System.out.println();
            CastleCrush.playServices.toast("Welcome back " + CastleCrush.playServices.getDisplayName() + "!");

        }else{
            CastleCrush.playServices.signIn();
        }
    }

    public void initFields() {
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

    protected void makeMovingBackground(){
        background1 = new Texture(Gdx.files.internal("loop_background_castles.png"));
        background2 = new Texture(Gdx.files.internal("loop_background_castles.png")); // identical
        xMax = CastleCrush.WIDTH;
        xCoordBg1 = xMax;
        xCoordBg2 = 0;
    }

    protected void makeButtons() {
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
    }
    @Override
    protected void handleInput() {}

    @Override
    public void update(float dt) {
        startMenuController.update(dt);
        startMenuDrawer.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        startMenuDrawer.render(sb);
    }

    public long getStartTime(){

        return this.startTime;
    }

    @Override
    public void dispose() {
        logo.dispose();
        background1.dispose();
        background2.dispose();
    }
}
