package com.castlecrush.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import models.MockGameWorld;
import models.states.GameStateManager;
import models.states.Splashscreen;
import models.states.playStates.SinglePlayerState;
import views.Drawer;
import views.game_world.GameWorldDrawer;

import models.states.GameStateManager;

import models.states.playStates.SinglePlayerState;


public class CastleCrush extends ApplicationAdapter {

    Drawer tegner;
	MockGameWorld world;

	public static Music music;
	public static boolean soundOn;
	public static int WIDTH;
	public static int HEIGHT;

	public static final String TITLE = "Castle Crush";

	private SpriteBatch batch;
	private GameStateManager gsm;
	private OrthographicCamera fullscreen;

	@Override
	public void create () {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		batch = new SpriteBatch();
		//tegner = new GameWorldDrawer(batch, world);
		//	img = new Texture("badlogic.jpg");
		gsm = new GameStateManager();
		soundOn = true;
		music = Gdx.audio.newMusic(Gdx.files.internal("gameMusic.mp3"));
		music.setLooping(true);
		music.setVolume(0.5f);
		music.play();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		gsm.push(new Splashscreen(gsm));
	}

	@Override
	public void render () {
		//tegner.render();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		tegner.dispose();
		batch.dispose();
	}
}
