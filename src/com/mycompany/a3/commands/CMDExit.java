package com.mycompany.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.gameworld.GameWorld;

/* Command to exit the program. Prompts user to confirm */
public class CMDExit extends Command {
	
	public CMDExit() {
		super("Exit");
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		Boolean close = Dialog.show("Confirm quit", "Are you sure?", "Yes", "Cancel");
		
		if (close) {
			Display.getInstance().exitApplication();
		}
	}
}
