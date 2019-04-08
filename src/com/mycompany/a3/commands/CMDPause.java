package com.mycompany.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.util.UITimer;
import com.mycompany.a3.Game;
import com.mycompany.a3.GameUtility;

public class CMDPause extends Command{
	private Game game;
	private UITimer timer;
	
	public CMDPause(Game g, UITimer t) {
		super("Pause");
		timer = t;
		game = g;
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if(this.getCommandName().equals("Pause")) {
			this.setCommandName("Play");
			timer.cancel();
		}
		else {
			this.setCommandName("Pause");
			timer.schedule(GameUtility.TICK_RATE, true, game);
		}
	}
}
