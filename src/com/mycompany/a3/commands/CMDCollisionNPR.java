package com.mycompany.a3.commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.gameworld.GameWorld;

/* Command to collide player with an NPR */
public class CMDCollisionNPR extends Command{

	private GameWorld gameWorld;
	public CMDCollisionNPR(GameWorld gw) {
		super("Collide NPR");
		gameWorld = gw;
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		gameWorld.collisionWithRobot();
	}
}