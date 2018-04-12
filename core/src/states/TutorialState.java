package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.castlecrush.game.CastleCrush;

import components.Button;
import components.SlidingObjectXdirection;
import states.menuStates.StartMenuScreen;

/**
 * Created by Jørgen on 05.04.2018.
 */

public class TutorialState extends State {

    Texture texture1;
    Texture texture2;
    Texture texture3;
    Texture texture4;
    Button back;
    private SlidingObjectXdirection sf;
    public boolean screenTouched;

    OrthographicCamera cam;
    OrthographicCamera fullScreenCam;

    public TutorialState(GameStateManager gsm) {
        super(gsm);
        cam = new OrthographicCamera();
        fullScreenCam = new OrthographicCamera();
        cam.setToOrtho(false, CastleCrush.WIDTH / 4, CastleCrush.HEIGHT);
        fullScreenCam.setToOrtho(false, CastleCrush.WIDTH, CastleCrush.HEIGHT);
        texture1 = new Texture("castle_1.png");
        texture2 = new Texture("castle_2.png");
        texture3 = new Texture("castle_3.png");
        texture4 = new Texture("castle_4.png");
        back = new Button(CastleCrush.WIDTH - CastleCrush.WIDTH / 8, CastleCrush.HEIGHT / 15,
                CastleCrush.WIDTH / 10,
                CastleCrush.HEIGHT / 10,
                new Sprite(new Texture("return_menu.png")));
        
        sf = new SlidingObjectXdirection(-CastleCrush.HEIGHT/4, 3*CastleCrush.HEIGHT/4, CastleCrush.HEIGHT/4, CastleCrush.HEIGHT/4, new Sprite(new Texture("sliding_finger.png")),5*CastleCrush.WIDTH/4, -900);
        screenTouched = false;
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isTouched()){
            screenTouched = true;
        }
        if (!((cam.position.x - Gdx.input.getDeltaX())> CastleCrush.WIDTH - (CastleCrush.WIDTH / 8))
                && !(cam.position.x - Gdx.input.getDeltaX() <= CastleCrush.WIDTH / 8)) {
            cam.translate(-Gdx.input.getDeltaX() / 3, 0);


            // sliding backward
            if (cam.position.x < CastleCrush.WIDTH/4 && !Gdx.input.isTouched()){
                cam.translate(CastleCrush.WIDTH/8-cam.position.x,0);
            }
            if (cam.position.x < 2*CastleCrush.WIDTH/4 && cam.position.x > CastleCrush.WIDTH /4 && !Gdx.input.isTouched()) {
                cam.translate(3*CastleCrush.WIDTH/8-cam.position.x,0);
            }
            if (cam.position.x < 3*CastleCrush.WIDTH/4 && cam.position.x > CastleCrush.WIDTH*2/4 && !Gdx.input.isTouched()) {
                cam.translate(5*CastleCrush.WIDTH/8-cam.position.x,0);
            }


            // sliding forward
            if(cam.position.x > CastleCrush.WIDTH/4 && !Gdx.input.isTouched() && cam.position.x < CastleCrush.WIDTH * 3/8){
               cam.translate((3*CastleCrush.WIDTH/8 - cam.position.x),0);
            }

            if(cam.position.x > 2*CastleCrush.WIDTH/4 && !Gdx.input.isTouched() && cam.position.x < CastleCrush.WIDTH * 5/8){
                cam.translate(5*CastleCrush.WIDTH/8 - cam.position.x,0);
            }
            if(cam.position.x > 3*CastleCrush.WIDTH/4 && !Gdx.input.isTouched() && cam.position.x < CastleCrush.WIDTH * 7/8){
                cam.translate(7*CastleCrush.WIDTH/8 - cam.position.x,0);
            }

            cam.update();
        }
        if ((cam.position.x > CastleCrush.WIDTH * 3 / 4) && isOnBackBtn()) {
            //gsm.set(new StartMenuScreen(gsm));
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
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            System.out.println("PRESSED");
            gsm.set(new StartMenuScreen(gsm));
        }
        sf.update(dt);

    }



    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);
        sb.draw(texture1, 0, 0, CastleCrush.WIDTH / 4, CastleCrush.HEIGHT);
        sb.draw(texture2, CastleCrush.WIDTH / 4, 0, CastleCrush.WIDTH / 4, CastleCrush.HEIGHT);
        sb.draw(texture3, CastleCrush.WIDTH * 2 / 4, 0, CastleCrush.WIDTH / 4, CastleCrush.HEIGHT);
        sb.draw(texture4, CastleCrush.WIDTH * 3 / 4, 0, CastleCrush.WIDTH / 4, CastleCrush.HEIGHT);
        sb.draw(back.getBtn(), back.getXpos(), back.getYpos(), back.getBtnWidth(), back.getBtnHeight());
        sb.setProjectionMatrix(fullScreenCam.combined);
        if (!screenTouched){
            sb.draw(sf.getBtn(), sf.getXpos(), sf.getYpos(), sf.getBtnWidth(), sf.getBtnHeight());
        }
        sb.end();

    }

    @Override
    public void dispose() {
    }
}
