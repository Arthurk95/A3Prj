package com.mycompany.a3.commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.gameworld.GameWorld;


/* Command to decelerate the player by 5 */
public class CMDBrake extends Command{

	private GameWorld gameWorld;
	public CMDBrake(GameWorld gw) {
		super("Brake");
		gameWorld = gw;
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		gameWorld.brake();
	}
}
