package com.mycompany.a3.strategy;

import com.codename1.util.MathUtil;
import com.mycompany.a3.GameUtility;
import com.mycompany.a3.gameobject.NonPlayerRobot;
import com.mycompany.a3.gameobject.PlayerRobot;


/* Strategy to attack the PlayerRobot.
 * IE. go straight towards the player. */
public class AttackStrategy implements Strategy{
	private NonPlayerRobot nonPlayerRobot;
	private int headingToTarget;
	
	public AttackStrategy(NonPlayerRobot npr) {
		nonPlayerRobot = npr;
	}
	
	/* Calculates the heading the NPR needs to reach the target */
	public void apply() {
		float nprX = nonPlayerRobot.getXCoordinate();
		float nprY = nonPlayerRobot.getYCoordinate();
		float robotX = PlayerRobot.getPlayerRobot().getLocation().getX();
		float robotY = PlayerRobot.getPlayerRobot().getLocation().getY();
		
		float a = Math.abs(nonPlayerRobot.getXCoordinate() - robotX);
		float b = Math.abs(nonPlayerRobot.getYCoordinate() - robotY);
		double thetaRads = MathUtil.atan2((double)a, (double)b);
		int angleFromPole = (int)Math.toDegrees(thetaRads);
		
		/* Gets the quadrant of the Player relative to the NPR and returns
		 * the target heading.
		 * See GameUtility.findQuadrant comment for detailed explanation */
		headingToTarget = GameUtility.findTargetHeading(angleFromPole, nprX, nprY, robotX, robotY);

		calculateNewTarget(headingToTarget);
		
		if(GameUtility.randomInt(0, 5) == 3) // 1/5 chance to accelerate
			nonPlayerRobot.accelerate();
	}
	
	/* Turns the NPR left or right, depending on the targetHeading, 
	 * until either it can no longer turn or it has reached the new heading. 
	 * Fixes over-steer */
	public void calculateNewTarget(int targetHeading) {
		headingToTarget = makeValidHeadingValue(targetHeading);
		int currentHeading = nonPlayerRobot.getHeading();
		
		int difference = headingToTarget - currentHeading;
		
		/* (-180 < difference < 0) OR (difference > 180) 
		 * Robot needs to turn LEFT to get to target */
		if(((difference < 0) && (difference > -180)) || (difference > 180)) {
			
			if(difference > 180) {
				difference = makeValidHeadingValue(difference + 180);
			}
			else difference = Math.abs(difference);
			
			while(nonPlayerRobot.canSteerLeft() && (difference > 0)) {
				nonPlayerRobot.steerLeft();
				difference -= GameUtility.STEER_AMOUNT;
			}
			if (difference < 0) { // account for over-steer
				nonPlayerRobot.steerRight(Math.abs(difference));
			}
		}
		/* (0 < difference < 180) OR (difference < -180) 
		 * Robot needs to turn RIGHT to get to target */
		else {
			if(difference < -180)
				difference = difference + 360;
			while(nonPlayerRobot.canSteerRight() && (difference > 0)) {
				nonPlayerRobot.steerRight();
				difference -= GameUtility.STEER_AMOUNT;
			}
			if (difference < 0) { // account for over-steer
				nonPlayerRobot.steerLeft(Math.abs(difference));
			}
		}
	}
	
	private int makeValidHeadingValue(int value) {
		if (value < 0) {
			value += 360;
		}
		else if (value > 359) {
			value -= 360;
		}
		return value;
	}

	public String toString() {
		return "AttackPlayer";
	}
}
