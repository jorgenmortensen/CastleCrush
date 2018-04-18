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
import models.states.State;
import models.states.playStates.SinglePlayerState;
import views.menuStatesViews.PlayMenuDrawer;

/**
 * Created by erikkjernlie on 05/04/2018.
 */

// PROBLEMS: local multiplayer button doesn't work, the rest does

public class  PlayMenu extends State {

    public float xMax;

    protected GravityButton btnSingle;
    protected GravityButton btnMulti;
    protected GravityButton btnLocal;

    protected PlayMenuDrawer playMenuDrawer;

    // remember to change every .PNG to .png.

    public PlayMenu() {
        super();
        initFields();
    }

    public PlayMenu(GameStateManager gsm) {
        super(gsm);
        initFields();
        playMenuDrawer = new PlayMenuDrawer(gsm);
    }

    private void initFields() {
        makeMovingBackground();
        xMax = CastleCrush.WIDTH;
        makeButtons();
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
    }

    @Override
    protected void handleInput() {
        playMenuDrawer.handleInput();
    }

    @Override
    public void update(float dt) {
        playMenuDrawer.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        playMenuDrawer.render(sb);
    }

    //dispose textures / buttons
    @Override
    public void dispose() {
        background1.dispose();
        background2.dispose();
    }

    //Makes the background move to the left
    protected void moveBackground() {
        // makes the background move to the left
        CastleCrush.xCoordBg1 += CastleCrush.BACKGROUND_MOVE_SPEED * Gdx.graphics.getDeltaTime();
        CastleCrush.xCoordBg2 = CastleCrush.xCoordBg1 - xMax;  // We move the background, not the camera
        if (CastleCrush.xCoordBg1 <= 0) {
            CastleCrush.xCoordBg1 = xMax;
            CastleCrush.xCoordBg2 = 0;
        }
    }

}
