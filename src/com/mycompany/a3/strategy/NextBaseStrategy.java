package com.mycompany.a3.strategy;

import com.codename1.util.MathUtil;
import com.mycompany.a3.GameUtility;
import com.mycompany.a3.gameobject.Base;
import com.mycompany.a3.gameobject.GameObject;
import com.mycompany.a3.gameobject.Location;
import com.mycompany.a3.gameobject.NonPlayerRobot;
import com.mycompany.a3.gameobject.objectcollection.GameObjectCollection;
import com.mycompany.a3.gameobject.objectcollection.IIterator;

/* Contains an instance of the GameObjectCollection,
 * which is used to find the NPR's next base. Meaning,
 * lastBaseReached + 1 */
public class NextBaseStrategy implements Strategy{
	private int lastBaseReached = 0;
	private int targetBase;
	private int headingToTarget;
	private NonPlayerRobot nonPlayerRobot;
	private GameObjectCollection allObjects;
	private Location baseLoc;

	public NextBaseStrategy(NonPlayerRobot npr, GameObjectCollection objects) {
		nonPlayerRobot = npr;
		allObjects = objects;
	}
	
	/* Calculates the heading the NPR needs to reach the target */
	public void apply() {
		baseLoc = getNewBaseLocation();
		float nprX = nonPlayerRobot.getXCoordinate();
		float nprY = nonPlayerRobot.getYCoordinate();
		float baseX = baseLoc.getX();
		float baseY = baseLoc.getY();
		
		// angleFromPole = arctan(a, b)
		float a = Math.abs(nonPlayerRobot.getXCoordinate() - baseLoc.getX());
		float b = Math.abs(nonPlayerRobot.getYCoordinate() - baseLoc.getY());
		double thetaRads = MathUtil.atan2((double)a, (double)b);
		int angleFromPole = (int)Math.toDegrees(thetaRads);
		
		/* Gets the quadrant of the Base relative to the NPR and returns
		 * the target heading.
		 * See GameUtility.findQuadrant comment for detailed explanation */
		headingToTarget = GameUtility.findTargetHeading(angleFromPole, nprX, nprY, baseX, baseY);
		
		calculateNewTarget(headingToTarget);
		
		if(GameUtility.randomInt(0, 5) == 3) // 1/5 chance to accelerate
			nonPlayerRobot.accelerate();
	}
	
	// goes through the list to find the next base for the passed NPR
	private Location getNewBaseLocation() {
		boolean foundBase = false;
		Location loc = null;
		IIterator tempObjList = allObjects.getIterator();
		while ((lastBaseReached + 1) < nonPlayerRobot.getLastBaseReached()) // future proof
			lastBaseReached++;
		
		// goes through to find base with correct sequence order
		if (lastBaseReached != nonPlayerRobot.getLastBaseReached()) {
			while(!(foundBase) && (tempObjList.hasNext())) {
				GameObject currentObject = (GameObject)tempObjList.getNext();
				if(currentObject instanceof Base) {
					Base b = (Base)currentObject;
					if (b.getSequenceOrder() == (nonPlayerRobot.getLastBaseReached() + 1)) {
						loc = b.getLocation();
						targetBase = b.getSequenceOrder();
						foundBase = true;
					}
				}
			} // while()
			return loc;
		} // if
		return baseLoc;
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
	
	// Returns a heading value between 0 and 360
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
		return "NextBase(" + targetBase + ")";
	}
	
	
}
