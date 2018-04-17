package models.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
//import com.sun.xml.internal.ws.client.sei.ResponseBuilder;

/**
 * Created by Jørgen on 09.03.2018.
 */

// Fundamental blocks for the castle

public class GameWinningObject extends Box {


    // private int counter = 0;

    private Music music;
    public GameWinningObject(Body body, Sprite sprite, float width, float height, float density) {
        super(body, sprite, width, height, density);
        music = Gdx.audio.newMusic(Gdx.files.internal("win_song.mp3"));
        music.setLooping(true);
        music.setVolume(0.5f);
    }

    @Override
    public void isHit(boolean isHit){
        super.isHit(isHit);
        music.play();
    }
    //Function to make the sprite change when the GWO is hit
    /*
    public int getCounter() {
        return counter;
    }
    */
    /* public void isHit(){
        counter += 1;
        if(counter == 1){
            this.sprite = sprite1;
        } else if (counter == 2){
            this.sprite = sprite2;
        }

    }
    */
}
