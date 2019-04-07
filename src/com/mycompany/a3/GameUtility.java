/* -----------------------
 * Robo-Track
 * Author: Arthur Kharit
 * CSC 133, Spring 2019
 * A1Prj
 * -------------------- */

package com.mycompany.a3;

import java.util.Random;

/* Utility class meant to store any constant variables
 * along with any methods that are used throughout
 * the various GameObjects, such as random number generation.
 * Contains an inner class which stores the size of the
 * game map, that can only be changed once. */
public class GameUtility {
	public static final int INITIAL_ENERGY_STATIONS = 5;
	public static final int MAX_SPEED = 100;
	public static final int NUM_BASES = 5;
	public static final int STEER_AMOUNT = 5;
	public static final int MAX_STEER_LEFT = -40;
	public static final int MAX_STEER_RIGHT = 40;
	public static final int MAX_STATION_SIZE = 30;
	public static final int MIN_STATION_SIZE = 5;
	public static final int COLLISION_DAMAGE = 10;
	public static final int NUM_DRONES = randomInt(2, 5);
	public static final int BASE_SIZE = 20;
	public static final float START_X = 800;
	public static final float START_Y = 40;
	public static final int ROBOT_SIZE = 30;
	public static final int[] BASE_COLOR = {000,255,000}; // GREEN
	public static final int[] ROBOT_COLOR = {000,000,255}; // BLUE
	public static final int[] DRONE_COLOR = {255,000,000}; // RED
	public static final int[] ENERGYSTATION_COLOR = {000,255,255}; // TURQUOISE I THINK
	public static final int NPR_ENERGY_LEVEL = 200;

	public static int gameSizeX() { return GameSize.x; }
	public static int gameSizeY() { return GameSize.y; }
	
	// Can ONLY be changed if GameSize.x/GameSize.y haven't been changed
	public static void setGameSize(int x, int y) {
		if (GameSize.x == -1 || GameSize.y == -1) {
			new GameSize(x, y);
		}
	}
	
	// generates a random number between min and max
	public static int randomInt(int min, int max) {
		Random rand = new Random();
		int randomInt = rand.nextInt(max);
		while (randomInt < min) {
			randomInt = rand.nextInt(max);
		}
		return randomInt;
	}
	
	public static float randomFloat(float min, float max) {
		Random rand = new Random();
		float randomFloat = rand.nextFloat() * max;
		while (randomFloat < min) {
			randomFloat = rand.nextFloat() * max;
		}
		return randomFloat;
	}
	
	public static float randomX() {
		return randomFloat((float)0, (float)GameSize.x);
	}
	
	public static float randomY() {
		return randomFloat((float)0, (float)GameSize.y);
	}
	
	/* B = arctan(a, b), where a is abs(x1 - targetX) and b is abs(y1 - targetY)
	 * (x1,y1) is a GameObject in the GameWorld that wants to reach the
	 * target (targetX,targetY), which is another GameObject in the GameWorld.
	 * With the above formula, B is the angle between 
	 * the nearest Y pole (0 or 180 degrees) and the line formed between 
	 * the target and (x1,y1). 
	 * So, if this was a regular polar graph, (x1,y1) would be the center of the 
	 * graph, with a line going between it and the target. Since the graph 
	 * is split into quadrants, we can see which quadrant the target is in 
	 * in relation to the center. 
	 * EX:  if the target's X is larger than the center's, and its Y is less than
	 * 		the center's, then it is in the second quadrant, and angleFromPole 
	 * 		is the angle between the line between the two and the 180 degree line
	 * 		(-Y plane). This means the heading between the center and the 
	 *  	target is (180 - angleFromPole) */
	public static int findTargetHeading(int angleFromPole, float x1, 
			float y1, float targetX, float targetY) {
		
		int headingToTarget;
		// x1,y1 IS CENTER OF GRAPH
		if((targetX > x1) && (targetY < y1)) { // quadrant 2
			headingToTarget = 180 - angleFromPole;
		}
		else if((targetX < x1) && (targetY < y1)) { // quadrant 3
			headingToTarget = angleFromPole + 180;
		}
		else if ((targetX < x1) && (targetY > y1)) { // quadrant 4
			headingToTarget = 360 - angleFromPole;
		}
		else headingToTarget = angleFromPole; // quadrant 1
		return headingToTarget;
	}
	
	// Private inner class to store the X/Y game size
	// Can only be changed ONCE
	private static class GameSize{
		private static int x = -1;
		private static int y = -1;
		
		public GameSize(int x, int y) {
			GameSize.x = x;
			GameSize.y = y;
		}
	}
	
}
