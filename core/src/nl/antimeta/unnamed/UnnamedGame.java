package nl.antimeta.unnamed;

import com.badlogic.gdx.Game;
import nl.antimeta.unnamed.screens.MainMenu;

public class UnnamedGame extends Game {
	public final static String GAME_NAME = "Unnamed";

	@Override
	public void create () {
		setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
