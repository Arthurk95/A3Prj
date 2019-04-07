/* -----------------------
 * Robo-Track
 * Author: Arthur Kharit
 * CSC 133, Spring 2019
 * A1Prj
 * -------------------- */

package com.mycompany.a3.gameobject;

import com.codename1.ui.geom.Point;
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
		
	}
	
	public String toString() {
		String parentDesc = super.toString();
		String thisDesc = " seqNum=" + sequenceNumber;
		return "Base: " + parentDesc + thisDesc;
	}
}
