/* -----------------------
 * Robo-Track
 * Author: Arthur Kharit
 * CSC 133, Spring 2019
 * A1Prj
 * -------------------- */

package com.mycompany.a3.gameobject;

import com.codename1.ui.geom.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

/* Fixed GameObject.
 * 
 * sequenceNumber is its position on the GameWorld's track. 
 * It's the goal of the player Robot to reach the final Base.
 */
public class Base extends Fixed{
	private int sequenceNumber;
	
	public Base(float x, float y, int seq, int[] color, int size) {
		super(x, y, color, size); // initial values passed to Fixed
		sequenceNumber = seq;
	}
	
	public int getSequenceOrder() {
		return sequenceNumber;
	}
	
	public void setColor(int[] color) {}
	
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
		g.fillPolygon(xPoints, yPoints, 3);
		g.setColor(ColorUtil.WHITE);
		g.drawString(String.valueOf(sequenceNumber), centerX - (halfSize/6), centerY - (halfSize/2));
	}
	
	public String toString() {
		String parentDesc = super.toString();
		String thisDesc = " seqNum=" + sequenceNumber;
		return "Base: " + parentDesc + thisDesc;
	}
}
