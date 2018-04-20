package models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import models.entities.Cannon;
import models.entities.OnlinePlayer;
import models.states.State;
import models.states.playStates.SuperPlayState;
import views.game_world.GameWorldDrawer;

public class OnlineMultiplayerWorld extends GameWorld {

    public OnlineMultiplayerWorld(SuperPlayState state, GameWorldDrawer drawer, float screenWidth, float screenHeight) {
        super(state, drawer, screenWidth, screenHeight);

    }

    @Override
    public void fireProjectile(Vector2 velocity) {
        start = System.currentTimeMillis();
        time = 0;
        projectile.fire(velocity);
        projectile.setFired(true);
        addToRenderList(projectile.getDrawable());

//        ******************************************
//        SEND VELOCITY AS MESSAGE HERE
    }

    @Override
    protected void checkIfGameOver() {
        if (getPlayer1().getGameWinningObject().getHit()) {
//            //TODO, Opponent wins, isHost MUST BE CHANGED WHEN MERGED WITH GPS!!
//            if ((OnlinePlayer) player2.isYou())
//            state.gameOver();

        } else if (getPlayer2().getGameWinningObject().getHit()) {
//            //TODO, You win, isHost MUST BE CHANGED WHEN MERGED WITH GPS!!
//            state.gameOver();

        }
    }

    @Override
    protected void createPlayerAndCannon(){
        Sprite powerbarSprite1 = new Sprite(new Texture(powerBarString));
        Sprite powerbarSprite2 = new Sprite(new Texture(powerBarString));
        Sprite cannonSprite1 = new Sprite(new Texture(cannonString));
        Sprite cannonSprite2 = new Sprite(new Texture(cannonString));
        addToRenderList(cannonSprite1);
        addToRenderList(cannonSprite2);
        addToRenderList(powerbarSprite1);
        addToRenderList(powerbarSprite2);
//      PLAYER THAT INVITES TO ONLINE GAME IS PLAYER ONE
        player1 = new OnlinePlayer("player1", this);
//      PLAYER THAT INVITES TO ONLINE GAME IS PLAYER ONE
        player2 = new OnlinePlayer("player2", this);
        cannon1 = new Cannon(player1, cannon1position,
                groundLevel, cannonWidth, cannonHeight,
                cannonSprite1,  powerbarSprite1, true);

        cannon2 = new Cannon(player2, cannon2position,
                groundLevel, cannonWidth, cannonHeight,
                cannonSprite2, powerbarSprite2, false);
        player1.setCannon(cannon1);
        player2.setCannon(cannon2);
        activePlayer = player1;
        activeCannon = player1.getCannon();
        activeCannon.activate();
    }

}
