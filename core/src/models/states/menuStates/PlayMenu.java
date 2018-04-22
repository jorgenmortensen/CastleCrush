package models.states.menuStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.castlecrush.game.CastleCrush;
import controllers.menuStatesControllers.PlayMenuController;
import models.components.GravityButton;
import models.states.GameStateManager;
import models.states.State;
import views.menuStatesViews.PlayMenuDrawer;

/**
 * Created by erikkjernlie on 05/04/2018.
 */

// PROBLEMS: local multiplayer button doesn't work, the rest does


public class PlayMenu extends State {

    protected float xMax, xCoordBg1, xCoordBg2;
    protected Texture background1;
    protected Texture background2;
    protected final int BACKGROUND_MOVE_SPEED = -30;

    protected GravityButton btnLocal;
    protected GravityButton btnMulti;
    protected GravityButton btnBack;
    protected Texture logo;

    protected PlayMenuController playMenuController;
    protected PlayMenuDrawer playMenuDrawer;

    public static Thread th;

    // remember to change every .PNG to .png.

    public PlayMenu() {
        super();
        initFields();
    }

    public PlayMenu(GameStateManager gsm) {
        super(gsm);
        initFields();
        playMenuController = new PlayMenuController(gsm);
        playMenuDrawer = new PlayMenuDrawer();
    }

    public void initFields() {
        logo = new Texture("logo.png");
        //background1 = new Texture("background.png");
        makeMovingBackground();
        makeButtons();
    }

    protected void makeMovingBackground(){
        background1 = new Texture(Gdx.files.internal("loop_background_castles.png"));
        background2 = new Texture(Gdx.files.internal("loop_background_castles.png")); // identical
        xMax = CastleCrush.WIDTH;
        xCoordBg1 = xMax;
        xCoordBg2 = 0;
    }

    protected void makeButtons() {
        //Single-menu button
        btnLocal = new GravityButton(CastleCrush.WIDTH / 3,
                5*CastleCrush.HEIGHT / 10,
                CastleCrush.WIDTH / 3,
                CastleCrush.HEIGHT / 10,
                new Sprite(new Texture("multi_local.png")), CastleCrush.HEIGHT);

        btnMulti = new GravityButton(CastleCrush.WIDTH / 3,
                3*CastleCrush.HEIGHT / 10,
                CastleCrush.WIDTH / 3,
                CastleCrush.HEIGHT / 10,
                new Sprite(new Texture("invite_friends.png")), CastleCrush.HEIGHT);

        btnBack = new GravityButton(CastleCrush.WIDTH / 3,
                1*CastleCrush.HEIGHT / 10,
                CastleCrush.WIDTH / 3,
                CastleCrush.HEIGHT / 10,
                new Sprite(new Texture("return_menu.png")), CastleCrush.HEIGHT);
    }

    @Override
    protected void handleInput() {
    }

    @Override
    public void update(float dt) {
        playMenuController.update(dt);
        playMenuDrawer.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        playMenuDrawer.render(sb);
    }

    //dispose textures / buttons
    @Override
    public void dispose() {
        logo.dispose();
    }


}
