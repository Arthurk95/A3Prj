/* -----------------------
 * Robo-Track
 * Author: Arthur Kharit
 * CSC 133, Spring 2019
 * A1Prj
 * -------------------- */

package com.mycompany.a3.gameobject;

import com.codename1.ui.geom.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
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
		capacity = getSize()/4;
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
	
	/* Draws a filled circle with this EnergyStation's capacity 
	 * written in the middle of it */
	public void draw(Graphics g, Point containerOrigin) {
		int halfSize = getSize()/2;
		int centerX = (int)containerOrigin.getX() + (int)this.getLocation().getX();
		int centerY = (int)containerOrigin.getY() + (int)this.getLocation().getY();
		
		int xCorner = centerX - halfSize;
		int yCorner = centerY - halfSize;

		// draw filled circle
		g.setColor(this.getColorAsInt());
		if(this.isSelected()) {
			g.drawArc(xCorner, yCorner, this.getSize(), this.getSize(), 0, 360);
			g.setColor(ColorUtil.BLACK); // sets color of font
		}
		else {
			g.fillArc(xCorner, yCorner, this.getSize(), this.getSize(), 0, 360);
			g.setColor(ColorUtil.WHITE); // sets color of font
		}
		// draw capacity size in center of circle
		g.setFont(Font.createTrueTypeFont("native:MainRegular").
                derive(halfSize, Font.STYLE_PLAIN));
		g.drawString(String.valueOf(capacity), centerX - (halfSize/2), centerY - (halfSize/2));
	}
	
	public int getCapacity() {return capacity;}
	
	public void collisionWithRobot() {
		if(capacity != 0) {
			capacity = 0;
			fadeColor();
		}
	}
	
	public String toString() {
		return "EnergyStation: " + super.toString() + " capacity=" + capacity;
	}
}
