package models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.castlecrush.game.CastleCrush;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.components.Button;
import models.entities.Box;
import models.entities.Cannon;
import models.entities.GameWinningObject;
import models.entities.OneWayWall;
import models.entities.OnlinePlayer;
import models.entities.PhysicalGameObject;
import models.entities.Player;
import models.entities.Projectile;
import models.states.playStates.SuperPlayState;
import views.game_world.GameWorldDrawer;


/**
 * Created by Ludvig on 07/04/2018.
 */

public class GameWorld {

    static final float STEP_TIME = 1f / 60f;
    static final int VELOCITY_ITERATIONS = 6;
    static final int POSITION_ITERATIONS = 2;
    private World physicsWorld;
    private float boxWidth;
    private float boxHeight;

    private List<Fixture> bodiesToDestroy = new ArrayList<Fixture>();

    private List<PhysicalGameObject> physicalGameObjectList;
    private Box ground;
    private float screenWidth;
    private float screenHeight;
    float groundLevel;
    Player player1;
    Player player2;
    Player activePlayer;
    Cannon activeCannon;
    Cannon cannon2;
    Cannon cannon1;

//    sprites
    private String bottomGroundString= "bottom_ground.png";
    private String windowBoxString= "window_box.png";
    private String normalBoxString= "normal_box.png";
    private String roofBoxString= "roof_box.png";
    private String cannonString= "cannon.png";
    private String cannonBallString = "ball_cannon.png";
    private String gameWinningObjectString= "gwo3.png";
    private String powerBarString= "powerBar.png";
    private String settingsButtonString = "settings_btn.png";
    private String homeButtonString = "homeBtn.png";
    float cannonWidth;
    float cannonHeight;
    float cannon1position;
    float cannon2position;
    private Box[][] castleMatrix, mirroredCastleMatrix;
    private String [][] boxStrings;
    int time;
    private int oldTime, turnLimit = 15, shootingTimeLimit = 5;
    long start, end;
    public Button homeButton;
    public Button settingsButton;

    protected SuperPlayState state;
    Projectile projectile;
    protected GameWorldDrawer drawer;
    protected List<OnlinePlayer> players = null;

    public GameWorld(){
    }

    public GameWorld(SuperPlayState state, GameWorldDrawer drawer, float screenWidth, float screenHeight, List<OnlinePlayer> players) {
        this.drawer = drawer;
        this.players = players;
        this.state = state;
        groundLevel = screenHeight/25;
        boxWidth = screenWidth/20;
        boxHeight = screenWidth/20;
        Box2D.init();
        physicsWorld = new World(new Vector2(0, -10), true);
        physicsWorld.setContactListener(new CollisionHandler(this));
        this.drawer = drawer;

        //List of all objects that need to update the position of it's sprite
        physicalGameObjectList = new ArrayList<PhysicalGameObject>();
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        cannonWidth = screenWidth/30;
        cannonHeight = screenHeight/30;
        cannon1position = screenWidth/3;
        cannon2position = screenWidth*2/3 - cannonWidth;
        createPlayerAndCannon();
        generateBodies();
        initiateButtons();
    }

    private void initiateButtons(){
        homeButton = new Button(CastleCrush.WIDTH/100, CastleCrush.HEIGHT-CastleCrush.WIDTH/15  - CastleCrush.HEIGHT/100,
                (int) CastleCrush.WIDTH / 15, (int) CastleCrush.WIDTH / 15,
                new Sprite(new Texture((homeButtonString))));
        homeButton.getBtn().setPosition(screenWidth/100, screenHeight - screenWidth / 15 - screenWidth/100);
        homeButton.getBtn().setSize(screenWidth / 15, screenWidth / 15);
        addToRenderList(homeButton.getBtn());
        settingsButton = new Button(CastleCrush.WIDTH * 14 / 15 - CastleCrush.WIDTH/100, CastleCrush.HEIGHT-CastleCrush.WIDTH/15  - CastleCrush.HEIGHT/100,
                (int) CastleCrush.WIDTH / 15, (int) CastleCrush.WIDTH / 15,
                new Sprite(new Texture((settingsButtonString))));
        settingsButton.getBtn().setPosition(screenWidth*14/15 - screenWidth/100, screenHeight - screenWidth / 15 - screenWidth/100);
        settingsButton.getBtn().setSize(screenWidth / 15, screenWidth / 15);
        addToRenderList(settingsButton.getBtn());

    }

    protected void createPlayerAndCannon(){
        Sprite powerbarSprite1 = new Sprite(new Texture(powerBarString));
        Sprite powerbarSprite2 = new Sprite(new Texture(powerBarString));
        Sprite cannonSprite1 = new Sprite(new Texture(cannonString));
        Sprite cannonSprite2 = new Sprite(new Texture(cannonString));
        addToRenderList(cannonSprite1);
        addToRenderList(cannonSprite2);
        addToRenderList(powerbarSprite1);
        addToRenderList(powerbarSprite2);
        if (players == null) {
            player1 = new Player("player1", this);
            player2 = new Player("player2", this);
        }else{
            //System.out.println("Setting online players");
            for (OnlinePlayer p : players){
                if (p.isHost()){
                    player1=p;
                }else{
                    player2=p;
                }
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
    }


    private void generateBodies() {
        createGround();
        makeCastle( screenWidth*0.2f, screenHeight*0.6f);

//      lage det første prosjektilet
        spawnProjectile();

        createOneWayWalls(player1.getCannon().getX() - projectile.getSprite().getWidth()/2);
        createOneWayWalls(player2.getCannon().getX() + player2.getCannon().getWidth()+ projectile.getSprite().getWidth()/2);
    }

    public void makeCastle(float castleWidth, float castleHeight) {
        // Makes castle for player1
        int numHorizontalBoxes = (int) Math.floor((castleWidth / boxWidth));
        int numVerticalBoxes = (int) Math.floor((castleHeight / boxHeight));
        float xPos1, yPos;
        Random ran = new Random();

        //Defining structure for castle to the left
        boxStrings = new  String[][] {
                {gameWinningObjectString, normalBoxString, windowBoxString, roofBoxString},
                {normalBoxString, normalBoxString, normalBoxString, windowBoxString, roofBoxString},
                {normalBoxString, normalBoxString, normalBoxString, roofBoxString},
                {normalBoxString, normalBoxString, windowBoxString, roofBoxString}
        };

        float startPosX = screenWidth*0.25f-boxWidth*boxStrings.length;

        castleMatrix = new Box[boxStrings.length][];
        mirroredCastleMatrix = new Box[boxStrings.length][];

//        Creating castles, both left and right
        for (int i = 0; i<boxStrings.length; i++){
            castleMatrix[i] = new Box[boxStrings[i].length];
            mirroredCastleMatrix[boxStrings.length-1-i] = new Box[boxStrings[i].length];
            for (int j=0; j<boxStrings[i].length; j++){
                xPos1 = startPosX+i*boxWidth;
                yPos = groundLevel + j * boxHeight + boxHeight / 3;
                if (boxStrings[i][j] == gameWinningObjectString){
                    //Setting castle to the left
                    Sprite sprite = new Sprite(new Texture(gameWinningObjectString));
                    GameWinningObject gameWinningObject = createGameWinningObject(xPos1, yPos, boxWidth, boxHeight, (numVerticalBoxes - j) * 10, sprite);
                    player1.setGameWinningObject(gameWinningObject);
                    castleMatrix[i][j] = gameWinningObject;

                    //Mirroring castle to the right
                    Sprite sprite2 = new Sprite(new Texture(gameWinningObjectString));
                    GameWinningObject gameWinningObject2 = createGameWinningObject(screenWidth-xPos1, yPos, boxWidth, boxHeight, (numVerticalBoxes - j) * 10, sprite2);
                    player2.setGameWinningObject(gameWinningObject2);
                    mirroredCastleMatrix[boxStrings.length-1-i][j] = gameWinningObject2;
                } else if (boxStrings[i][j] == normalBoxString){
                    castleMatrix[i][j] = createBox(xPos1, yPos, boxWidth, boxHeight, (numVerticalBoxes - j) * 10, normalBoxString);
                    mirroredCastleMatrix[boxStrings.length-1-i][j]= createBox(screenWidth-xPos1, yPos, boxWidth, boxHeight, (numVerticalBoxes - j) * 10, normalBoxString);
                } else if (boxStrings[i][j] == roofBoxString) {
                    castleMatrix[i][j] = createBox(xPos1, yPos, boxWidth, boxHeight, (numVerticalBoxes - j) * 10, roofBoxString);
                    mirroredCastleMatrix[boxStrings.length-1-i][j]= createBox(screenWidth-xPos1, yPos, boxWidth, boxHeight, (numVerticalBoxes - j) * 10, roofBoxString);
                } else if (boxStrings[i][j] == windowBoxString){
                    castleMatrix[i][j] = createBox(xPos1, yPos, boxWidth, boxHeight, (numVerticalBoxes - j) * 10, windowBoxString);
                    mirroredCastleMatrix[boxStrings.length-1-i][j]= createBox(screenWidth-xPos1, yPos, boxWidth, boxHeight, (numVerticalBoxes - j) * 10, windowBoxString);
                }
            }
        }
    }

//    Keep commented out, may be used later
//    public void makeRandomCastle(float startPosX, float castleWidth, float castleHeight, Player player) {
//        int numHorizontalBoxes = (int) Math.floor((castleWidth / boxWidth));
//        int numVerticalBoxes = (int) Math.floor((castleHeight / boxHeight));
//        Random ran = new Random();
//        //Make the vertical left wall
//        for (int i = 0; i < numHorizontalBoxes; i++) {
//            int extraBoxes = (int)(10*(1-(castleHeight)/screenHeight));
//            int number = ran.nextInt(numVerticalBoxes) + extraBoxes;
//            for (int j = 0; j < number; j++) {
//                if (startPosX + i * boxWidth < screenWidth) {
//                    if (j == 0 && i == numHorizontalBoxes-1){
//                        Sprite sprite = new Sprite(new Texture(gameWinningObjectString));
//                        GameWinningObject gameWinningObject = createGameWinningObject(startPosX + i * boxWidth, groundLevel + j * boxHeight + boxHeight / 3, boxWidth, boxHeight, (numVerticalBoxes - j) * 10, sprite);
//                        player.setGameWinningObject(gameWinningObject);
//
//                    } else if (j == number - 1) {
//                        Box box = createBox(startPosX + i * boxWidth, groundLevel + j * boxHeight + boxHeight / 3, boxWidth, boxHeight, (numVerticalBoxes - j) * 10, new Sprite(new Texture("roof_box.png")));
//                    } else {
//                        Box box = createBox(startPosX + i * boxWidth, groundLevel + j * boxHeight + boxHeight / 3, boxWidth, boxHeight, (numVerticalBoxes - j) * 10, null);
//                    }
//                }
//            }
//        }
//    }


//    Keep commented out, may be used later
//    private void makeMirroredCastle(Player player) {
//        for (int i = mockBoxes.size()- 1; i >= 0; i--) {
//            float boxXpos, boxYpos;
//            if (mockBoxes.get(i) instanceof GameWinningObject){
//                GameWinningObject gameWinningObject = (GameWinningObject) mockBoxes.get(i);
//                Sprite sprite = new Sprite(new Texture(gameWinningObjectString));
//                GameWinningObject mirroredGameWinningObject = createGameWinningObject(gameWinningObject.getBody().getPosition().x, gameWinningObject.getBody().getPosition().y, gameWinningObject.getWidth(), gameWinningObject.getHeight(), gameWinningObject.getDensity(), sprite);
//                player.setGameWinningObject(mirroredGameWinningObject);
//                boxXpos = mirroredGameWinningObject.getBody().getPosition().x;
//                boxYpos = mirroredGameWinningObject.getBody().getPosition().y;
//                moveBox(mirroredGameWinningObject,  screenWidth - boxXpos, boxYpos);
//                mockBoxes.add(mirroredGameWinningObject);
//            } else {
//                Box originalBox = (Box) mockBoxes.get(i);
//                Sprite sprite = new Sprite(new Texture(normalBoxString));
//                Box mirroredBox = createBox(originalBox.getBody().getPosition().x, originalBox.getBody().getPosition().y, originalBox.getWidth(), originalBox.getHeight(), originalBox.getDensity(), sprite);
//                boxXpos = originalBox.getBody().getPosition().x;
//                boxYpos = originalBox.getBody().getPosition().y;
//                moveBox(mirroredBox,  screenWidth - boxXpos, boxYpos);
//                mockBoxes.add(mirroredBox);
//            }
//
//        }
//        drawableList.addAll(mockBoxes);
//    }

    private void createGround() {
        if (ground != null) physicsWorld.destroyBody(ground.getBody());

        float groundHeight = groundLevel;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0.1f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(screenWidth,groundHeight/2);
        fixtureDef.shape = shape;

        Body body;
        body = physicsWorld.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setTransform(0,0,0);

        shape.dispose();

        Sprite groundSprite = new Sprite(new Texture(bottomGroundString));
        groundSprite.setSize(Gdx.graphics.getWidth(), groundHeight/2);
        ground = new Box(body, groundSprite);
        body.setUserData(ground);

        addToRenderList(groundSprite);
        //Doesn't need to be addded to list of objects in the world. No need to update position of sprite
    }



    private Box createBox(float xPos, float yPos, float boxWidth, float boxHeight, float density, String spriteString){
        //creating and setting up the physical shape of the box
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(xPos, yPos);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(boxWidth/2, boxHeight/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0.4f;
        fixtureDef.density = density;
        fixtureDef.restitution = 0.0f;
        fixtureDef.shape = shape;
        Body body;
        body = physicsWorld.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setTransform(xPos, yPos, 0);
        shape.dispose();

        Sprite sprite = new Sprite(new Texture(spriteString));
        sprite.setSize(boxWidth, boxHeight);
        sprite.setOriginCenter();
        Box box = new Box(body, sprite);
        body.setUserData(box);

        addToRenderList(sprite);
        physicalGameObjectList.add(box);

        return box;
    }


    public Body createProjectileBody(float xPos, float yPos, float radius, float friction, float density, float restitution){
        //creating the physical shape of the ball and setting physical attributes
        if (projectile != null) {
            addBodyToDestroy(projectile.getBody().getFixtureList().get(0));
            projectile = null;
        }
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(xPos, yPos);
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = friction;
        fixtureDef.density = density;
        fixtureDef.restitution = restitution;
        fixtureDef.shape = shape;
        Body body;
        body = physicsWorld.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setTransform(xPos, yPos, 0);
        shape.dispose();
        return body;
    }

    private Sprite createProjectileSprite(float xPos, float yPos, float radius) {
        //setting the sprite of the ball and positioning it correctly
        Sprite sprite = new Sprite(new Texture(cannonBallString));
        sprite.setSize(radius * 2, radius * 2);
        sprite.setOriginCenter();
        return sprite;
    }


    public void spawnProjectile() {
        // used for spawning projectile at beginning of game and when the turn switches
        float xPos = activeCannon.getX();
        float yPos = activeCannon.getY();
        float radius = screenWidth/60;
        float friction = 0.5f;
        float density = 20f;
        float restitution = 0.2f;

        Body body = createProjectileBody(xPos, yPos, radius, friction, density, restitution);
        Sprite sprite = createProjectileSprite(xPos, yPos, radius);
        projectile = Projectile.getInstance();
        projectile.init(body, sprite, this);
        body.setUserData(projectile);
        if (!physicalGameObjectList.contains(projectile)){
            physicalGameObjectList.add(projectile);
        }
    }

    private void createOneWayWalls(float x) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0.1f;

        EdgeShape shape = new EdgeShape();
        shape.set(x, groundLevel, x, groundLevel + screenHeight);
        fixtureDef.shape = shape;

        Body body;
        body = physicsWorld.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setTransform(0, 0, 0);

        shape.dispose();


        OneWayWall wall = new OneWayWall(body);
        body.setUserData(wall);
    }

    private GameWinningObject createGameWinningObject(float xPos, float yPos, float boxWidth, float boxHeight, float density, Sprite sprite){
        //creating and setting up the physical shape of the box
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(xPos, yPos);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(boxWidth/2, boxHeight/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 1.0f;
        fixtureDef.density = density;
        fixtureDef.restitution = 0.0f;
        fixtureDef.shape = shape;
        Body body;
        body = physicsWorld.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setTransform(xPos, yPos, 0);
        shape.dispose();
        sprite.setSize(boxWidth, boxHeight);
        sprite.setOriginCenter();
        GameWinningObject gameWinningObject = new GameWinningObject(body, sprite);
        body.setUserData(gameWinningObject);

        physicalGameObjectList.add(gameWinningObject);
        addToRenderList(sprite);
        return gameWinningObject;
    }


    public void update(float dt) {

        activeCannon = activePlayer.getCannon();
        activeCannon.updatePowerBar();
//        projectile.getBody().setAngularVelocity(0);
        activeCannon.update();





        end = System.currentTimeMillis();
        oldTime = time;
        time = (int) Math.floor((end - start) / 1000);

//        Outputs the time of the current time counter
//        if (time > oldTime) {
//            System.out.println(time);
//        }


        // Time limit after shooting
        if ((projectile.isFired() && time > shootingTimeLimit && projectile.getAbsoluteSpeed() < 5) || time > turnLimit) {
//            System.out.println("Switching player turns! Cannon ball has lived for 5 seconds");
//            System.out.println("Current active player: " + activePlayer.getId());
            switchPlayer();
          //  System.out.println("New active player " + activePlayer.getId());
        }

        if (!physicsWorld.isLocked()){
            destroy((ArrayList<Fixture>) getBodiesToDestroy());
        }

        //Check if game is over, if so, the gameOverMenu becomes active
        checkIfGameOver();
//        if (getPlayer1().getGameWinningObject().getHit()) {
////            //TODO, Opponent wins, isHost MUST BE CHANGED WHEN MERGED WITH GPS!!
//            (LocalMulitplayerState) state.gameOver();
//
////                }
////            });
//
//        } else if (getPlayer2().getGameWinningObject().getHit()) {
////            //TODO, You win, isHost MUST BE CHANGED WHEN MERGED WITH GPS!!
//                    state.gameOver();
//
//        }
        physicsWorld.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        //set correct position of bodies that have a body
        for (PhysicalGameObject gameObject: physicalGameObjectList) {
            if (gameObject.getBody() != null){
                float xPos = gameObject.getBody().getPosition().x - gameObject.getSprite().getWidth()/2;
                float yPos = gameObject.getBody().getPosition().y - gameObject.getSprite().getHeight()/2;
                float degrees = (float) Math.toDegrees(gameObject.getBody().getAngle());
                gameObject.getSprite().setPosition(xPos, yPos);
                gameObject.getSprite().setRotation(degrees);
            }
        }

    }

    protected void checkIfGameOver() {
        if (getPlayer1().getGameWinningObject().isHit()) {
//            //TODO, Opponent wins, isHost MUST BE CHANGED WHEN MERGED WITH GPS!!
             state.gameOver();

        } else if (getPlayer2().getGameWinningObject().isHit()) {
//            //TODO, You win, isHost MUST BE CHANGED WHEN MERGED WITH GPS!!
            state.gameOver();

        }
    }


    public void setProjectileVelocity(Vector2 velocity) {
        projectile.getBody().setLinearVelocity(velocity);
    }

    // GameWinningObject is like a box, just with another texture
    // If we want to add functionality to the GameWinningObject - we can use this method and the GameWinningObject-class

//    This method may be used later, keep commented out
//    private void moveBox(PhysicalGameObject box, float xPos, float yPos) {
//        box.getBody().setTransform(xPos, yPos, 0);
//        box.getDrawable().setPosition(xPos, yPos);
//    }


    public World getPhysicsWorld() {
        return physicsWorld;
    }

    public Projectile getProjectile() {
        return projectile;
    }


    public void removeBox(Box b){
        if (physicalGameObjectList.contains(b)){
            physicalGameObjectList.remove(b);
        }
    }

    public void addBodyToDestroy(Fixture b){
        if (!bodiesToDestroy.contains(b)){
//            ((PhysicalGameObject)b.getBody().getUserData().setBodyToNull());
            bodiesToDestroy.add(b);
        }
    }

    public List<Fixture> getBodiesToDestroy(){
        return bodiesToDestroy;
    }

    public void removeAllBodiesToDestroy(){
        bodiesToDestroy = new ArrayList<Fixture>();
    }


    public void destroy(ArrayList<Fixture> bodiesToDestroy) {
        for (Fixture bodyToDestroy : bodiesToDestroy) {
            if (bodyToDestroy.getBody() != null) {
                physicsWorld.destroyBody(bodyToDestroy.getBody());
                removeBox((Box) bodyToDestroy.getUserData());
            }
        }
        removeAllBodiesToDestroy();
    }


    public void switchPlayer(){
        start = System.currentTimeMillis();
        time = 0;
        //Deactivates variables

        activeCannon.deactivate();

        System.out.println("Switching");
        //Changes active player
        if (activePlayer == player1){
            activePlayer = player2;
        } else if (activePlayer == player2){
            activePlayer = player1;
        }
        //Activates variables
        activeCannon = activePlayer.getCannon();
        activeCannon.activate();
        drawer.removeSprite(projectile.getSprite());
        spawnProjectile();
    }


    public void input() {
        activeCannon.progressShootingSequence();
    }



    /* Initial method for firing projectile
    public void fireProjectile() {
        start = System.currentTimeMillis();
        time = 0;
        Vector2 fireVelocity = new Vector2(
                (float)Math.cos(activeCannon.getShootingAngle()*Math.PI/180) * activeCannon.getPower()/3,
                (float)Math.sin(activeCannon.getShootingAngle()*Math.PI/180) * activeCannon.getPower()/3);

        projectile.fire(fireVelocity);
        projectile.setFired(true);
        addToRenderList(projectile.getDrawable());
    }*/

    public void fireProjectile(Vector2 velocity) {
        start = System.currentTimeMillis();
        time = 0;
        projectile.fire(velocity);
        projectile.setFired(true);
        addToRenderList(projectile.getSprite());
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void dispose() {
        physicsWorld.dispose();
    }

    protected void addToRenderList(Sprite sprite) {
//        all sprites that are shown on screen is added here
        drawer.addSprite(sprite);
    }

    protected void removeFromRenderlist(Sprite sprite) {
        drawer.removeSprite(sprite);
    }

    public boolean isOnButton(Button button) {
        if (((CastleCrush.HEIGHT - Gdx.input.getY()) > button.getYpos()) &&
                ((CastleCrush.HEIGHT - Gdx.input.getY()) < (button.getYpos() + button.getBtnHeight())) &&
                (Gdx.input.getX() > button.getXpos()) && (Gdx.input.getX() < (button.getXpos() + button.getBtnWidth()))) {
            return true;
        }
        return false;
    }


}