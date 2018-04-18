package models.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.castlecrush.game.CastleCrush;

/**
 * Created by Jørgen on 09.03.2018.
 */

// Fundamental blocks for the castle

public class GameWinningObject extends Box {


    // private int counter = 0;

    public GameWinningObject(Body body, Sprite sprite, float width, float height, float density) {
        super(body, sprite, width, height, density);
        CastleCrush.music.stop();
        CastleCrush.music = Gdx.audio.newMusic(Gdx.files.internal("win_song.mp3"));
        CastleCrush.music.setLooping(true);
        CastleCrush.music.setVolume(0.5f);
    }

    @Override
    public void isHit(boolean isHit){
        super.isHit(isHit);
        CastleCrush.music.play();
    }

}
