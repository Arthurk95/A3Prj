package com.mycompany.a3.commands;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameUtility;
import com.mycompany.a3.gameworld.GameWorld;

/* Displays a dialog box asking for the base number to collide
 * with, then calls the function in GameWorld to do this */
public class CMDCollisionBase extends Command{

	private GameWorld gameWorld;
	public CMDCollisionBase(GameWorld gw) {
		super("Collide Base");
		gameWorld = gw;
	}
	@Override
	public void actionPerformed(ActionEvent ev) {
		TextField tf = new TextField();
		Command cOk = new Command("Ok");
		Command oCancel = new Command("Cancel");
		Command cmds[] = new Command[] {cOk, oCancel};
		
		Command c = Dialog.show("Enter base number: ", tf, cmds);
		
		
		if(c == cOk) {
			try {
				String input = tf.getText();
				int num = Integer.parseInt(input);
				if ((num >= 0) && (num <= GameUtility.NUM_BASES))
					gameWorld.collisionWithBase(num);
			}
			catch(Exception e) {
				
			}
		}
	}
}
