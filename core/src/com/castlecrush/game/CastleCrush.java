package com.castlecrush.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import models.MockGameWorld;
import views.Drawer;
import views.game_world.GameWorldDrawer;
import models.states.GameStateManager;

import states.Splashscreen;


import googleplayservice.PlayServices;
import states.menuStates.StartMenuScreen;
import states.playStates.OnlineMultiplayerState;

public class CastleCrush extends ApplicationAdapter implements PlayServices.GameListener{


	private static final String TAG = CastleCrush.class.getName();
	public static Music music;
	public static boolean soundOn;
	public static int WIDTH;
	public static int HEIGHT;
	Drawer tegner;
	MockGameWorld world;


	private GameStateManager gsm;
	private SpriteBatch batch;

	public static PlayServices playServices;


	public CastleCrush(PlayServices playServices) {
		this.playServices = playServices;
		playServices.setGameListener(this);
	}

	@Override
	public void create () {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();

		SpriteBatch batch = new SpriteBatch();
		world = new MockGameWorld();
		tegner = new GameWorldDrawer(batch, world);
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
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();

		tegner.dispose();
	}

	@Override
	public void onMultiplayerGameStarting() {
        Gdx.app.debug(TAG, "onMultiplayerGameStarting: ");
		System.out.println("onMultiplayerGameStarting");
        gsm.set(new OnlineMultiplayerState(gsm));

	}

	@Override
	public void goToMain() {
		System.out.println("goToMainScreen");
		gsm.set(new StartMenuScreen(gsm));
	}

}
