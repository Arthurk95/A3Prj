package com.mycompany.a3.commands;

import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.SideMenuBar;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.gameworld.GameWorld;

/* Command to flick the sound on or off */
public class CMDSound extends Command {
	private GameWorld gameWorld;
	
	public CMDSound(GameWorld gw) {
		super("Sound");
		gameWorld = gw;
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if(((CheckBox)ev.getComponent()).isSelected()){
			gameWorld.flickSound(true);
		}
		else gameWorld.flickSound(false);
		SideMenuBar.closeCurrentMenu();
	}
}
