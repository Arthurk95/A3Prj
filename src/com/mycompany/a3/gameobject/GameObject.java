/* -----------------------
 * Robo-Track
 * Author: Arthur Kharit
 * CSC 133, Spring 2019
 * A1Prj
 * -------------------- */

package com.mycompany.a3.gameobject;

import com.codename1.charts.util.ColorUtil;


/* Parent object for all other objects in this game.
 * Each object is represented by a square on the map, with the center of the
 * square being the center of the object.
 * By default, GameObjects can have their color and location changed by anyone.
 * 
 * size 	- size value unique to each GameObject. represents length of one side of square
 * 			  EX. size = 20; 20x20 = 400; object takes up 400 pixels on the map
 * color 	- each object type is assigned a 3-index RGB [r,g,b] int color
 * 
 * location - Location object that stores the float x and y coordinates of the GameObject
 */
public abstract class GameObject implements IDrawable, ICollider{
	private int size;
	private int[] color = new int[3]; // RGB color
	private ColorUtil colorCU = new ColorUtil();
	private Location location = new Location();

	public GameObject() {}
	
	/* All children of this class pass their necessary data to
	 * this one constructor, where everything is set */
	protected GameObject(float x, float y, int[] colr, int sz) {
		location.set(x, y);
		setSize(sz);
		this.color = colr;
		setColorCU();
	}
	
	public boolean collidesWith(GameObject otherObject) { return false; }
	
	public void handleCollision(GameObject otherObject) {}
	
	// Sets the RGB colors of the ColorUtil object
	private void setColorCU() {colorCU.rgb(color[0], color[1], color[2]);}
	public int getColorAsInt() { return colorCU.rgb(color[0], color[1], color[2]); }
	
	public void setColor(int[] newColor) {
		this.color = newColor;
		setColorCU();
	}
	
	public int[] getColor() 	{	return this.color;	}
	private void setSize(int s) {	size = s;	}
	public int getSize()	   {	return size;}
	
	
	public void setLocation(float x, float y) {location.set(x, y);}
	public float getXCoordinate() {	return location.getX();	}
	public float getYCoordinate() {	return location.getY();	}
	public Location getLocation() {	return location;		}

	public String toString() {
		String thisDesc = location.toString() + " color=[" + this.color[0] + ","
				+ this.color[1] + "," + this.color[2] + "] size=" + size;
		return thisDesc;
	}
}
