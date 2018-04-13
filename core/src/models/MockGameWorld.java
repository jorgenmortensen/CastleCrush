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
    static final float SCALE = 0.05f;
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
        createBox(100, 10, 60, 60);
        //createBox(60,10, 30,50);
      //  createBox(40,10, 20,20);
       // createBox(Gdx.graphics.getWidth() - 300, 10, 60,40);

        createProjectile(20, 30, 15);
    }

    private void createGround() {
        if (body != null) physicsWorld.destroyBody(body);

        int groundHeight = 20;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0.01f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Gdx.graphics.getWidth()/PTM_RATIO,groundHeight/PTM_RATIO/2);
        fixtureDef.shape = shape;

        body = physicsWorld.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setTransform(0,0,0);

        shape.dispose();



        //Sprite groundSprite = new Sprite(new Texture("bottom_ground.png"));
        Sprite groundSprite = textureAtlas.createSprite("bottom_ground");
        //groundSprite.setScale(Gdx.graphics.getWidth(), 1);
        groundSprite.setSize(Gdx.graphics.getWidth(), groundHeight/2);
        mockBoxes.add(new Box(body, groundSprite, Gdx.graphics.getWidth(), groundHeight));
    }



    private void createBox(float xPos, float yPos, int width, int height){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(xPos/PTM_RATIO, yPos/PTM_RATIO);

        Body body = physicsWorld.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        //*2 because the parameters is the "half width" of the box, from center og the box
        shape.setAsBox(width/PTM_RATIO/2,height/PTM_RATIO/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0.1f;
        fixtureDef.density = 1;
        fixtureDef.restitution = 0.3f;

        Fixture fixture = body.createFixture(fixtureDef);

        body.createFixture(fixtureDef);
        body.setTransform(xPos/PTM_RATIO,yPos/PTM_RATIO,0);

        shape.dispose();
        Sprite sprite = textureAtlas.createSprite("brick1");
        sprite.setSize(width, height);

        body = createBody("brick1", xPos, yPos, 0);

        mockBoxes.add(new Box(body, sprite, width, height));

    }

    private void createProjectile(float xPos, float yPos, int radius){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        // Set our body's starting position in the world
        bodyDef.position.set(xPos/PTM_RATIO, yPos/PTM_RATIO);

// Create our body in the world using our body definition
        Body body = physicsWorld.createBody(bodyDef);

// Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(radius/PTM_RATIO);

// Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0.9f; // Make it bounce a little bit

// Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);

// Remember to dispose of any shapes after you're done with them!
// BodyDef and FixtureDef don't need disposing, but shapes do.
        circle.dispose();

        Sprite sprite = textureAtlas.createSprite("ball_cannon");
        //sprite.setPosition(body.getPosition().x*PTM_RATIO, body.getPosition().y*PTM_RATIO);
        sprite.setSize(radius*2, radius*2);
        //worldBodies.add(body);

        body = createBody("ball_cannon", xPos/PTM_RATIO, yPos/PTM_RATIO, 0);

        prosjektil = new Projectile(body, sprite, radius*2, radius*2, new Vector2(100,100));

        body.setLinearVelocity(10.0f, 0.0f);
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
}
