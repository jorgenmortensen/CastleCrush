package states.playStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.castlecrush.game.CastleCrush;

import entities.Cannon;
import entities.Projectile;
import states.GameStateManager;
import states.State;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.round;
import static java.lang.Math.sin;

/**
 * Created by Jørgen on 12.03.2018.
 */

public class SinglePlayerState extends State{

    Projectile projectile;
    Cannon cannon;
    int width = CastleCrush.WIDTH;
    int height = CastleCrush.HEIGHT;

    boolean angleUp;
    boolean powerUp;

    private BitmapFont font;

    boolean angleActive;
    boolean powerActive;
    boolean shotsFired;

    float xMax, xCoordBg1, xCoordBg2;
    private Texture background1;
    private Texture background2;
    final int BACKGROUND_MOVE_SPEED = -30;

    public SinglePlayerState(GameStateManager gsm) {
        super(gsm);
        angleUp = true;
        font = new BitmapFont();
        cannon = new Cannon(new Vector3(width / 10, width / 10, 0), width / 10, width / 20,
                0, 0, new Sprite(new Texture("cannon.png")),
                new Sprite(new Texture("wheel.png")));

        projectile = new Projectile(new Vector3(cannon.getPosition().x + cannon.getWidth() / 2,
                cannon.getPosition().y + cannon.getHeight() / 4, 0),
                cannon.getWidth() / 10,cannon.getWidth() / 10, new Vector3(0,0,0),
                new Sprite(new Texture("ball_cannon.png")));

        makeMovingBackground();

        angleActive = true;
        powerActive = false;
        shotsFired = false;
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
        if (!angleActive && !powerActive) {
            projectile.fire(cannon.getAngle(), cannon.getPower());
            shotsFired = true;
        }
    }

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
        float powerSpeed = (float)0.5;
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
        sb.begin();

        sb.draw(background1, 0, 0, CastleCrush.WIDTH, CastleCrush.HEIGHT);
        //sb.draw(background2, xCoordBg2, 0, CastleCrush.WIDTH, CastleCrush.HEIGHT);

        if (shotsFired) {
            sb.draw(projectile.getSprite(), projectile.getPosition().x, projectile.getPosition().y,
                    projectile.getWidth(), projectile.getHeight());
        }

        sb.draw(cannon.getCannon(), cannon.getPosition().x, cannon.getPosition().y,
                cannon.getWidth() * 3 / 10, cannon.getHeight() * 3 / 10, cannon.getWidth(), cannon.getHeight(), 1, 1,
                cannon.getCannon().getRotation());

        sb.draw(cannon.getWheel(), cannon.getPosition().x, cannon.getPosition().y - 20, cannon.getWidth() * 2 / 5,
                cannon.getHeight());

        font.draw(sb, "Angle: " + cannon.getAngle() + " Power: " + cannon.getPower() + " Rotation"
                 + cannon.getCannon().getRotation(),
                0, height / 10);

        sb.end();
    }

    @Override
    public void dispose() {
    }
}