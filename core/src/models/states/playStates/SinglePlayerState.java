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

    Projectile projectile;
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
    private int time, oldTime, turnLimit = 15;
    long start, end;

    MockGameWorld world;
    GameWorldDrawer drawer;
    GameWorldController controller;

    private Player player1, player2, activePlayer;

    public SinglePlayerState(GameStateManager gsm) {
        super(gsm);
        world = new MockGameWorld(gsm);
        angleUp = true;
        font = new BitmapFont();
        //cannon = new Cannon(Math.round((width / 20) * world.getSCALE()),
        //        Math.round((width / 20)*world.getSCALE()),
        //        Math.round((width / 10) * world.getSCALE()),
        //        Math.round((width / 20) * world.getSCALE()),
        //        new Sprite(new Texture("cannon.png")),
        //        new Sprite(new Texture("wheel.png")), null);

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
            //world.setProsjektil(projectile);
            //world.getProsjektil().fire(cannon.getAngle(), cannon.getPower());
            //world.getCannons().get(0).setShotsFired(true);
            ///projectile.fire(cannon.getAngle(), cannon.getPower());
            if (!world.getProjectile().isFired()) {
                fire();
                //fired = true;
                //switchPlayer();
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

    public void switchPlayer(){
        start = System.currentTimeMillis();
        time = 0;
        //Deactivates variables
        if (activePlayer.isAngleActive()){
            activePlayer.switchAngleActive();
        }
        if (activePlayer.isPowerActive()){
            activePlayer.switchPowerActive();
        }

        activeCannon.setPower(0);
        activeCannon.setAngle(0);

        System.out.println("Switching");
        //Changes active player
        if (activePlayer == player1){
            activePlayer = player2;
        } else if (activePlayer == player2){
            activePlayer = player1;
        }
        activeCannon = activePlayer.getCannon();
        //Activates variables
        if (!activePlayer.isAngleActive()){
            activePlayer.switchAngleActive();
        }
        if (activePlayer.isPowerActive()) {
            activePlayer.switchPowerActive();
        }

        world.setProjectile(activePlayer);
    }

    public void fire() {
        world.setProjectileVelocity(new Vector2(
                (float)Math.cos(activeCannon.getShootingAngle()*Math.PI/180) * activeCannon.getPower()/3,
                (float)Math.sin(activeCannon.getShootingAngle()*Math.PI/180) * activeCannon.getPower()/3));
        world.getProjectile().setFired(true);
    }
}