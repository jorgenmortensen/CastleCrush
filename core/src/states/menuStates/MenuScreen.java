package states.menuStates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.castlecrush.game.CastleCrush;
import com.sun.corba.se.spi.orbutil.fsm.State;

import components.Button;
import states.GameStateManager;
import states.SettingsState;
import states.TutorialState;

/**
 * Created by Jørgen on 12.03.2018.
 */

public class MenuScreen extends states.State {

    //Located in the center of the screen
    private int btnSingleXCoordinate;
    private int btnSingleYCoordinate;
    private int btnSingleWidth;
    private int btnSingleHeight;


    private int btnMultiXCoordinate;
    private int btnMultiYCoordinate;
    private int btnTutorialXCoordinate;
    private int btnTutorialYCoordinate;

    Sprite btnSingle;
    Sprite btnMulti;
    Sprite btnTutorial;

    //Located in the one of the corners
    private int btnSettingsXCoordinate;
    private int btnSettingsYCoordinate;
    Sprite btnSettings;

    public MenuScreen(GameStateManager gsm) {
        super(gsm);
        btnSingle = new Sprite(new Texture("singleBtn.PNG"));
        btnMulti = new Sprite(new Texture("multiBtn.PNG"));
        btnTutorial = new Sprite(new Texture("tutorialBtn.PNG"));
        btnSettings = new Sprite(new Texture("settingsBtn.PNG"));
    }

    private void makeButtons() {
        //Single-menu button
        Button btnSingle = new Button(CastleCrush.WIDTH / 2 - (CastleCrush.WIDTH / 5),
                CastleCrush.HEIGHT - (CastleCrush.HEIGHT / 4) - CastleCrush.HEIGHT / 10,
                CastleCrush.WIDTH * 2 / 5,
                CastleCrush.HEIGHT * 2 / 10,
                new Sprite(new Texture("singleBtn.PNG")));

        //Multi-menu button
        Button btnMulti = new Button(CastleCrush.WIDTH / 2 - (CastleCrush.WIDTH / 5),
                CastleCrush.HEIGHT - (CastleCrush.HEIGHT * 2 / 4) - CastleCrush.HEIGHT / 10,
                CastleCrush.WIDTH * 2 / 5,
                CastleCrush.HEIGHT * 2 / 10,
                new Sprite(new Texture("multiBtn.PNG")));

        //Tutorial button
        Button btnTutorial = new Button(CastleCrush.WIDTH / 2 - (CastleCrush.WIDTH / 5),
                CastleCrush.HEIGHT - (CastleCrush.HEIGHT * 3 / 4) - CastleCrush.HEIGHT / 10,
                CastleCrush.WIDTH * 2 / 5,
                CastleCrush.HEIGHT * 2 / 10,
                new Sprite(new Texture("tutorialBtn.PNG")));

        //Settings button
        Button btnSettings = new Button(0, 0, CastleCrush.WIDTH / 15,
                CastleCrush.HEIGHT / 15,
                new Sprite(new Texture("settingsBtn.PNG")));

    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched() && isOnSingleBtn()) {
            gsm.set(new SingleplayerMenu(gsm));
            dispose();
        }
        else if (Gdx.input.justTouched() && isOnMultiBtn()) {
            gsm.set(new MultiplayerMenu(gsm));
            dispose();
        }
        else if (Gdx.input.justTouched() && isOnTutorialBtn()) {
            gsm.set(new TutorialState(gsm));
            dispose();
        }
        //else if (Gdx.input.justTouched() && isOnSettingsBtn()) {
          //  gsm.set(new SettingsState());
        //}
    }

    private boolean isOnSingleBtn() {
        if (((Gdx.input.getX() > btnSingle.getX()) && (Gdx.input.getX() < (btnSingle.getX() + btnSingle.getWidth())))
                && ((Gdx.input.getY() > btnSingle.getY()) && (Gdx.input.getY() < (btnSingle.getY() + btnSingle.getHeight())))){
            return true;
        }
        return false;
    }

    private boolean isOnMultiBtn() {
        if (((Gdx.input.getX() > btnMulti.getX()) && (Gdx.input.getX() < (btnMulti.getX() + btnMulti.getWidth())))
                && ((Gdx.input.getY() > btnMulti.getY()) && (Gdx.input.getY() < (btnMulti.getY() + btnMulti.getHeight())))){
            return true;
        }
        return false;
    }

    private boolean isOnTutorialBtn() {
        if (((Gdx.input.getX() > btnTutorial.getX()) && (Gdx.input.getX() < (btnTutorial.getX() + btnTutorial.getWidth())))
                && ((Gdx.input.getY() > btnTutorial.getY()) && (Gdx.input.getY() < (btnTutorial.getY() + btnTutorial.getHeight())))){
            return true;
        }
        return false;
    }

    private boolean isOnSettingsBtn() {
        if (((Gdx.input.getX() > btnSettings.getX()) && (Gdx.input.getX() < (btnSettings.getX() + btnSettings.getWidth())))
                && ((Gdx.input.getY() > btnSettings.getY()) && (Gdx.input.getY() < (btnSettings.getY() + btnSettings.getHeight())))){
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

        sb.draw(btnSingle, CastleCrush.WIDTH / 2 - (CastleCrush.WIDTH / 5),
                CastleCrush.HEIGHT - (CastleCrush.HEIGHT / 4) - CastleCrush.HEIGHT / 10,
                CastleCrush.WIDTH / 3, CastleCrush.HEIGHT / 5);

        sb.draw(btnMulti, CastleCrush.WIDTH / 2 - (CastleCrush.WIDTH / 5),
                CastleCrush.HEIGHT - (CastleCrush.HEIGHT * 2 / 4) - CastleCrush.HEIGHT / 10,
                CastleCrush.WIDTH / 3, CastleCrush.HEIGHT / 5);

        sb.draw(btnTutorial, CastleCrush.WIDTH / 2 - (CastleCrush.WIDTH / 5),
                CastleCrush.HEIGHT - (CastleCrush.HEIGHT * 3 / 4) - CastleCrush.HEIGHT / 10,
                CastleCrush.WIDTH / 3, CastleCrush.HEIGHT / 5);

        sb.end();
    }

    @Override
    public void dispose() {
    }
}
