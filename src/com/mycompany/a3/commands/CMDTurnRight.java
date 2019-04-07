package com.mycompany.a3.commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.gameworld.GameWorld;

/* Command to turn the Player right */
public class CMDTurnRight extends Command{

	private GameWorld gameWorld;
	public CMDTurnRight(GameWorld gw) {
		super("TurnRight");
		gameWorld = gw;
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		gameWorld.turnRight();
	}
}
