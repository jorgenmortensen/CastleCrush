package com.castlecrush.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import models.states.GameStateManager;

import models.states.Splashscreen;
import models.states.menuStates.PlayMenu;
import googleServices.PlayServices;
import models.states.menuStates.StartMenuScreen;
import models.states.playStates.OnlineMultiplayerState;

public class CastleCrush extends ApplicationAdapter implements PlayServices.GameListener{


	private static final String TAG = CastleCrush.class.getName();
	public static Music music;
	public static boolean soundOn;
	public static boolean soundEffectsOn;
	public static int WIDTH;
	public static int HEIGHT;
	OnlineMultiplayerState onlinemultiplayerstate;
	StartMenuScreen startmenuscreen;

	public static PlayServices playServices;


	public CastleCrush(PlayServices playServices) {
		this.playServices = playServices;
		playServices.setGameListener(this);
	}

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
		soundEffectsOn = true;
		music = Gdx.audio.newMusic(Gdx.files.internal("gameMusic.mp3"));
		music.setLooping(true);
		music.setVolume(0.5f);
		music.play();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		onlinemultiplayerstate = new OnlineMultiplayerState(gsm, batch);
		startmenuscreen = new StartMenuScreen(gsm);
		try {
			gsm.set(new Splashscreen(gsm));
		} catch(Exception e) {
			e.printStackTrace();
		}
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
		try {
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					gsm.set(startmenuscreen);
				}
			});
		}catch (Exception e) {
			System.out.println("Exception caught");
			e.printStackTrace();
		}
	}



}
