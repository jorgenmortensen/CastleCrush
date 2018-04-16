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
import models.entities.Projectile;


/**
 * Created by Ludvig on 07/04/2018.
 */

public class MockGameWorld {

    static final float STEP_TIME = 1f / 60f;
    static final int VELOCITY_ITERATIONS = 6;
    static final int POSITION_ITERATIONS = 2;
    static final float SCALE = 0.05f;
    private World physicsWorld;

    private List<Fixture> bodiesToDestroy = new ArrayList<Fixture>();
    private TextureAtlas textureAtlas;
    private PhysicsShapeCache physicsBodies;

    private List<Drawable> mockBoxes;
    private Box ground;
    private List cannons;
    private Projectile projectile;
    private float screenWidth = CastleCrush.WIDTH*SCALE;
    private float screenHeight = CastleCrush.HEIGHT*SCALE;
    private float groundLevel;

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

    public void removeAllBodiesToDestroy(){
        bodiesToDestroy = new ArrayList<Fixture>();
    }


    public MockGameWorld() {
        mockBoxes = new ArrayList<Drawable>();
        cannons = new ArrayList<Cannon>();
        groundLevel = screenHeight/25;

        textureAtlas = new TextureAtlas("sprites.txt");

        Box2D.init();
        physicsBodies = new PhysicsShapeCache("physics.xml");
        physicsWorld = new World(new Vector2(0, -10), true);
        physicsWorld.setContactListener(new GameCollision(this));
        this.generateBodies();
    }



    private void generateBodies() {
        createGround();
        int numVertBoxes = 8, numHorBoxes = 20, castleWidth = 30, castleHeight = 30;
        float startPosX = screenWidth*0.8f;
        makeCastle(numVertBoxes, numHorBoxes, startPosX, castleWidth, castleWidth);
        makeMirroredCastle();
        // createBox(30, 3, screenWidth/50, screenWidth/50);
        //createBox(550,30, 30,30);
        //createBox(600,60, 20,20);
        //createBox(600,80, 20,20);
        //  createBox(Gdx.graphics.getWidth() - 300, 10, 60,40);

        //createProjectile( 30 , 2, screenWidth/50f);
        //System.out.println("Screen height: " + groundLevel);
        //createBox(20, groundLevel, screenWidth/10, screenWidth/10, 1);

        Cannon cannon = new Cannon(Math.round((startPosX+castleWidth+10) * this.getSCALE() + castleWidth*2/3),
                Math.round((CastleCrush.WIDTH / 20) * this.getSCALE()),
                Math.round((CastleCrush.WIDTH / 20) * this.getSCALE()),
                Math.round((CastleCrush.WIDTH / 40) * this.getSCALE()),
                new Sprite(new Texture("cannon.png")),
                new Sprite(new Texture("wheel.png")), null);

        setCannons(new ArrayList<Cannon>(Arrays.asList(cannon, null)));
    }

    public void makeCastle(int numVerticalBoxes, int numHorizontalBoxes, float startPosX, int castleWidth, int castleHeight) {
        float boxWidth = castleWidth/numHorizontalBoxes;
        float boxHeight = castleHeight/numVerticalBoxes;

        Random ran = new Random();
        //Make the vertical left wall
        for (int i = 0; i < numHorizontalBoxes; i++) {
            int number = ran.nextInt(numVerticalBoxes-4 )+4 + 1;
            for (int j = 0; j < number; j++) {
                if (startPosX + i * boxWidth < screenWidth) {
                    Box box = createBox(startPosX + i * boxWidth, groundLevel + j * boxHeight + boxHeight/3, boxWidth, boxHeight, (numVerticalBoxes - j)*10);
                }
            }
        }
    }


    private void  makeMirroredCastle() {
        for (int i = mockBoxes.size()- 1; i >= 0; i--) {
            Box originalBox = (Box) mockBoxes.get(i);
            Box mirroredBox = createBox(originalBox.getBody().getPosition().x, originalBox.getBody().getPosition().y, originalBox.getWidth(), originalBox.getHeight(), originalBox.getDensity());
            float boxXpos = originalBox.getBody().getPosition().x;
            float boxYpos = originalBox.getBody().getPosition().y;
            //float boxWidth = originalBox.getDrawable().getWidth();
            moveBox(mirroredBox,  screenWidth- boxXpos, boxYpos);
            mockBoxes.add(mirroredBox);
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
        //groundSprite.setScale(Gdx.graphics.getWidth(), 1);
        groundSprite.setSize(Gdx.graphics.getWidth(), groundHeight/2);
        // mockBoxes.add(new Box(body, groundSprite));
        ground = new Box(body, groundSprite, 0, 0, 0);
        body.setUserData(ground);
    }



    private Box createBox(float xPos, float yPos, float boxWidth, float boxHeight, float density){

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
        //body.setAngularVelocity(3);
        shape.dispose();

        Sprite sprite = textureAtlas.createSprite("brick1");
        sprite.setSize(boxWidth, boxHeight);
        sprite.setOriginCenter();
        Box box = new Box(body, sprite, boxWidth, boxHeight, density);
        mockBoxes.add(box);
        body.setUserData(box);
        return box;
    }


    public void createProjectile(float xPos, float yPos, float radius, Vector2 velocity){
        //creating the physical shape of the ball and setting physical attributes
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
        Sprite sprite = textureAtlas.createSprite("ball_cannon");
        sprite.setSize(radius*2, radius*2);
        sprite.setOrigin(0, 0);
        sprite.setOriginCenter();

        body.setLinearVelocity(velocity.x, velocity.y);
        projectile = new Projectile(body, new Vector2(xPos, yPos), sprite, radius*2, radius*2, velocity,this);
        body.setUserData(projectile);

    }

//

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

    public void setCannons(List cannons) {
        this.cannons = cannons;
    }

    public void setProjectile(Projectile projectile) {
        this.projectile = projectile;
    }


}
