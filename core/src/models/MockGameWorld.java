package models;

import com.badlogic.gdx.Gdx;
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

    private TextureAtlas textureAtlas;
    private PhysicsShapeCache physicsBodies;

    private List<Drawable> mockBoxes;
    private Box ground;
    private List cannons;
    private Projectile projectile;
    private float screenWidth = CastleCrush.WIDTH*SCALE;
    private float screenHeight = CastleCrush.HEIGHT*SCALE;
    private float groundLevel;



    public MockGameWorld() {
        mockBoxes = new ArrayList<Drawable>();
        cannons = new ArrayList<Cannon>();
        groundLevel = screenHeight/100;

        textureAtlas = new TextureAtlas("sprites.txt");

        Box2D.init();
        physicsBodies = new PhysicsShapeCache("physics.xml");
        physicsWorld = new World(new Vector2(0, -10), true);
        this.generateBodies();
    }



    private void generateBodies() {
        createGround();
        makeCastle(5, 7, screenWidth*0.8f, 10, 15);
        makeMirroredCastle();
        // createBox(30, 3, screenWidth/50, screenWidth/50);
        //createBox(550,30, 30,30);
        //createBox(600,60, 20,20);
        //createBox(600,80, 20,20);
        //  createBox(Gdx.graphics.getWidth() - 300, 10, 60,40);

        createProjectile( 30 , 2, screenWidth/50f);
    }

    public void makeCastle(int numVerticalBoxes, int numHorizontalBoxes, float startPosX, int castleWidth, int castleHeight) {
        float boxWidth = castleWidth/numVerticalBoxes;
        float boxHeight = castleHeight/numHorizontalBoxes;

        Random ran = new Random();
        //Make the vertical left wall
        for (int i = 0; i < numHorizontalBoxes; i++) {
            int number = ran.nextInt(numVerticalBoxes) + 1;
            for (int j = 0; j < number; j++) {
                Box box = createBox(startPosX + i * boxWidth, groundLevel + j * boxHeight, boxWidth, boxHeight);
            }
        }
    }


    private void  makeMirroredCastle() {
        for (int i = mockBoxes.size()- 1; i >= 0; i--) {
            Box originalBox = (Box) mockBoxes.get(i);
            Box mirroredBox = createBox(originalBox.getBody().getPosition().x, originalBox.getBody().getPosition().y, originalBox.getWidth(), originalBox.getHeight());
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
        ground = new Box(body, groundSprite, 0, 0);
    }



    private Box createBox(float xPos, float yPos, float boxWidth, float boxHeight){

        //creating and setting up the physical shape of the box
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(xPos, yPos);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(boxWidth/2, boxHeight/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0.6f;
        fixtureDef.density = 2f;
        fixtureDef.restitution = 0.1f;
        fixtureDef.shape = shape;
        Body body;
        body = physicsWorld.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setTransform(xPos, yPos, 0);
        body.setAngularVelocity(3);
        shape.dispose();


        Sprite sprite = textureAtlas.createSprite("brick1");
        sprite.setSize(boxWidth, boxHeight);
        sprite.setOriginCenter();
        Box box = new Box(body, sprite, boxWidth, boxHeight);
        mockBoxes.add(box);
        return box;


        //magic number 7, to scale the boxes appropriatly
//        float boxScale = SCALE/7;
//        body = createBody("brick1", xPos, yPos, 0, boxScale );
    }

    private void createProjectile(float xPos, float yPos, float radius){
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

        body.setLinearVelocity(0.0f, 0.0f);
        projectile = new Projectile(body, sprite, radius*2, radius*2);


//        body = physicsWorld.createBody(bodyDef);
//        body.createFixture(fixtureDef);
        //magic number 40, to scale the projectile appropriatly
//        float objectScale = SCALE/40;
//        sprite.setScale(objectScale);
//        body = createBody("ball_cannon", xPos, yPos, 0, objectScale);
    }


//    private Body createBody(String name, float x, float y, float rotation, float scale) {
//        Body body = physicsBodies.createBody(name, physicsWorld, scale, scale);
//        body.setTransform(x, y, rotation);
//
//        return body;
//    }

    private void moveBox(Drawable box, float xPos, float yPos) {
        box.getBody().setTransform(xPos, yPos, 0);
        box.getDrawable().setPosition(xPos, yPos);
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
}