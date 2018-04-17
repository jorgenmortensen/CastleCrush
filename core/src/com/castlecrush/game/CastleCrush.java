package com.castlecrush.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import models.MockGameWorld;
import models.states.menuStates.PlayMenu;
import views.Drawer;
import models.states.GameStateManager;

import models.states.Splashscreen;


import googleplayservice.PlayServices;
import models.states.menuStates.StartMenuScreen;
import models.states.playStates.OnlineMultiplayerState;

public class CastleCrush extends ApplicationAdapter implements PlayServices.GameListener{


	private static final String TAG = CastleCrush.class.getName();
	public static Music music;
	public static boolean soundOn;
	public static int WIDTH;
	public static int HEIGHT;
	Drawer tegner;
	MockGameWorld world;
	OnlineMultiplayerState onlinemultiplayerstate;


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
		batch = new SpriteBatch();
		System.out.println("HEI3");

		gsm = new GameStateManager();
		onlinemultiplayerstate = new OnlineMultiplayerState(gsm, batch);
		soundOn = true;
		music = Gdx.audio.newMusic(Gdx.files.internal("gameMusic.mp3"));
		music.setLooping(true);
		music.setVolume(0.5f);
		music.play();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		gsm.push(new Splashscreen(gsm));
		System.out.println("HEI4");


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
		System.out.println("onMultiplayerGameStarting");
		try {
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					gsm.set(onlinemultiplayerstate);
				}
			});
		}catch (Exception e) {
			System.out.println("Exception caught");
			e.printStackTrace();
		}
	}

	@Override
	public void goToMain() {
		System.out.println("goToMainScreen");
		gsm.set(new StartMenuScreen(gsm));
	}



}
