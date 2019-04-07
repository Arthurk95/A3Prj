/* -----------------------
 * Robo-Track
 * Author: Arthur Kharit
 * CSC 133, Spring 2019
 * A1Prj
 * -------------------- */

package com.mycompany.a3.gameobject;

/* GameObject whose location cannot be changed */
public abstract class Fixed extends GameObject{
	
	protected Fixed(float x, float y, int[] color, int size) {
		super(x, y, color, size); // initial values passed to GameObject
	}
	
	public void setLocation(float x, float y) {}
	
	public String toString() {
		return super.toString();
	}
}
