package com.mycompany.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.gameobject.Fixed;
import com.mycompany.a3.gameobject.GameObject;
import com.mycompany.a3.gameobject.objectcollection.IIterator;
import com.mycompany.a3.gameworld.GameWorld;

public class CMDPosition extends Command{
	private GameWorld gameWorld;
	
	public CMDPosition(GameWorld gw) {
		super("Position");
		gameWorld = gw;
	}

	/* Goes through list of all objects and finds the first
	 * selected Fixed object. Then sets its toBeMoved value
	 * to true, which will make it move when the next
	 * pointerPressed call occurs in MapView */
	@Override
	public void actionPerformed(ActionEvent ev) {
		if(gameWorld.isPaused()) {
			IIterator allObjects = gameWorld.getObjectCollection().getIterator();
			while(allObjects.hasNext()) {
				GameObject object = (GameObject)allObjects.getNext();
				if(object instanceof Fixed) {
					Fixed fixedObj = (Fixed)object;
					if(fixedObj.isSelected()){
						fixedObj.willBeMoved();
						break;
					}
				}
			}
		}
	}
}
