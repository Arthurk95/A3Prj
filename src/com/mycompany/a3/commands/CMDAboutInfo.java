package com.mycompany.a3.commands;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameUtility;
import com.mycompany.a3.gameworld.GameWorld;

/* Command to display info about me and stuff */
public class CMDAboutInfo extends Command{

	String aboutMsg = "RoboTrack\n"
			+ "by Arthur Kharit\n"
			+ "CSC 133, Spring 2019"
			+ "Version: A2";

	public CMDAboutInfo() {
		super("About");
	}

	
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		TextArea helpTA = new TextArea(aboutMsg);
		Command close = new Command("Close");
		Dialog.show("Help", helpTA, close);
	}
}
