package models.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Created by Jørgen on 24.01.2018.
 */

public class GameStateManager {

    private Stack<State> states;

    public GameStateManager() {
        this.states = new Stack<State>();
    }

    public void push(State state) {
        states.push(state);
    }

    public void pop() {
        states.pop();
    }

    public void set(State state) {
        states.pop();
        states.push(state);
    }

    public void update(float dt) {
        states.peek().update(dt);
    }

    // SpriteBatches are really heavy files, so we are only using one spritebatch and passing it around to different states
    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }


}
