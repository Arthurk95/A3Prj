package com.mycompany.a3.gameworld;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.ui.plaf.Border;
import com.mycompany.a3.gameobject.Fixed;
import com.mycompany.a3.gameobject.GameObject;
import com.mycompany.a3.gameobject.objectcollection.IIterator;

/* An empty container that Observes GameWorld and 
 * prints all of the objects to the console */
public class MapView extends Container implements Observer{
	private GameWorld gw;
	public MapView() {
	}
	public void update(Observable o, Object arg) {
		gw = (GameWorld)o;
		// red border
		this.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.rgb(255, 0, 0)));
		System.out.println(gw.outputGameMap());
		drawWorld();
	}

	private void drawWorld() {
		repaint();
	}
	
	/* Paints every object in the GameWorld */
	public void paint(Graphics g) {
		super.paint(g);
		IIterator allObjects = gw.getObjectCollection().getIterator();
		Point thisOrigin = new Point(getX(), getY());
		while(allObjects.hasNext()) {
			GameObject o = (GameObject)allObjects.getNext();
			o.draw(g, thisOrigin);
		}
	}
	
	/* Overrides the Container method to perform an action when 
	 * the mouse is clicked on the container */
	@Override
	public void pointerPressed(int x, int y) {
		// only allows stuff to be selected when game is paused
		if(gw.isPaused()) {
			x = x - getX();
			y = y - getY() - getParent().getAbsoluteY();
			Point mousePointer = new Point(x, y);
			IIterator allObjects = gw.getObjectCollection().getIterator();
			while(allObjects.hasNext()) {
				GameObject object = (GameObject)allObjects.getNext();
				if(object instanceof Fixed) {
					Fixed fixedObj = (Fixed)object;
					
					/* Moves object if the Position command was
					 * pressed before clicking on the MapView */
					if(fixedObj.isToBeMoved() && fixedObj.isSelected()) {
						System.out.println(fixedObj.toString());
						fixedObj.setLocation(x, y);
						fixedObj.setSelected(false);
						break;
					}
					
					else if(fixedObj.contains(mousePointer)) {
						gw.deselectAll();
						fixedObj.setSelected(true);
						break;
					}
					else fixedObj.setSelected(false);					
				}
			}
			drawWorld();
		}
		
	}
}
