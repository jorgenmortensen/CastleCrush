package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.castlecrush.game.CastleCrush;

import entities.Castle;

/**
 * Created by Jørgen on 05.04.2018.
 */

public class TutorialState extends State {

    Texture texture1;
    Texture texture2;
    Texture texture3;
    Texture texture4;

    OrthographicCamera cam;



    public TutorialState(GameStateManager gsm) {
        super(gsm);
        cam = new OrthographicCamera();
        cam.setToOrtho(false, CastleCrush.WIDTH / 4, CastleCrush.HEIGHT);
        texture1 = new Texture("texture1.png");
        texture2 = new Texture("texture2.png");
        texture3 = new Texture("texture3.png");
        texture4 = new Texture("texture4.png");
    }

    @Override
    protected void handleInput() {
        if (!((cam.position.x - Gdx.input.getDeltaX())> CastleCrush.WIDTH - (CastleCrush.WIDTH / 8))
                && !(cam.position.x - Gdx.input.getDeltaX() <= CastleCrush.WIDTH / 8)) {
            cam.translate(-Gdx.input.getDeltaX() / 2, 0);
            cam.update();
        }
        if (Gdx.input.justTouched()) {
            System.out.println(cam.position.x) ;
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        sb.draw(texture1, 0, 0, CastleCrush.WIDTH / 4, CastleCrush.HEIGHT);
        sb.draw(texture2, CastleCrush.WIDTH / 4, 0, CastleCrush.WIDTH / 4, CastleCrush.HEIGHT);
        sb.draw(texture3, CastleCrush.WIDTH * 2 / 4, 0, CastleCrush.WIDTH / 4, CastleCrush.HEIGHT);
        sb.draw(texture4, CastleCrush.WIDTH * 3 / 4, 0, CastleCrush.WIDTH / 4, CastleCrush.HEIGHT);

        sb.end();
    }

    @Override
    public void dispose() {
    }
}
