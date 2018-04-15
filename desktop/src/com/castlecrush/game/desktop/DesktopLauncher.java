package com.castlecrush.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.castlecrush.game.CastleCrush;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = CastleCrush.WIDTH;
		config.height = CastleCrush.HEIGHT;
		new LwjglApplication(new CastleCrush(), config);
	}
}
