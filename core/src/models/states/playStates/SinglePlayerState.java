package models.states.playStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.castlecrush.game.CastleCrush;

import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controllers.game_world.GameWorldController;
import models.MockGameWorld;
import models.entities.Cannon;
import models.entities.Player;
import models.entities.Projectile;
import models.states.GameStateManager;
import models.states.State;
import views.game_world.GameWorldDrawer;

import static java.lang.Math.round;

/**
 * Created by Jørgen on 12.03.2018.
 */

public class SinglePlayerState extends State {

    Projectile projectile;
    Cannon cannon;
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
    private int time, oldTime, turnLimit = 20;
    long start, end;

    MockGameWorld world;
    GameWorldDrawer drawer;
    GameWorldController controller;

    private Player player1, player2, activePlayer;

    public SinglePlayerState(GameStateManager gsm) {
        super(gsm);
        world = new MockGameWorld();
        angleUp = true;
        font = new BitmapFont();
        //cannon = new Cannon(Math.round((width / 20) * world.getSCALE()),
        //        Math.round((width / 20)*world.getSCALE()),
        //        Math.round((width / 10) * world.getSCALE()),
        //        Math.round((width / 20) * world.getSCALE()),
        //        new Sprite(new Texture("cannon.png")),
        //        new Sprite(new Texture("wheel.png")), null);

        cannon = world.getCannons().get(0);
        projectile = new Projectile(null, new Vector2(cannon.getX() + cannon.getWidth() / 2,
                cannon.getY() + cannon.getHeight() / 4),
                new Sprite(new Texture("ball_cannon.png")),
                cannon.getWidth() / 10,cannon.getWidth() / 10, new Vector2(0,0), world);


        angleActive = true;
        powerActive = false;
        shotsFired = false;

        world.setCannons(new ArrayList<Cannon>(Arrays.asList(cannon, null)));
        drawer = new GameWorldDrawer(new SpriteBatch(), world);
        //controller = new GameWorldController();

        player1 = new Player();
        player2 = new Player();
        player1.setId("Player 1!");
        player2.setId("Player 2!!!");
        activePlayer = player1;

        start = System.currentTimeMillis();
    }


    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            if (activePlayer.isAngleActive()) {
                System.out.println(activePlayer.getId()+ "  Angle: "+activePlayer.isAngleActive());
                System.out.println("Power: "+activePlayer.isPowerActive());
                float angle = cannon.getAngle();

                activePlayer.switchAngleActive();
                activePlayer.switchPowerActive();

                System.out.println(activePlayer.getId()+ "  Angle: "+activePlayer.isAngleActive());
                System.out.println("Power: "+activePlayer.isPowerActive());

            } else if (activePlayer.isPowerActive()) {
                float power = cannon.getPower();

                System.out.println(activePlayer.getId()+ "  Angle: "+activePlayer.isAngleActive());
                System.out.println("Power: "+activePlayer.isPowerActive());

                activePlayer.switchPowerActive();

                System.out.println(activePlayer.getId()+ "  Angle: "+activePlayer.isAngleActive());
                System.out.println("Power: "+activePlayer.isPowerActive());

            }
        }
        if (!activePlayer.isAngleActive() && !activePlayer.isPowerActive()) {
            //world.setProsjektil(projectile);
            //world.getProsjektil().fire(cannon.getAngle(), cannon.getPower());
            //world.getCannons().get(0).setShotsFired(true);
            ///projectile.fire(cannon.getAngle(), cannon.getPower());
            if (!fired) {
                drawer.fire();
                //fired = true;
                switchPlayer();
            }
            //shotsFired = true;
        }
    }
    int counter = 0;
    @Override
    public void update(float dt) {
        end = System.currentTimeMillis();
        oldTime = time;
        time = (int) Math.floor((end-start)/1000);
        if (time>oldTime){
            System.out.println(time);
        }
        if (time>=turnLimit){
            System.out.println("Switching player turns!");
            System.out.println("Current ctive player: " + activePlayer.getId());
            switchPlayer();
            System.out.println("New active player " + activePlayer.getId());
        }

        handleInput();

        //Make the angle and power increment between respectively 0-90 and 0-100
        float angleSpeed = (float)3;
        float powerSpeed = (float)4;
        if (activePlayer.isAngleActive()) {
            if (cannon.getAngle() >= 90) {
                angleUp = false;
            } else if (cannon.getAngle() <= 0) {
                angleUp = true;
            }
            if (angleUp) {
                cannon.setAngle(cannon.getAngle() + angleSpeed);
            } else if (!angleUp) {
                cannon.setAngle(cannon.getAngle() - angleSpeed);
            }
        } else if (activePlayer.isPowerActive()) {
            if (cannon.getPower() >= 100) {
                powerUp = false;
            } else if (cannon.getPower() <= 0) {
                powerUp = true;
            }
            if (powerUp) {
                cannon.setPower(cannon.getPower() + powerSpeed);
            } else if (!powerUp) {
                cannon.setPower(cannon.getPower() - powerSpeed);
            }
        }
        cannon.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        drawer.render();
        /*sb.begin();

        sb.draw(background1, 0, 0, CastleCrush.WIDTH, CastleCrush.HEIGHT);
        //sb.draw(background2, xCoordBg2, 0, CastleCrush.WIDTH, CastleCrush.HEIGHT);

        if (shotsFired) {
            sb.draw(projectile.getSprite(), projectile.getPosition().x, projectile.getPosition().y,
                    projectile.getWidth(), projectile.getHeight());
        }

        sb.draw(cannon.getDrawable(),
                cannon.getX(),
                cannon.getY(),
                cannon.getWidth() * 3 / 10,
                cannon.getHeight() * 3 / 10,
                cannon.getWidth(),
                cannon.getHeight(), 1, 1,
                cannon.getCannon().getRotation());

        sb.draw(cannon.getWheel(), cannon.getX(), cannon.getY() - 20, cannon.getWidth() * 2 / 5,
                cannon.getHeight());

        font.draw(sb, "Angle: " + cannon.getAngle() + " Power: " + cannon.getPower() + " Rotation"
                 + cannon.getCannon().getRotation(),
                0, height / 10);

        sb.end();*/
    }

    @Override
    public void dispose() {
        drawer.dispose();
    }

    private void switchPlayer(){
        start = System.currentTimeMillis();
        time = 0;
        //Deactivates variables
        if (activePlayer.isAngleActive()){
            activePlayer.switchAngleActive();
        }
        if (activePlayer.isPowerActive()){
            activePlayer.switchPowerActive();
        }

        cannon.setPower(0);
        cannon.setAngle(0);

        System.out.println("Switching");
        //Changes active player
        if (activePlayer == player1){
            activePlayer = player2;
        } else if (activePlayer == player2){
            activePlayer = player1;
        }
        //Activates variables
        if (!activePlayer.isAngleActive()){
            activePlayer.switchAngleActive();
        }
        if (activePlayer.isPowerActive()) {
            activePlayer.switchPowerActive();
        }
    }
}