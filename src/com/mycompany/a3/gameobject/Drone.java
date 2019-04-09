/* -----------------------
 * Robo-Track
 * Author: Arthur Kharit
 * CSC 133, Spring 2019
 * A1Prj
 * -------------------- */

package com.mycompany.a3.gameobject;

import com.codename1.ui.geom.Point;
import com.codename1.ui.Graphics;
import com.mycompany.a3.GameUtility;

/* Movable, non-player-controlled object.
 * 
 * Randomly changes its heading each time it moves by a small number.
 * Has a randomly generated starting location, heading, and speed.
 * Color can't be changed after initialization.
 */
public class Drone extends Movable{
	private int headingIncrement = 0;
	
	public Drone(float x, float y, int[] color, int size) {
		super(x, y, color, size); // initial values passed to Movable
		setHeading(GameUtility.randomInt(0, 360));
		setSpeed(GameUtility.randomInt(10,30));
	}
	
	/* Adds a random number between -5 and 5 to the heading.
	 * Calls the parent move() method. Checks if the new location is valid.
	 * Fixes the location if invalid */
	public void move(int tickRate) {
		int randomNum = GameUtility.randomInt(1, 10);
		if((headingIncrement == 0) && (randomNum == 5)) {
			headingIncrement = GameUtility.randomInt(-15, 15);
			while(headingIncrement == 0)
				headingIncrement = GameUtility.randomInt(-15, 15);
		}
		
		if(headingIncrement < 0) { // turn left
			setHeading(getHeading() - 1);
			headingIncrement++;

		}
		else if(headingIncrement > 0) { // turn right
			setHeading(getHeading() + 1);
			headingIncrement--;
		}
		float oldX = getXCoordinate();
		float oldY = getYCoordinate();
		
		super.move(tickRate);
		
		/* If the new location is invalid, the Drone moves
		 * one by one from its old x/y coordinates to the new(invalid) x/y
		 * coordinates, reducing speed by 1 each time it moves (speed is basically
		 * distance traveled in this situation).
		 * Once it reaches an edge, its heading is reversed with a slight random
		 * variation, and new x/y coordinates are found based on its remainingDistance
		 * and current x/y coordinates, which are at an edge of the map.
		 * 
		 * Loops again if the newly generated values are still out of the map.
		 */
		while (!(getLocation().isValid())) {
			float newX = getXCoordinate();
			float newY = getYCoordinate();
			int distanceRemaining = getSpeed();
			boolean valid = true;
			while (valid) {
				oldX = incrementX(newX, oldX);
				if (oldX >= GameUtility.gameSizeX() || oldX <= 0)
					valid = false;
				
				oldY = incrementY(newY, oldY);
				if (oldY >= GameUtility.gameSizeY() || oldY <= 0)
					valid = false;
				
				distanceRemaining--;
			}
			setHeading(getHeading() + 180 + GameUtility.randomInt(-20, 20)); // reverse direction
			
			double theta = Math.toRadians(90-getHeading());
			newX = oldX + (float)(Math.cos(theta))*distanceRemaining;
			newY = oldY + (float)(Math.sin(theta))*distanceRemaining;
			setLocation(newX, newY);
		}	
	}
	
	/* Increments/decrements oldX based on value of newX, 
	 * moving oldX towards newX */
	private float incrementX(float newX, float oldX) {
		if (newX >= oldX) {
			oldX++;
		}
		else if(newX <= oldX) {
			oldX--;
		}
		return oldX;
	}
	
	/* Increments/decrements oldY based on value of newY, 
	 * moving oldY towards newY */
	private float incrementY(float newY, float oldY) {
		if (newY >= oldY) {
			oldY++;
		}
		else if (newY <= oldY) {
			oldY--;
		}
		return oldY;
	}
	
	public void draw(Graphics g, Point pCmpRelPrnt) {
		int halfSize = this.getSize() / 2;
		int centerX = (int)pCmpRelPrnt.getX() + (int)this.getLocation().getX();
		int centerY = (int)pCmpRelPrnt.getY() + (int)this.getLocation().getY();
		
		// Creates a triangle
		
		// Top of triangle
		int xCorner1 = centerX; 
		int yCorner1 = centerY + halfSize;

		// bottom left corner
		int xCorner2 = centerX - halfSize;
		int yCorner2 = centerY - halfSize;
		
		// bottom right corner
		int xCorner3 = centerX + halfSize;
		int yCorner3 = centerY - halfSize;
		
		int xPoints[] = {xCorner1, xCorner2, xCorner3};
		int yPoints[] = {yCorner1, yCorner2, yCorner3};

		g.setColor(this.getColorAsInt());
		g.drawPolygon(xPoints, yPoints, 3);
	}
	
	public void setColor(int[] color) {}
	
	public String toString() {
		return "Drone: " + super.toString();
	}
}
