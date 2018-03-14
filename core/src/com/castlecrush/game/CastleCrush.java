package com.castlecrush.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import views.Drawer;
import views.game_world.GameWorldDrawer;

public class CastleCrush extends ApplicationAdapter {
	Drawer tegner;
	
	@Override
	public void create () {

		tegner = new GameWorldDrawer();
	//	batch = new SpriteBatch();
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
