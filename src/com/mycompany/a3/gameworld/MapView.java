package com.mycompany.a3.gameworld;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.ui.plaf.Border;
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
}
