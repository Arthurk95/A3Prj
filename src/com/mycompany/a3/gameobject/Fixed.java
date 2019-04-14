/* -----------------------
 * Robo-Track
 * Author: Arthur Kharit
 * CSC 133, Spring 2019
 * A1Prj
 * -------------------- */

package com.mycompany.a3.gameobject;

import com.codename1.ui.geom.Point;

/* GameObject whose location cannot be changed */
public abstract class Fixed extends GameObject implements ISelectable{
	private boolean isSelected = false;
	
	protected Fixed(float x, float y, int[] color, int size) {
		super(x, y, color, size); // initial values passed to GameObject
	}
	
	public void setLocation(float x, float y) {}
	
	public void setSelected(boolean s) {isSelected = s;}
	public boolean isSelected() {return isSelected;}
	
	/* mousePointer is where the mouse was clicked
	 * component is the component in which is was clicked (probably MapView) */
	public boolean contains(Point mousePointer, Point component) {
		float mouseX = mousePointer.getX();
		float mouseY = mousePointer.getY();
		
		// The getSize()/2 turns the x/y into the upper left corner 
		// of the base, instead of the center
		float baseX = component.getX() - (getSize()/2);
		float baseY = component.getY() - (getSize()/2);
		
		if((mouseX >= baseX) && (mouseX <= (baseX + getSize()) 
				&& (mouseY >= baseY) && (mouseY <= (baseY + getSize()))))
			return true;
		else return false;
	}
	
	public String toString() {
		return super.toString();
	}
}
