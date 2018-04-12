package com.castlecrush.game.desktop;
//import com.google.example.games.basegameutils.GameHelper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.castlecrush.game.CastleCrush;

import googleplayservice.PlayServices;

public class DesktopLauncher implements PlayServices {


	//private GameHelper gameHelper;
	private final static int requestCode = 1;

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = CastleCrush.WIDTH;
		config.height = CastleCrush.HEIGHT;
		new LwjglApplication(new CastleCrush(new PlayServices() {
			@Override
			public void signIn() {

			}

			@Override
			public void signOut() {

			}

			@Override
			public void rateGame() {

			}

			@Override
			public void unlockAchievement() {

			}

			@Override
			public void submitScore(int highScore) {

			}

			@Override
			public void showAchievement() {

			}

			@Override
			public void showScore() {

			}

			@Override
			public boolean isSignedIn() {
				return false;
			}
		}), config);

	//	gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
	//	gameHelper.enableDebugLog(false);

	//	GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener()
	}
	@Override
	public void signIn() {
		try
		{
			/*runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					gameHelper.beginUserInitiatedSignIn();
				}
			});*/
		}
		catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void signOut() {
		try
		{
			/*runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					gameHelper.signOut();
				}
			});*/
		}
		catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void rateGame() {
		String str = "Your PlayStore Link";
		//startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));

	}

	@Override
	public void unlockAchievement() {}

	@Override
	public void submitScore(int highScore) {}

	@Override
	public void showAchievement() {}

	@Override
	public void showScore() {}

	@Override
	public boolean isSignedIn() {
		//return gameHelper.isSignedIn();'
		return true;

	}
}
