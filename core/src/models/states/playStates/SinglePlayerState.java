package models.states.playStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.castlecrush.game.CastleCrush;

import controllers.game_world.GameWorldController;
import models.MockGameWorld;
import models.entities.Cannon;
import models.entities.Player;
import models.entities.Projectile;
import models.states.GameStateManager;
import models.states.State;
import views.game_world.GameWorldDrawer;

/**
 * Created by Jørgen on 12.03.2018.
 */

public class SinglePlayerState extends State {

    Cannon cannon1, cannon2, activeCannon;
    int width = CastleCrush.WIDTH;
    int height = CastleCrush.HEIGHT;

    boolean angleUp;
    boolean powerUp;

    public BitmapFont font;

    boolean angleActive;
    boolean powerActive;
    boolean shotsFired;

    float xMax, xCoordBg1, xCoordBg2;
    private Texture background1;
    private Texture background2;
    final int BACKGROUND_MOVE_SPEED = -30;
    boolean fired = false;
    private int time, oldTime, turnLimit = 15, shootingTimeLimit = 5;
    long start, end;

    MockGameWorld world;
    f
    GameWorldController controller;

    private Player player1, player2, activePlayer;

    public SinglePlayerState(GameStateManager gsm) {
        super(gsm);
        /* world = new MockGameWorld(gsm);
        angleUp = true;
        font = new BitmapFont();
        cannon1 = world.getCannons().get(0);
        cannon2 = world.getCannons().get(1);
        activeCannon = cannon1;


        angleActive = true;
        powerActive = false;
        shotsFired = false;

        drawer = new GameWorldDrawer(new SpriteBatch(), world, gsm);
        //controller = new GameWorldController();

        player1 = new Player(cannon1);
        player2 = new Player(cannon2);
        player1.setId("Player 1!");
        player2.setId("Player 2!!!");
        activePlayer = player1;

        start = System.currentTimeMillis();
        */
    }


    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            if (activePlayer.isAngleActive()) {
                float angle = activeCannon.getAngle();

                activePlayer.switchAngleActive();
                activePlayer.switchPowerActive();

            } else if (activePlayer.isPowerActive()) {
                float power = activeCannon.getPower();

                activePlayer.switchPowerActive();

            }
        }


        if (!activePlayer.isAngleActive() && !activePlayer.isPowerActive()) {
            if (!world.getProjectile().isFired()) {
                fire();
            }
        }
    }
    int counter = 0;
    @Override
    public void update(float dt) {
        world.update

        end = System.currentTimeMillis();
        oldTime = time;
        time = (int) Math.floor((end-start)/1000);
        if (time>oldTime){
            System.out.println(time);
        }
        if (time>=turnLimit && !world.getProjectile().isFired()){
            System.out.println("Switching player turns! You waited too long 😞");
            System.out.println("Current active player: " + activePlayer.getId());
            switchPlayer();
            System.out.println("New active player " + activePlayer.getId());
        }
        // Time limit after shooting
        if ((world.getProjectile().isFired() && time>shootingTimeLimit && world.getProjectile().getAbsoluteSpeed()<5) || time>turnLimit) {
            System.out.println("Switching player turns! Cannon ball has lived for 5 seconds");
            System.out.println("Current active player: " + activePlayer.getId());
            switchPlayer();
            System.out.println("New active player " + activePlayer.getId());
        }

        handleInput();

        //Make the angle and power increment between respectively 0-90 and 0-100
        if (activePlayer.isAngleActive()) {
            activePlayer.getCannon().updateAngle();
        } else if (activePlayer.isPowerActive()) {
            activePlayer.getCannon().updatePower();
        }
        activeCannon.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        drawer.render();
    }

    @Override
    public void dispose() {
        drawer.dispose();
    }



}