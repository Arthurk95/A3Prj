package com.mycompany.a3.commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.gameworld.GameWorld;

/* Command to change the NPR strategies */
public class CMDStrategies extends Command{

	private GameWorld gameWorld;
	public CMDStrategies(GameWorld gw) {
		super("Strategies");
		gameWorld = gw;
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		gameWorld.changeNPRStrategies();
	}
}
