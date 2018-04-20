package models.states.playStates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.castlecrush.game.CastleCrush;

import controllers.game_world.GameWorldController;
import models.GameWorld;
import models.entities.Player;
import models.states.GameStateManager;
import models.states.menuStates.LocalGameOverMenu;
import models.states.menuStates.OnlineGameOverMenu;
import views.game_world.GameWorldDrawer;

/**
 * Created by Jørgen on 12.03.2018.
 */

public class LocalMulitplayerState extends SuperPlayState {

    private GameWorldController controller;
    private GameWorld world;
    private GameWorldDrawer gameWorldDrawer;
    static float SCALE = 0.05f;
    private float screenWidth;
    private float screenHeight;


    public LocalMulitplayerState(GameStateManager gsm) {
        super(gsm);
        screenWidth = CastleCrush.WIDTH * SCALE;
        screenHeight = CastleCrush.HEIGHT * SCALE;
        gameWorldDrawer = new GameWorldDrawer(new SpriteBatch(), screenWidth, screenHeight);
        world = new GameWorld(this, gameWorldDrawer, screenWidth, screenHeight);
        controller = new GameWorldController(world);

    }

    @Override
    protected void handleInput() {
//        JEG VIL IKKE HA DENNE METODEN I STATE, FAEN
//        controller.handleInput();
    }

    @Override
    public void gameOver () {
            gsm.set(new OnlineGameOverMenu(gsm, false));
        }

    public void gameOver(Player winningPlayer, Player loosingPLayer) {
        gsm.set(new LocalGameOverMenu(gsm, winningPlayer, loosingPLayer));
    }

    Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    @Override
    public void render (SpriteBatch sb){
        gameWorldDrawer.render();
        debugRenderer.render(world.getPhysicsWorld(), gameWorldDrawer.getCam().combined);
    }

        @Override
    public void dispose () {
        gameWorldDrawer.dispose();
        world.dispose(); // legges til i world?
        debugRenderer.dispose();
    }

    @Override
    public void update (float dt){
        world.update(dt);
        controller.handleInput();

    }

}


//        angleUp = true;
//        font = new BitmapFont();
//        cannon1 = world.getCannons().get(0);
//        cannon2 = world.getCannons().get(1);
//        activeCannon = cannon1;


//        angleActive = true;
//        powerActive = false;
//        shotsFired = false;

        //controller = new GameWorldController();

//        player1 = new Player(cannon1);
//        player2 = new Player(cannon2);
//        player1.setId("Player 1!");
//        player2.setId("Player 2!!!");
//        activePlayer = player1;

   //     start = System.currentTimeMillis();


    //
//            } else if (activePlayer.isPowerActive()) {
//                float power = activeCannon.getPower();
//
//                activePlayer.switchPowerActive();
//
//            }
//        }
//
//
//        if (!activePlayer.isAngleActive() && !activePlayer.isPowerActive()) {
//            if (!world.getOldProjectile().isFired()) {
//                fire();
//            }
//        }
//        end = System.currentTimeMillis();
//        oldTime = time;
//        time = (int) Math.floor((end-start)/1000);
//        if (time>oldTime){
//            System.out.println(time);
//        }
//        if (time>=turnLimit && !world.getOldProjectile().isFired()){
//            System.out.println("Switching player turns! You waited too long 😞");
//            System.out.println("Current active player: " + activePlayer.getId());
//            switchPlayer();
//            System.out.println("New active player " + activePlayer.getId());
//        }
//        // Time limit after shooting
//        if ((world.getOldProjectile().isFired() && time>shootingTimeLimit && world.getOldProjectile().getAbsoluteSpeed()<5) || time>turnLimit) {
//            System.out.println("Switching player turns! Cannon ball has lived for 5 seconds");
//            System.out.println("Current active player: " + activePlayer.getId());
//            switchPlayer();
//            System.out.println("New active player " + activePlayer.getId());
//        }
//
//
//        //Make the angle and power increment between respectively 0-90 and 0-100
//        if (activePlayer.isAngleActive()) {
//            activePlayer.getCannon().updateAngle();
//        } else if (activePlayer.isPowerActive()) {
//            activePlayer.getCannon().updatePower();
//        }
//        activeCannon.update(dt);

    //                activePlayer.switchPowerActive();
    //                activePlayer.switchAngleActive();
    //
    //                float angle = activeCannon.getAngle();
    //            if (activePlayer.isAngleActive()) {
    //        if (Gdx.input.justTouched()) {
    //    protected void handleInput() {
    //    @Override
//      moved to Controller

//    }


//    moved to World
//    public void switchPlayer(){
//        start = System.currentTimeMillis();
//        time = 0;
//        //Deactivates variables
//        if (activePlayer.isAngleActive()){
//            activePlayer.switchAngleActive();
//        }
//        if (activePlayer.isPowerActive()){
//            activePlayer.switchPowerActive();
//        }
//
//        activeCannon.setPower(0);
//        activeCannon.setAngle(0);
//
//        System.out.println("Switching");
//        //Changes active player
//        if (activePlayer == player1){
//            activePlayer = player2;
//        } else if (activePlayer == player2){
//            activePlayer = player1;
//        }
//        activeCannon = activePlayer.getCannon();
//        //Activates variables
//        if (!activePlayer.isAngleActive()){
//            activePlayer.switchAngleActive();
//        }
//        if (activePlayer.isPowerActive()) {
//            activePlayer.switchPowerActive();
//        }
//
//        world.spawnProjectile(activePlayer);
//    }

//    moved to World
//    public void fire() {
//        start = System.currentTimeMillis();
//        time = 0;
//        world.setProjectileVelocity(new Vector2(
//                (float)Math.cos(activeCannon.getShootingAngle()*Math.PI/180) * activeCannon.getPower()/3,
//                (float)Math.sin(activeCannon.getShootingAngle()*Math.PI/180) * activeCannon.getPower()/3));
//        world.getOldProjectile().setFired(true);
//    }
