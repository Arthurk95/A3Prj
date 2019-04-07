package com.mycompany.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.gameworld.GameWorld;

/* Command to display the list of key bindings in the game */
public class CMDHelp extends Command {
	
	String helpMsg = "a - Accelerate\n"
			+ "b - Brake\n"
			+ "l - Left Turn\n"
			+ "r - Right turn\n"
			+ "e - Collide w/ station\n"
			+ "g - Collide w/ drone \n"
			+ "t - Clock tick";
	
	public CMDHelp() {
		super("Help");
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		TextArea helpTA = new TextArea(helpMsg);
		Command close = new Command("Close");
		Dialog.show("Help", helpTA, close);
	}
}
