/* -----------------------
 * Robo-Track
 * Author: Arthur Kharit
 * CSC 133, Spring 2019
 * A1Prj
 * -------------------- */

package com.mycompany.a3.gameobject;

import com.mycompany.a3.GameUtility;

/* A GameObject whose location can be changed.
 * 
 * The location of a Movable object is changed based on
 * its speed and heading. */
public abstract class Movable extends GameObject{
	public Movable() {}
	
	public Movable(float x, float y, int[] color, int size) {
		super(x, y, color, size); // initial values passed to GameObject
	}
	
	private int heading; // degree value 0-360 for direction. 0 is north, 90 is east, etc
	private float speed; // 0-GameObjectUtil.MAX_SPEED
	
	/* Tells the object to move by determining its new coordinates
	 * based on its heading and speed.
	 * Makes sure it stays within the GameWorld */
	public void move(int tickRate) {
		float newX, newY;
		double theta = Math.toRadians(90-heading);
		float numX = GameUtility.gameSizeX() / tickRate;
		float numY = GameUtility.gameSizeY() / tickRate;
		newX = getXCoordinate() + ((float)Math.cos(theta)*getSpeed() / (numX/3));
		newY = getYCoordinate() + ((float)Math.sin(theta)*getSpeed() / (numY/3));
		setLocation(newX, newY);
	}
	
	public int getHeading() { return heading; }
	
	/* Sets the heading of the Movable object
	 * Makes sure the heading is between 0 and 359 */
	public void setHeading(int newHeading) { 
		heading = newHeading;
		if (heading < 0) {
			heading += 360;
		}
		else if (newHeading > 359) {
			heading -= 360;
		}
	}
	
	/* Sets the speed, makes sure it's within GameObjectUtil.MAX_SPEED and 0 */
	public void setSpeed(float newSpeed) {
		speed = newSpeed;
		if (speed > GameUtility.MAX_SPEED)
			speed = GameUtility.MAX_SPEED;
		else if (speed < 0)
			speed = 0;
	}
	public float getSpeed() {return speed;}
	
	public String toString() {
		String parentDesc = super.toString();
		String thisDesc = " heading=" + heading + " speed=" + speed;
		return parentDesc + thisDesc;
	}
}
