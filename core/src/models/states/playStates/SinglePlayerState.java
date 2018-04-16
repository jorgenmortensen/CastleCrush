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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import models.MockGameWorld;
import models.entities.Cannon;
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

    MockGameWorld world;
    GameWorldDrawer drawer;

    public SinglePlayerState(GameStateManager gsm) {
        super(gsm);
        world = new MockGameWorld();
        angleUp = true;
        font = new BitmapFont();
        cannon = new Cannon(Math.round((width / 20) * world.getSCALE()),
                Math.round((width / 20)*world.getSCALE()),
                Math.round((width / 10) * world.getSCALE()),
                Math.round((width / 20) * world.getSCALE()),
                new Sprite(new Texture("cannon.png")),
                new Sprite(new Texture("wheel.png")), null);

        projectile = new Projectile(null, new Vector2(cannon.getX() + cannon.getWidth() / 2,
                cannon.getY() + cannon.getHeight() / 4),
                new Sprite(new Texture("ball_cannon.png")),
                cannon.getWidth() / 10,cannon.getWidth() / 10, new Vector2(0,0));

        makeMovingBackground();

        angleActive = true;
        powerActive = false;
        shotsFired = false;

        world.setCannons(new ArrayList<Cannon>(Arrays.asList(cannon, null)));
        drawer = new GameWorldDrawer(new SpriteBatch(), world);
    }

    private void makeMovingBackground(){
        background1 = new Texture(Gdx.files.internal("loop_background_castles.png"));
        background2 = new Texture(Gdx.files.internal("loop_background_castles.png")); // identical
        xMax = CastleCrush.WIDTH;
        xCoordBg1 = xMax;
        xCoordBg2 = 0;
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            if (angleActive) {
                float angle = cannon.getAngle();
                angleActive = false;
                powerActive = true;
            } else if (powerActive) {
                float power = cannon.getPower();
                powerActive = false;
            }
        }
        if (!angleActive && !powerActive && (counter == 0)) {
            //world.setProsjektil(projectile);
            //world.getProsjektil().fire(cannon.getAngle(), cannon.getPower());
            //world.getCannons().get(0).setShotsFired(true);
            ///projectile.fire(cannon.getAngle(), cannon.getPower());
            if (!fired) {
                drawer.fire();
                fired = true;
            }
            counter++;
            //shotsFired = true;
        }
    }
    int counter = 0;
    @Override
    public void update(float dt) {
        handleInput();
        // makes the background move to the left
        xCoordBg1 += BACKGROUND_MOVE_SPEED * Gdx.graphics.getDeltaTime();
        xCoordBg2 = xCoordBg1 - xMax;  // We move the background, not the camera
        if (xCoordBg1 <= 0) {
            xCoordBg1 = xMax;
            xCoordBg2 = 0;
        }

        //Make the angle and power increment between respectively 0-90 and 0-100
        float angleSpeed = (float)0.5;
        float powerSpeed = (float)2;
        if (angleActive) {
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
        } else if (powerActive) {
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
        projectile.update(dt);
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
}