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
            cam.translate(-Gdx.input.getDeltaX() / 2, 0);
            cam.update();
        }
        if ((cam.position.x > CastleCrush.WIDTH * 3 / 4) && isOnBackBtn()) {
            gsm.set(new StartMenuScreen(gsm));
            dispose();
        }
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
        }
        sb.draw(back.getBtn(), back.getXpos(), back.getYpos(), back.getBtnWidth(), back.getBtnHeight());
        sb.setProjectionMatrix(fullScreenCam.combined);
        sb.end();

    }

    @Override
    public void dispose() {
    }
}
