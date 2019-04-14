/* -----------------------
 * Robo-Track
 * Author: Arthur Kharit
 * CSC 133, Spring 2019
 * A1Prj
 * -------------------- */

package com.mycompany.a3.gameobject;

import com.mycompany.a3.GameUtility;

/* PlayerRobot object that extends Robot.
 * Implements the Singleton pattern.
 * Exactly the same as a Robot but can only have a single
 * instance in the entire program.
 * Also prints when is turned, accel'd, or brakes. */

public class PlayerRobot extends Robot{
	private static PlayerRobot playerRobot;
	
	private PlayerRobot() {
		super(GameUtility.startX(), GameUtility.startY(), 
				GameUtility.ROBOT_COLOR, GameUtility.ROBOT_SIZE); // initial values passed to Movable
	}
	
	public static void resetRobot() {
		playerRobot = null;
	}
	
	public void steerLeft() {
		if (super.canSteerLeft()) {
			super.steerLeft();
			System.out.println("Steered " + GameUtility.STEER_AMOUNT + " degrees left");
		}
		else System.out.println("You can't steer left anymore!");
	}
	
	public void steerRight() {
		if (super.canSteerRight()) {
			super.steerRight();
			System.out.println("Steered " + GameUtility.STEER_AMOUNT + " degrees Right");
		}
		else System.out.println("You can't steer right anymore!");
	}
	
	public boolean accelerate() {
		if(super.accelerate())
			System.out.println("Accelerated by " + 5);
		else System.out.println("Can't accelerate more!");
		return true;
	}
	
	public static PlayerRobot getPlayerRobot() {
		if (playerRobot == null)
			playerRobot = new PlayerRobot();
		return playerRobot;
	}
	
	public String toString() {
		return "Player: " + super.toString() + " steeringDirection=" + getSteeringAmount();
		
	}
}
