/* -----------------------
 * Robo-Track
 * Author: Arthur Kharit
 * CSC 133, Spring 2019
 * A1Prj
 * -------------------- */

package com.mycompany.a3.gameobject;
import java.util.Random;

import com.codename1.ui.geom.Point;
import com.codename1.ui.Graphics;

/* Fixed GameObject.
 * Stores a certain amount of energy (capacity), equal to its size.
 * 
 * When a player Robot collides with an EnergyStation, the station's
 * capacity is set to 0 and its color is faded.
 */
public class EnergyStation extends Fixed{
	private int capacity;
	
	public EnergyStation(float x, float y, int[] color, int size){
		super(x, y, color, size); // initial values passed to Fixed
		capacity = getSize()*2;
	}

	/* Fades the color of the EnergyStation by half its RGB value 
	 * I had to do this one element at a time because otherwise, doing 
	 * int[] rgb = this.getColor();
	 * would, for some reason, affect every other EnergyStation 
	 * object's color, setting them all to the same value from 
	 * one instance. I have no idea */
	private void fadeColor() {
		int r = getColor()[0];
		int g = getColor()[1];
		int b = getColor()[2];
		
		if (r > 10)
			r = r - (r/2);
		if (g > 10)
			g = g - (g/2);
		if (b > 10)
			b = b - (b/2);

		int[] rgb = {r,g,b};
		this.setColor(rgb);
	}
	
	public void draw(Graphics g, Point pCmpRelPrnt) {
		
	}
	
	public int getCapacity() {return capacity;}
	
	public void collisionWithPlayer() {
		capacity = 0;
		fadeColor();
	}
	
	public String toString() {
		return "EnergyStation: " + super.toString() + " capacity=" + capacity;
	}
}
