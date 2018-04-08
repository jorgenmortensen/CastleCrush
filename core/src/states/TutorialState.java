package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.castlecrush.game.CastleCrush;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import components.Button;
import entities.Castle;
import states.menuStates.StartMenuScreen;

/**
 * Created by Jørgen on 05.04.2018.
 */

public class TutorialState extends State {

    List<Texture> textures;

    Button back;

    OrthographicCamera cam;
    OrthographicCamera fullScreenCam;

    public TutorialState(GameStateManager gsm) {
        super(gsm);
        cam = new OrthographicCamera();
        fullScreenCam = new OrthographicCamera();
        cam.setToOrtho(false, CastleCrush.WIDTH / 4, CastleCrush.HEIGHT);
        fullScreenCam.setToOrtho(false, CastleCrush.WIDTH, CastleCrush.HEIGHT);

        Texture texture1 = new Texture("texture1.png");
        Texture texture2 = new Texture("texture2.png");
        Texture texture3 = new Texture("texture3.png");
        Texture texture4 = new Texture("texture4.png");
        textures = new ArrayList<Texture>(Arrays.asList(texture1, texture2, texture3, texture4));

        back = new Button(CastleCrush.WIDTH - CastleCrush.WIDTH / 8, CastleCrush.HEIGHT / 15,
                CastleCrush.WIDTH / 10,
                CastleCrush.HEIGHT / 10,
                new Sprite(new Texture("backBtn.png")));
    }

    @Override
    protected void handleInput() {
        if (!((cam.position.x - Gdx.input.getDeltaX())> CastleCrush.WIDTH - (CastleCrush.WIDTH / 8))
                && !(cam.position.x - Gdx.input.getDeltaX() <= CastleCrush.WIDTH / 8)) {
            if (Gdx.input.getDeltaX() < 0) {
                slideForward(10);
            }
        }
        if ((cam.position.x > CastleCrush.WIDTH * 3 / 4) && isOnBackBtn()) {
            gsm.set(new StartMenuScreen(gsm));
            dispose();
        }
    }

    //If gdx.input.getDeltaX() < 0
    private void slideForward(int slideSpeed) {
        int offset = CastleCrush.WIDTH / 20;

        //From 3 to 4
        if ((cam.position.x >= CastleCrush.WIDTH * 5 / 8) &&
                (cam.position.x != CastleCrush.WIDTH * 7 / 8)){
            System.out.println("AAA");
            cam.translate(slideSpeed, 0);
            cam.update();
            if (cam.position.x >= CastleCrush.WIDTH * 7 / 8 - offset) {
                System.out.println("BBB");
                cam.translate(0, 0);
                cam.position.x = CastleCrush.WIDTH * 7 / 8;
                cam.update();
            }
        }

        //From 2 to 3
        else if ((cam.position.x >= CastleCrush.WIDTH * 3 / 8) &&
                (cam.position.x != CastleCrush.WIDTH * 5 / 8)) {
            System.out.println("CCC");
            cam.translate(slideSpeed, 0);
            cam.update();
            if (cam.position.x >= CastleCrush.WIDTH * 5 / 8) {
                System.out.println("DDD");
                cam.translate(0, 0);
                cam.position.x = CastleCrush.WIDTH * 5 / 8;
                cam.update();
            }
        }

        //From 1 to 2
        else if ((cam.position.x >= CastleCrush.WIDTH * 1 / 8) &&
                (cam.position.x != CastleCrush.WIDTH * 3 / 8)) {
            System.out.println("EEE");
            cam.translate(slideSpeed, 0);
            cam.update();
            if (cam.position.x >= CastleCrush.WIDTH * 3 / 8) {
                System.out.println("FFF");
                cam.position.x = CastleCrush.WIDTH * 3 / 8;
                cam.update();
            }
        }
    }

    private void slideBackward(int slideSpeed) {

    }

    private boolean isOnBackBtn() {
        if (((CastleCrush.HEIGHT - Gdx.input.getY()) > back.getYpos()) &&
                ((CastleCrush.HEIGHT - Gdx.input.getY()) < (back.getYpos() + back.getBtnHeight())) &&
                (((CastleCrush.WIDTH * 3 / 4) + (Gdx.input.getX() * CastleCrush.WIDTH / 4) / CastleCrush.WIDTH) > back.getXpos())&&
                (((CastleCrush.WIDTH * 3 / 4) + (Gdx.input.getX() * CastleCrush.WIDTH / 4) / CastleCrush.WIDTH) < (back.getXpos() + back.getBtnWidth()))) {
            return true;
        }
        return false;
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);
        int counter = 0;
        //Draw all textures to the spriteBatch
        for (Texture texture : textures) {
            sb.draw(texture, counter * CastleCrush.WIDTH / 4, 0,
                    CastleCrush.WIDTH / 4, CastleCrush.HEIGHT);
            counter++;
        }
        sb.draw(back.getBtn(), back.getXpos(), back.getYpos(), back.getBtnWidth(), back.getBtnHeight());
        sb.setProjectionMatrix(fullScreenCam.combined);
        sb.end();

    }

    @Override
    public void dispose() {
    }
}
