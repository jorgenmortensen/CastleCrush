package com.castlecrush.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import models.MockGameWorld;
import views.Drawer;
import views.game_world.GameWorldDrawer;

public class CastleCrush extends ApplicationAdapter {
	Drawer tegner;
	MockGameWorld world;
	public static int WIDTH;
	public static int HEIGHT;

	@Override
	public void create () {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();

		SpriteBatch batch = new SpriteBatch();
		world = new MockGameWorld();
		tegner = new GameWorldDrawer(batch, world);
	//	img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		tegner.render();
	}
	
	@Override
	public void dispose () {
		tegner.dispose();
	}
}
