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
import com.codeandweb.physicseditor.PhysicsShapeCache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import models.entities.Box;
import models.entities.Cannon;
import models.entities.Drawable;
import models.entities.GameWinningObject;
import models.entities.OneWayWall;
import models.entities.Player;
import models.entities.Projectile;
import models.states.GameStateManager;


/**
 * Created by Ludvig on 07/04/2018.
 */

public class MockGameWorld {

    static final float STEP_TIME = 1f / 60f;
    static final int VELOCITY_ITERATIONS = 6;
    static final int POSITION_ITERATIONS = 2;
    static final float SCALE = 0.05f;
    private World physicsWorld;
    private float boxWidth;
    private float boxHeight;

    private List<Fixture> bodiesToDestroy = new ArrayList<Fixture>();
    private TextureAtlas textureAtlas;

    private List<Drawable> mockBoxes;
    private Box ground;
    private List cannons;
    private Projectile projectile;
    private float screenWidth = CastleCrush.WIDTH*SCALE;
    private float screenHeight = CastleCrush.HEIGHT*SCALE;
    private float groundLevel;
    private Player player1;
    private Player player2;


    GameStateManager gsm;

    public MockGameWorld(GameStateManager gsm) {
        mockBoxes = new ArrayList<Drawable>();
        this.gsm = gsm;
        cannons = new ArrayList<Cannon>();
        groundLevel = screenHeight/25;
        player1 = new Player("player1");
        player2 = new Player("player2");
        textureAtlas = new TextureAtlas("sprites.txt");
        boxWidth = screenWidth/20;
        boxHeight = screenWidth/20;
        Box2D.init();
        physicsWorld = new World(new Vector2(0, -10), true);
        physicsWorld.setContactListener(new GameCollision(this));
        this.generateBodies();
    }



    private void generateBodies() {
        createGround();
        makeCastle(screenWidth*0.8f, screenWidth*0.2f, screenHeight*0.6f, player2);
        makeMirroredCastle(player1);

        Cannon cannon1 = new Cannon(screenWidth*1/3,
                groundLevel, screenWidth/30, screenHeight/30,
                new Sprite(new Texture("cannon.png")),
                new Sprite(new Texture("wheel.png")), null, true);

        Cannon cannon2 = new Cannon(screenWidth*2/3,
                groundLevel, screenWidth/30, screenHeight/30,
                new Sprite(new Texture("cannon.png")),
                new Sprite(new Texture("wheel.png")), null, false);


        setCannons(new ArrayList<Cannon>(Arrays.asList(cannon1, cannon2)));
        projectile = createProjectile(cannon1.getX(), groundLevel, screenHeight/40);

        createOneWayWalls(cannon1.getX() - projectile.getDrawable().getWidth()/2);
        createOneWayWalls(cannon2.getX() + projectile.getDrawable().getWidth()/2);

    }

    public void makeCastle(float startPosX, float castleWidth, float castleHeight, Player player) {
        int numHorizontalBoxes = (int) Math.floor((castleWidth / boxWidth));
        int numVerticalBoxes = (int) Math.floor((castleHeight / boxHeight));
        Random ran = new Random();
        //Make the vertical left wall
        for (int i = 0; i < numHorizontalBoxes; i++) {
            int extraBoxes = (int)(10*(1-(castleHeight)/screenHeight));
            int number = ran.nextInt(numVerticalBoxes) + extraBoxes;
            for (int j = 0; j < number; j++) {
                if (startPosX + i * boxWidth < screenWidth) {
                    if (j == 0 && i == numHorizontalBoxes-1){
                        GameWinningObject gameWinningObject = createGameWinningObject(startPosX + i * boxWidth, groundLevel + j * boxHeight + boxHeight / 3, boxWidth, boxHeight, (numVerticalBoxes - j) * 10, new Sprite(new Texture("gwo3.png")));
                        player.setGameWinningObject(gameWinningObject);
                    } else if (j == number - 1) {
                        Box box = createBox(startPosX + i * boxWidth, groundLevel + j * boxHeight + boxHeight / 3, boxWidth, boxHeight, (numVerticalBoxes - j) * 10, new Sprite(new Texture("roof_box.png")));
                    } else {
                        Box box = createBox(startPosX + i * boxWidth, groundLevel + j * boxHeight + boxHeight / 3, boxWidth, boxHeight, (numVerticalBoxes - j) * 10, null);
                    }
                }
            }
        }
    }



    private void makeMirroredCastle(Player player) {
        for (int i = mockBoxes.size()- 1; i >= 0; i--) {
            float boxXpos, boxYpos;
            if (mockBoxes.get(i) instanceof GameWinningObject){
                GameWinningObject gameWinningObject = (GameWinningObject) mockBoxes.get(i);
                GameWinningObject mirroredGameWinningObject = createGameWinningObject(gameWinningObject.getBody().getPosition().x, gameWinningObject.getBody().getPosition().y, gameWinningObject.getWidth(), gameWinningObject.getHeight(), gameWinningObject.getDensity(), gameWinningObject.getDrawable());
                player.setGameWinningObject(mirroredGameWinningObject);
                boxXpos = mirroredGameWinningObject.getBody().getPosition().x;
                boxYpos = mirroredGameWinningObject.getBody().getPosition().y;
                moveBox(mirroredGameWinningObject,  screenWidth - boxXpos, boxYpos);
                mockBoxes.add(mirroredGameWinningObject);
            } else {
                Box originalBox = (Box) mockBoxes.get(i);
                Box mirroredBox = createBox(originalBox.getBody().getPosition().x, originalBox.getBody().getPosition().y, originalBox.getWidth(), originalBox.getHeight(), originalBox.getDensity(), originalBox.getDrawable());
                boxXpos = originalBox.getBody().getPosition().x;
                boxYpos = originalBox.getBody().getPosition().y;
                moveBox(mirroredBox,  screenWidth - boxXpos, boxYpos);
                mockBoxes.add(mirroredBox);
            }

        }
    }

    private void createGround() {
        if (ground != null) physicsWorld.destroyBody(ground.getBody());

        float groundHeight = groundLevel;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0.1f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Gdx.graphics.getWidth()*SCALE,groundHeight/2);
        fixtureDef.shape = shape;

        Body body;
        body = physicsWorld.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setTransform(0,0,0);

        shape.dispose();



        Sprite groundSprite = textureAtlas.createSprite("bottom_ground");
        groundSprite.setSize(Gdx.graphics.getWidth(), groundHeight/2);
        ground = new Box(body, groundSprite, 0, 0, 0);
        body.setUserData(ground);
    }



    private Box createBox(float xPos, float yPos, float boxWidth, float boxHeight, float density, Sprite sprite){
        //creating and setting up the physical shape of the box
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(xPos, yPos);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(boxWidth/2, boxHeight/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0.9f;
        fixtureDef.density = density;
        fixtureDef.restitution = 0.0f;
        fixtureDef.shape = shape;
        Body body;
        body = physicsWorld.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setTransform(xPos, yPos, 0);
        shape.dispose();
        Random ran = new Random();
        int number = ran.nextInt(15);
        if (sprite != null){
        } else if(number == 10){
            sprite = new Sprite(new Texture("window_box.png"));
        } else {
            sprite = new Sprite(new Texture("normal_box.png"));
        }
        sprite.setSize(boxWidth, boxHeight);
        sprite.setOriginCenter();
        Box box = new Box(body, sprite, boxWidth, boxHeight, density);
        mockBoxes.add(box);
        body.setUserData(box);
        return box;
    }

    public Projectile createProjectile(float xPos, float yPos, float radius){
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
        fixtureDef.friction = 0.5f;
        fixtureDef.density = 2f;
        fixtureDef.restitution = 0.2f;
        fixtureDef.shape = shape;
        Body body;
        body = physicsWorld.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setTransform(xPos, yPos, 0);
        shape.dispose();

        //setting the sprite of the ball and positioning it correctly
        Sprite sprite = new Sprite(new Texture("ball_cannon.png"));
        sprite.setSize(radius*2, radius*2);
        sprite.setOrigin(0, 0);
        sprite.setOriginCenter();

        Projectile tempProjectile = new Projectile(body, new Vector2(xPos, yPos), sprite, radius*2, radius*2, this);
        body.setUserData(tempProjectile);

        return tempProjectile;

    }

    public void setProjectileVelocity(Vector2 velocity) {
        projectile.getBody().setLinearVelocity(velocity);
    }

    // GameWinningObject is like a box, just with another texture
    // If we want to add functionality to the GameWinningObject - we can use this method and the GameWinningObject-class


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
        GameWinningObject gameWinningObject = new GameWinningObject(body, sprite, boxWidth, boxHeight, density);
        mockBoxes.add(gameWinningObject);
        body.setUserData(gameWinningObject);
        return gameWinningObject;
    }


    private void moveBox(Drawable box, float xPos, float yPos) {
        box.getBody().setTransform(xPos, yPos, 0);
        box.getDrawable().setPosition(xPos, yPos);
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

    public World getPhysicsWorld() {
        return physicsWorld;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public List<Drawable> getBoxes(){return mockBoxes;}

    public List<Cannon> getCannons(){return cannons;}

    public Box getGround() {
        return ground;
    }

    public static float getSCALE() {
        return SCALE;
    }

    public List<Fixture> getBodiesToDestroy(){
        return bodiesToDestroy;
    }

    public void removeBox(Box b){
        if (mockBoxes.contains(b)){
            mockBoxes.remove(b);
        }
    }

    public void addBodyToDestroy(Fixture b){
        if (!bodiesToDestroy.contains(b)){
            bodiesToDestroy.add(b);
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

    public void removeAllBodiesToDestroy(){
        bodiesToDestroy = new ArrayList<Fixture>();
    }


    public float getBoxHeight(){
        return this.boxHeight;
    }

    public float getBoxWidth(){
        return this.boxWidth;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setCannons(List cannons) {
        this.cannons = cannons;
    }

    public void setProjectile(Player player) {
        this.projectile = createProjectile(player.getCannon().getX(), player.getCannon().getY(),screenHeight/40);
    }

    public float getScreenWidth() {
        return screenWidth;
    }
}
