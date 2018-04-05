package states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Jørgen on 05.04.2018.
 */

public class TutorialState extends State{

    public TutorialState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    protected void handleInput() {
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
