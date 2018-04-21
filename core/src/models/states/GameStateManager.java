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
        System.out.println("setting new state");
        try {
            if (!states.empty()){
                states.pop();
            }
                states.push(state);
        }catch(Exception e){
            e.printStackTrace();
        }

        System.out.println("new state set");
    }

    public void update(float dt) {
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }


}
