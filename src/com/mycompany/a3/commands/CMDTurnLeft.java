package com.mycompany.a3.commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.gameworld.GameWorld;

/* Command to turn the player Left */
public class CMDTurnLeft extends Command{

	private GameWorld gameWorld;
	public CMDTurnLeft(GameWorld gw) {
		super("TurnLeft");
		gameWorld = gw;
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		gameWorld.turnLeft();
	}
}
