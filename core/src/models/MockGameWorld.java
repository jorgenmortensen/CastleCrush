package models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
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
import models.entities.Castle;
import models.entities.Projectile;
import views.game_world.GameWorldDrawer;

//import com.codeandweb.physicseditor.PhysicsShapeCache;

/**
 * Created by Ludvig on 07/04/2018.
 */

public class MockGameWorld {

    static final float STEP_TIME = 1f / 60f;
    static final int VELOCITY_ITERATIONS = 6;
    static final int POSITION_ITERATIONS = 2;
    static final float SCALE = 0.05f;
    World physicsWorld;
    PhysicsShapeCache physicsBodies;
    ArrayList<Body> worldBodies;
    private Projectile prosjektil;


    Sprite mockSprite = new Sprite(new Texture("badlogic.jpg"));


    private List mockBoxes;
    private List cannons;
    private Body ground;

    public World getPhysicsWorld() {
        return physicsWorld;
    }

    public MockGameWorld() {
        mockBoxes = new ArrayList<Box>();
        cannons = new ArrayList<Cannon>();
       // createMockCastle();

        worldBodies = new ArrayList<Body>();
        Box2D.init();
        physicsWorld = new World(new Vector2(0, -10), true);
        //physicsBodies = new PhysicsShapeCache("physics.xml");
        this.generateBodies();



    }

    public void createMockCastle() {
        Castle mockCastle;
       // mockBoxes.add(new Box(10,0, 30,50, mockSprite));
       // mockBoxes.add(new Box(60,0, 30,50, mockSprite));
       // mockBoxes.add(new Box(40,0, 20,20, mockSprite));
       // Cannon cannon = new Cannon(150, 0, 10, 10, mockSprite );
       // cannons.add(cannon);
      //  mockCastle = new Castle(10,11, mockBoxes, cannon);



    }

    private void generateBodies() {
        this.createGround();
        worldBodies.add(createBox(10, 21, 30, 50));
        worldBodies.add(createBox(60,21, 30,50));
        worldBodies.add(createBox(40,21, 20,20));

        createProjectile(200, 200);
    }

    private void createGround() {
        if (ground != null) physicsWorld.destroyBody(ground);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 1;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Gdx.graphics.getWidth(),10);
        fixtureDef.shape = shape;

        ground = physicsWorld.createBody(bodyDef);
        ground.createFixture(fixtureDef);
        ground.setTransform(0,0,0);

        shape.dispose();

        Sprite groundSprite = new Sprite(new Texture("bottom_ground.png"));
        mockBoxes.add(new Box(ground, groundSprite, Gdx.graphics.getWidth(), 20));
    }

   // private Body createBody(String name, float x, float y, float rotation) {
   //     Body body = physicsBodies.createBody(name, physicsWorld, SCALE, SCALE);
    //    body.setTransform(x, y, rotation);

      //  return body;
    //}

    private Body createBox(float xPos, float yPos, int width, int height){
        Body boxBody;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 1;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width,height);
        fixtureDef.shape = shape;

        boxBody = physicsWorld.createBody(bodyDef);
        boxBody.createFixture(fixtureDef);
        boxBody.setTransform(xPos,yPos,0);

        shape.dispose();

        mockBoxes.add(new Box(boxBody, mockSprite, width, height));

        return boxBody;
    }

    private void createProjectile(float xPos, float yPos){
        int radius = 15;
        Body boxBody;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        // Set our body's starting position in the world
        bodyDef.position.set(xPos, yPos);

// Create our body in the world using our body definition
        Body body = physicsWorld.createBody(bodyDef);

// Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(radius);

// Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit

// Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);

// Remember to dispose of any shapes after you're done with them!
// BodyDef and FixtureDef don't need disposing, but shapes do.
        circle.dispose();

        Sprite sprite = new Sprite(new Texture("black_ball.png"));

        worldBodies.add(body);

        prosjektil = new Projectile(body, sprite, radius*2, radius*2);
    }

    public Projectile getProsjektil() {
        return prosjektil;
    }

    public List<Box> getBoxes(){return mockBoxes;}

    public List<Cannon> getCannons(){return cannons;}



}
