package models.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.castlecrush.game.CastleCrush;
//import com.sun.xml.internal.ws.client.sei.ResponseBuilder;

/**
 * Created by Jørgen on 09.03.2018.
 */

// Fundamental blocks for the castle

public class Box extends PhysicalGameObject {

    private boolean isHit = false;
    private Sound hitSound;

    public Box(Body body, Sprite sprite) {
        super(body, sprite);
        hitSound = Gdx.audio.newSound(Gdx.files.internal("crate_break.ogg"));
    }

    public void isHit(boolean isHit){
        this.isHit = isHit;
        if (CastleCrush.soundEffectsOn){
            hitSound.play(0.5f);
        }
    }

    public void setHit(boolean isHit){
        this.isHit = isHit;
    }

    public boolean isHit() { return isHit; }



}
