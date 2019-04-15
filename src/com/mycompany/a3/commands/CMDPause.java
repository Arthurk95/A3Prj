package com.mycompany.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.util.UITimer;
import com.mycompany.a3.Game;
import com.mycompany.a3.GameUtility;
import com.mycompany.a3.gameworld.GameWorld;

public class CMDPause extends Command{
	private Game game;
	private GameWorld gameWorld;
	private UITimer timer;
	private MyButton button;
	private boolean isPaused = false;
	
	public CMDPause(Game g, UITimer t, GameWorld gw) {
		super("Pause");
		timer = t;
		gameWorld = gw;
		game = g;
	}

	/* Pauses/unpauses the game. Tells game to disable
	 * buttons that shouldn't be used during pause state. */
	@Override
	public void actionPerformed(ActionEvent ev) {
		isPaused = !isPaused;
		if(isPaused) {
			button.setText("Play");
			timer.cancel();
			game.pauseGame();
			gameWorld.changeGamePause();
		}
		else {
			button.setText("Pause");
			gameWorld.deselectAll();
			game.unPauseGame();
			gameWorld.changeGamePause();
			timer.schedule(GameUtility.TICK_RATE, true, game);
		}
	}
	
	public void setButton(MyButton b) {
		button = b;
	}
}
