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
        super((OnlineMultiplayerState)state, drawer, screenWidth, screenHeight,players);
        System.out.println("OnlineMultiplayerWorld C finished");
    }

    public OnlineMultiplayerWorld(){
        super();
    }

    @Override
    public void fireProjectile(Vector2 velocity) {
        System.out.println("fireProj ....... OK");
        start = System.currentTimeMillis();
        time = 0;
        projectile.fire(velocity);
        System.out.println("projectile fired");
        projectile.setFired(true);
        System.out.println("projectile set fired=true");
        addToRenderList(projectile.getDrawable());
        System.out.println("Projectile added to renderlist");

    }


    public void fireProjectileAndMessage(Vector2 velocity) {
        System.out.println("fireProj.....Message OK");
        fireProjectile(velocity);
        System.out.println("Fire OK");
        ((OnlineMultiplayerState) state).broadcastShotData(velocity);
        System.out.println("Broadcast OK");
    }

    @Override
    protected void checkIfGameOver() {
        if (getPlayer1().getGameWinningObject().getHit()) {
//            //TODO, Opponent wins, isHost MUST BE CHANGED WHEN MERGED WITH GPS!!
            ((OnlineMultiplayerState) state).gameOver(false);
        } else if (getPlayer2().getGameWinningObject().getHit()) {
//            //TODO, You win, isHost MUST BE CHANGED WHEN MERGED WITH GPS!!
            ((OnlineMultiplayerState) state).gameOver(true);
        }
    }
/*
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

        System.out.println("PLAYERLIST");
        for (OnlinePlayer p : players){
            System.out.println("PLAYERLIST1");
            p.setWorld(this);
            System.out.println("PLAYERLIST2");
            if (p.isHost()){
                player1=p;
            }else{
                player2=p;
            }
        }

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
    }*/

}
