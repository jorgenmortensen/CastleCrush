package models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
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
    private Body body;
    private Projectile projectile;





    public MockGameWorld() {
        mockBoxes = new ArrayList<Drawable>();
        cannons = new ArrayList<Cannon>();

        textureAtlas = new TextureAtlas("sprites.txt");

        Box2D.init();
        physicsBodies = new PhysicsShapeCache("physics.xml");
        physicsWorld = new World(new Vector2(0, -10), true);
        physicsWorld.setContactListener(new GameCollision());
        this.generateBodies();
    }



    public void makeCastle(int verticalBoxes, int horizontalBoxes, int startPosX, int castleWidth,
                           int startPosY, int castleHeight) {
        int endPosX =  castleWidth+ startPosX;
        int endPosY =  castleHeight + startPosY;

        Random ran = new Random();

        int startX = startPosX;
        //Make the vertical left wall
        for (int i = 0; i < 5; i++) {
            int number = ran.nextInt(7) + 1;
            for (int j = 0; j < number; j++) {
                createBox(startX, (float) (startPosY + j * 0.5 * castleHeight / verticalBoxes));
            }
            startX = startX + 1;
        }
    }


    private void generateBodies() {
        createGround();
        makeCastle(5, 2, 40, 5, 3, 20);

        //createBox(500, 30, 60, 60);
        //createBox(550,30, 30,30);
        //createBox(600,60, 20,20);
        //createBox(600,80, 20,20);
        //  createBox(Gdx.graphics.getWidth() - 300, 10, 60,40);

        createProjectile(2, 2, 50);
    }

    private void createGround() {
        if (body != null) physicsWorld.destroyBody(body);

        float groundHeight = 20*SCALE;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0.1f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Gdx.graphics.getWidth()*SCALE,groundHeight/2);
        fixtureDef.shape = shape;

        body = physicsWorld.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setTransform(0,0,0);

        shape.dispose();



        Sprite groundSprite = textureAtlas.createSprite("bottom_ground");
        //groundSprite.setScale(Gdx.graphics.getWidth(), 1);
        groundSprite.setSize(Gdx.graphics.getWidth(), groundHeight/2);
        // mockBoxes.add(new Box(body, groundSprite));
        ground = new Box(body, groundSprite);
        body.setUserData(ground);
    }



    private void createBox(float xPos, float yPos){
        Sprite sprite = textureAtlas.createSprite("brick1");
        //magic number 7, to scale the boxes appropriatly
        float boxScale = SCALE/7;
        body = createBody("brick1", xPos, yPos, 0, boxScale );
        sprite.setScale(boxScale);
        sprite.setOrigin(0, 0);
        Box box = new Box(body, sprite);
        mockBoxes.add(box);
        body.setUserData(box);
        System.out.println("Box type: " + body.getType());
    }

    private void createProjectile(float xPos, float yPos, int radius){
        Sprite sprite = textureAtlas.createSprite("ball_cannon");
        //magic number 40, to scale the projectile appropriatly
        float objectScale = SCALE/40;
        sprite.setScale(objectScale);
        sprite.setOrigin(0, 0);
        body = createBody("ball_cannon", xPos, yPos, 0, objectScale);
        //body.setUserData("Projectile");
        body.setLinearVelocity(20.0f, 10.0f);
        projectile = new Projectile(body, sprite, radius*2, radius*2, new Vector2(100,100));
        body.setUserData(projectile);
        System.out.println("Projectile type: "+body.getType());
    }


    private Body createBody(String name, float x, float y, float rotation, float scale) {
        Body body = physicsBodies.createBody(name, physicsWorld, scale, scale);
        body.setTransform(x, y, rotation);

        return body;
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
