package models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import models.entities.Cannon;
import models.entities.OnlinePlayer;
import models.entities.Player;
import models.states.State;
import models.states.menuStates.OnlineGameOverMenu;
import models.states.playStates.OnlineMultiplayerState;
import models.states.playStates.SuperPlayState;
import views.game_world.GameWorldDrawer;

public class OnlineMultiplayerWorld extends GameWorld {

    public OnlineMultiplayerWorld(SuperPlayState state, GameWorldDrawer drawer, float screenWidth, float screenHeight, List<OnlinePlayer> players) {
        super(state, drawer, screenWidth, screenHeight,players);
        System.out.println("OnlineMultiplayerWorld C finished");
    }

//    @Override
//    public void fireProjectile(Vector2 velocity) {
//        System.out.println("fireProj ....... OK");
//        start = System.currentTimeMillis();
//        time = 0;
//        projectile.fire(velocity);
//        System.out.println("projectile fired");
//        projectile.setFired(true);
//        System.out.println("projectile set fired=true");
//        addToRenderList(projectile.getSprite());
//        System.out.println("Projectile added to renderlist");
//
//    }


    public void fireProjectileAndMessage(Vector2 velocity) {
        System.out.println("fireProj.....Message OK");
        fireProjectile(velocity);
        System.out.println("Fire OK");
        ((OnlineMultiplayerState) state).broadcastShotData(velocity);
        System.out.println("Broadcast OK");
    }

    @Override
    protected void checkIfGameOver() {
        if (getPlayer1().getGameWinningObject().isHit()) {
//            //TODO, Opponent wins, isHost MUST BE CHANGED WHEN MERGED WITH GPS!!
            ((OnlineMultiplayerState) state).gameOver(false);
        } else if (getPlayer2().getGameWinningObject().isHit()) {
//            //TODO, You win, isHost MUST BE CHANGED WHEN MERGED WITH GPS!!
            ((OnlineMultiplayerState) state).gameOver(true);
        }
    }

    @Override
    public void input() {
        if (((OnlinePlayer) activePlayer).isSelf()) {
            activeCannon.progressShootingSequence();
        }
    }
}
