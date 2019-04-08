/* -----------------------
 * Robo-Track
 * Author: Arthur Kharit
 * CSC 133, Spring 2019
 * A1Prj
 * -------------------- */

package com.mycompany.a3.gameobject;

import java.util.ArrayList;

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
	private ArrayList<GameObject> isCollidingWith = new ArrayList<>();

	public GameObject() {}
	
	/* All children of this class pass their necessary data to
	 * this one constructor, where everything is set */
	protected GameObject(float x, float y, int[] colr, int sz) {
		location.set(x, y);
		setSize(sz);
		this.color = colr;
		setColorCU();
	}
	
	public boolean collidesWith(GameObject otherObject) { 
		int halfSizeThis = this.getSize()/2;
		int halfSizeOther = otherObject.getSize()/2;
		boolean isCollision = false;
		
		int rightX1 = (int)(this.getXCoordinate() + halfSizeThis);
		int leftX1 = (int)(this.getXCoordinate() - halfSizeThis);
		int topY1 = (int)(this.getYCoordinate() + halfSizeThis);
		int bottomY1 = (int)(this.getYCoordinate() - halfSizeThis);
			
		int rightX2 = (int)(otherObject.getXCoordinate() + halfSizeOther);
		int leftX2 = (int)(otherObject.getXCoordinate() - halfSizeOther);
		int topY2 = (int)(otherObject.getYCoordinate() + halfSizeOther);
		int bottomY2 = (int)(otherObject.getYCoordinate() - halfSizeOther);
		if(this instanceof PlayerRobot) {
			halfSizeThis=halfSizeThis + 1 - 1;
		}
		if((rightX1 < leftX2) || (leftX1 > rightX2)) {} // no left/right overlap
		else if((topY2 < bottomY1) || (topY1 < bottomY2)) {} // no top/bottom overlap
		else isCollision = true; // something overlapped
		
		// if there's a collision, and it's not in the list yet, add it to the list
		if(isCollision && !(isCollidingWith.contains(otherObject))) {
			isCollidingWith.add(otherObject);
		}
				
		// if there is no collision, but it's in the list, remove it from list
		else if(!isCollision && (isCollidingWith.contains(otherObject))){
			isCollidingWith.remove(otherObject);
		}
				
		// if it's colliding, but it's already in the list, ignore it
		else if(isCollision && isCollidingWith.contains(otherObject)) {
			isCollision = false;
		}
				
		
		return isCollision;
	}
	
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
