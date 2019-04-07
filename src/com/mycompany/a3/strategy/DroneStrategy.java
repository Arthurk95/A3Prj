package com.mycompany.a3.strategy;

import com.mycompany.a3.GameUtility;
import com.mycompany.a3.gameobject.NonPlayerRobot;

public class DroneStrategy implements Strategy{
	private NonPlayerRobot nonPlayerRobot;
	
	public DroneStrategy(NonPlayerRobot npr) {
		nonPlayerRobot = npr;
	}
	
	/* Randomly steers the NPR up to 15 in either direction */
	public void apply() {
		int headingIncrement = GameUtility.randomInt(-15, 15);
		
		while(headingIncrement == 0)
			headingIncrement = GameUtility.randomInt(-15, 15);
		if(headingIncrement < 0) {
			while(nonPlayerRobot.canSteerLeft() && (headingIncrement > 0)) {
				nonPlayerRobot.steerLeft();
				headingIncrement -= GameUtility.STEER_AMOUNT;
			}
			if(headingIncrement < 0) {
				nonPlayerRobot.steerRight(Math.abs(headingIncrement));
			}
		}
		else {
			while(nonPlayerRobot.canSteerRight() && (headingIncrement > 0)) {
				nonPlayerRobot.steerRight();
				headingIncrement -= GameUtility.STEER_AMOUNT;
			}
			if(headingIncrement < 0) {
				nonPlayerRobot.steerLeft(Math.abs(headingIncrement));
			}
		}
		
		if(GameUtility.randomInt(0, 5) == 3) // 1/5 chance to accelerate
			nonPlayerRobot.accelerate();
	}
	
	public String toString() {
		return "Drone";
	}
}
