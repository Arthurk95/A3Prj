package com.mycompany.a3.commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.gameworld.GameWorld;

/* Command to move time forward */
public class CMDTick extends Command{

	private GameWorld gameWorld;
	public CMDTick(GameWorld gw) {
		super("Tick");
		gameWorld = gw;
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		gameWorld.clockTick();
	}
}
