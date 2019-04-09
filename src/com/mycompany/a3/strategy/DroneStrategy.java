package com.mycompany.a3.strategy;

import com.mycompany.a3.GameUtility;
import com.mycompany.a3.gameobject.NonPlayerRobot;

public class DroneStrategy implements Strategy{
	private NonPlayerRobot nonPlayerRobot;
	private int headingIncrement = 0;
	
	public DroneStrategy(NonPlayerRobot npr) {
		nonPlayerRobot = npr;
	}
	
	/* Randomly steers the NPR up to 15 in either direction, 
	 * steering the robot one degree per tick */
	public void apply() {
		int randomNum = GameUtility.randomInt(1, 4);
		if((headingIncrement == 0) && (randomNum == 2)) {
			headingIncrement = GameUtility.randomInt(-15, 15);
			while(headingIncrement == 0)
				headingIncrement = GameUtility.randomInt(-15, 15);
		}
		
		if(headingIncrement < 0) { // turn left
			if(nonPlayerRobot.canSteerLeft()) {
				nonPlayerRobot.steerLeft(1);
				headingIncrement++;
			}
		}
		else if(headingIncrement > 0) { // turn right
			if(nonPlayerRobot.canSteerRight()) {
				nonPlayerRobot.steerRight(1);
				headingIncrement--;
			}
		}

		if(randomNum == 3)
			nonPlayerRobot.accelerate();
	}
	
	public String toString() {
		return "Drone";
	}
}
