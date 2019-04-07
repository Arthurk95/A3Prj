package com.mycompany.a3.commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.gameworld.GameWorld;


/* Command to accelerates the player robot by 5 */
public class CMDAccelerate extends Command{

	private GameWorld gameWorld;
	public CMDAccelerate(GameWorld gw) {
		super("Accelerate");
		gameWorld = gw;
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		gameWorld.accelerate();
	}
}
