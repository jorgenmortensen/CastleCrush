package states.menuStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.castlecrush.game.CastleCrush;

import components.Button;
import states.GameStateManager;
import states.State;

/**
 * Created by erikkjernlie on 05/04/2018.
 */

// PROBLEMS: local multiplayer button doesn't work, the rest does


public class PlayMenu extends State {

    private Button btnSingle;
    private Button btnMulti;
    private Button btnLocal;
    private Texture logo;
    private Texture small_logo;
    private Texture background;



    public PlayMenu(GameStateManager gsm) {
        super(gsm);
        //logo = new Texture("logo.PNG");
        small_logo = new Texture("small_logo.PNG");
        background = new Texture("background.PNG");
        makeButtons();
    }


    private void makeButtons() {
        //Single-menu button
        btnSingle = new Button(CastleCrush.WIDTH / 3,
                5*CastleCrush.HEIGHT / 10,
                CastleCrush.WIDTH / 3,
                CastleCrush.HEIGHT / 10,
                new Sprite(new Texture("singleBtn.PNG")));

        btnLocal = new Button(CastleCrush.WIDTH / 3,
                3*CastleCrush.HEIGHT / 10,
                CastleCrush.WIDTH / 3,
                CastleCrush.HEIGHT / 10,
                new Sprite(new Texture("multiBtn_local.PNG")));

        btnMulti = new Button(CastleCrush.WIDTH / 3,
                1*CastleCrush.HEIGHT / 10,
                CastleCrush.WIDTH / 3,
                CastleCrush.HEIGHT / 10,
                new Sprite(new Texture("multiBtn.PNG")));
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched() && isOnSingleBtn()) {
            gsm.set(new SingleplayerMenu(gsm));
            System.out.println("Single pressed");
            dispose();
        }
        else if (Gdx.input.justTouched() && isOnMultiBtn()) {
            gsm.set(new MultiplayerMenu(gsm));
            System.out.println("Multi pressed");
            dispose();
        }
        else if (Gdx.input.justTouched() && isOnLocalMultiBtn()) {
            gsm.set(new MultiplayerMenu(gsm));
            System.out.println("Local pressed");
            dispose();
        }

    }

    private boolean isOnSingleBtn() {
        if (((Gdx.input.getX() > btnSingle.getXpos() && (Gdx.input.getX() < (btnSingle.getXpos() + btnSingle.getBtnWidth())))
                && ((CastleCrush.HEIGHT - Gdx.input.getY() - 1) > btnSingle.getYpos()) && ((CastleCrush.HEIGHT - Gdx.input.getY() - 1) < (btnSingle.getYpos() + btnSingle.getBtnHeight())))){
            return true;
        }
        return false;
    }

    private boolean isOnMultiBtn() {
        if (((Gdx.input.getX() > btnMulti.getXpos()) && (Gdx.input.getX() < (btnMulti.getXpos() + btnMulti.getBtnWidth())))
                && (((CastleCrush.HEIGHT - Gdx.input.getY() - 1) > btnMulti.getYpos()) && ((CastleCrush.HEIGHT - Gdx.input.getY() - 1) < (btnMulti.getYpos() + btnMulti.getBtnHeight())))){
            return true;
        }
        return false;
    }

    private boolean isOnLocalMultiBtn() {
        if (((Gdx.input.getX() > btnLocal.getXpos()) && (Gdx.input.getX() < (btnLocal.getXpos() + btnLocal.getBtnWidth())))
                && (((CastleCrush.HEIGHT - Gdx.input.getY() - 1) > btnLocal.getYpos()) && ((CastleCrush.HEIGHT - Gdx.input.getY() - 1) < (btnLocal.getYpos() + btnLocal.getBtnHeight())))){
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
        sb.draw(background, 0, 0, CastleCrush.WIDTH, CastleCrush.HEIGHT);
        sb.draw(btnSingle.getBtn(), CastleCrush.WIDTH / 3,
                CastleCrush.HEIGHT / 2,
                CastleCrush.WIDTH / 3,
                CastleCrush.HEIGHT / 10);

        sb.draw(btnMulti.getBtn(), CastleCrush.WIDTH / 3,
                CastleCrush.HEIGHT / 10,
                CastleCrush.WIDTH / 3,
                CastleCrush.HEIGHT / 10);

        sb.draw(btnLocal.getBtn(), CastleCrush.WIDTH / 3,
                3*CastleCrush.HEIGHT/10,
                CastleCrush.WIDTH / 3,
                CastleCrush.HEIGHT / 10);

        sb.draw(small_logo, 0, CastleCrush.HEIGHT * 7 / 10, CastleCrush.WIDTH, 3*CastleCrush.HEIGHT/10);

        sb.end();
    }

    @Override
    public void dispose() {
    }
}
