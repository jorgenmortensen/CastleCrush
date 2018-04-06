package com.castlecrush.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import states.GameStateManager;
import states.menuStates.PlayMenu;
import states.menuStates.StartMenuScreen;

public class CastleCrush extends ApplicationAdapter {

	public static Music music;
	public static boolean soundOn;
	public static int WIDTH;
	public static int HEIGHT;

	public static final String TITLE = "Castle Crush";

	private SpriteBatch batch;
	private GameStateManager gsm;
	@Override
	public void create () {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		soundOn = true;
		music = Gdx.audio.newMusic(Gdx.files.internal("gameMusic.mp3"));
		music.setLooping(true);
		music.setVolume(0.5f);
		music.play();
		Gdx.gl.glClearColor(1, 1, 0, 1);
		gsm.push(new StartMenuScreen(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
