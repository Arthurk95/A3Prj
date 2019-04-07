/* -----------------------
 * Robo-Track
 * Author: Arthur Kharit
 * CSC 133, Spring 2019
 * A1Prj
 * -------------------- */

package com.mycompany.a3.gameobject;

import java.util.Random;

import com.mycompany.a3.GameUtility;

/* Utility class to store and handle a GameObject's location.
 * Stores x,y coordinates as floats */
public class Location {
	private float locationX;
	private float locationY;
	
	public void set(float x, float y){
		locationX = x;
		locationY = y;
	}
	
	public float getX() {
		return locationX;
	}
	
	public float getY() {
		return locationY;
	}
	
	/* Checks if the current locationX and locationY coordinates 
	 * are valid (within the size of the world) */
	public boolean isValid() {
		if ((locationX > GameUtility.gameSizeX()) || (locationX < 0) || 
				(locationY > GameUtility.gameSizeY()) || (locationY < 0)) {
			return false;
		}
		else 
			return true;
	}
	
	/* Returns true if the passed coords are within the game board, false otherwise */
	public boolean isValid(float x, float y) {
		if ((x > GameUtility.gameSizeX()) || (x < 0) || 
				(y > GameUtility.gameSizeY()) || (y < 0)) {
			return false;
		}
		else 
			return true;
	}
	
	/* If the passed X coordinate is outside of the map,
	 * this method will set it to the nearest limit. 
	 * That is, 0 or GameObjUtil.GAMEWORLD_SIZE_X */
	public float makeXValid(float x) {
		if (x > GameUtility.gameSizeX()) {
			return GameUtility.gameSizeX();
		}
		else if (x < 0) {
			return 0;
		}
		else return x;
	}
	
	/* If the passed Y coordinate is outside of the map,
	 * this method will set it to the nearest limit.
	 * That is, 0 or GameObjUtil.GAMEWORLD_SIZE_Y */
	public float makeYValid(float y) {
		if (y > GameUtility.gameSizeY()) {
			return GameUtility.gameSizeY();
		}
		else if (y < 0) {
			return 0;
		}
		else return y;
	}
	
	public String toString() {
		return "loc=(" + Math.round(locationX*10.0)/10.0 + "," 
				+ Math.round(locationY*10.0)/10.0 + ")";
	}
}
