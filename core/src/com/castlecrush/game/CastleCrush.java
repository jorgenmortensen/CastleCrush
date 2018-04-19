package com.castlecrush.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import models.states.GameStateManager;
import models.states.Splashscreen;
import models.states.menuStates.PlayMenu;
import models.states.playStates.SinglePlayerState;

public class CastleCrush extends ApplicationAdapter {

	public static Music music;
	public static boolean soundOn;
	public static int WIDTH;
	public static int HEIGHT;
	public static float xCoordBg1;
	public static float xCoordBg2;
	public static final int BACKGROUND_MOVE_SPEED = -50;

	public static final String TITLE = "Castle Crush";

	private SpriteBatch batch;
	private GameStateManager gsm;

	@Override
	public void create () {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		xCoordBg1 = WIDTH;
		xCoordBg2 = 0;
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		soundOn = true;
		music = Gdx.audio.newMusic(Gdx.files.internal("gameMusic.mp3"));
		music.setLooping(true);
		music.setVolume(0.5f);
		music.play();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		gsm.push(new PlayMenu(gsm)/*new Splashscreen(gsm)*/);
		//world = new GameWorld();
		//drawer = new GameWorldDrawer(batch, world);
	//	img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
		//drawer.render();
	}
	
	@Override
	public void dispose () {
	}

}
