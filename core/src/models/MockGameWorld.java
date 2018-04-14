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
import com.codeandweb.physicseditor.PhysicsShapeCache;

import java.util.ArrayList;
import java.util.List;

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
    static final float SCALE = 0.25f;
    private World physicsWorld;

    private TextureAtlas textureAtlas;
    PhysicsShapeCache physicsBodies;

    private final int PTM_RATIO = 10;

    Sprite mockSprite = new Sprite(new Texture("badlogic.jpg"));

    private List<Drawable> mockBoxes;
    private List cannons;
    private Body body;
    private Projectile prosjektil;
    private List<Drawable> worldBodies;



    public MockGameWorld() {
        mockBoxes = new ArrayList<Drawable>();
        cannons = new ArrayList<Cannon>();
        worldBodies = new ArrayList<Drawable>();

        textureAtlas = new TextureAtlas("sprites.txt");

        Box2D.init();
        physicsBodies = new PhysicsShapeCache("physics.xml");
        physicsWorld = new World(new Vector2(0, -10), true);
        this.generateBodies();
    }

    private void generateBodies() {
        createGround();
        createBox(500, 30, 60, 60);
        createBox(550,30, 30,30);
        createBox(600,60, 20,20);
        createBox(600,80, 20,20);
      //  createBox(Gdx.graphics.getWidth() - 300, 10, 60,40);

        createProjectile(20, 20, 50);
    }

    public void createGround() {
        if (body != null) physicsWorld.destroyBody(body);

        int groundHeight = 20;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0.1f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Gdx.graphics.getWidth(),groundHeight/2);
        fixtureDef.shape = shape;

        body = physicsWorld.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setTransform(0,0,0);

        shape.dispose();



        Sprite groundSprite = textureAtlas.createSprite("bottom_ground");
        //groundSprite.setScale(Gdx.graphics.getWidth(), 1);
        groundSprite.setSize(Gdx.graphics.getWidth(), groundHeight/2);
        mockBoxes.add(new Box(body, groundSprite, Gdx.graphics.getWidth(), groundHeight));
    }



    private void createBox(float xPos, float yPos, int width, int height){
        Sprite sprite = textureAtlas.createSprite("brick1");
        body = createBody("brick1", xPos, yPos, 0);
        sprite.setScale(SCALE);
        sprite.setOrigin(0, 0);
        mockBoxes.add(new Box(body, sprite, width*2, height*2));

    }

    private void createProjectile(float xPos, float yPos, int radius){
        Sprite sprite = textureAtlas.createSprite("ball_cannon");
        sprite.setScale(SCALE);
        sprite.setOrigin(0, 0);
        body = createBody("ball_cannon", xPos, yPos, 0);
        body.setLinearVelocity(200.0f, 20.0f);
        body.setAngularVelocity(100.0f/radius);
        prosjektil = new Projectile(body, sprite, radius*2, radius*2, new Vector2(100,100));

    }


    private Body createBody(String name, float x, float y, float rotation) {
        Body body = physicsBodies.createBody(name, physicsWorld, SCALE, SCALE);
        body.setTransform(x, y, rotation);

        return body;
    }



    public World getPhysicsWorld() {
        return physicsWorld;
    }

    public Projectile getProsjektil() {
        return prosjektil;
    }

    public List<Drawable> getBoxes(){return mockBoxes;}

    public List<Cannon> getCannons(){return cannons;}

    public int getPTM_RATIO() {
        return PTM_RATIO;
    }

    public static float getSCALE() {
        return SCALE;
    }
}
