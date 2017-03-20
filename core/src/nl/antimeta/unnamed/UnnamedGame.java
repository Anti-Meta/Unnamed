package nl.antimeta.unnamed;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import nl.antimeta.unnamed.screens.MainMenuScreen;

public class UnnamedGame extends Game {
	public final static String GAME_NAME = "Unnamed";

	@Override
	public void create () {
		//Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
