package com.mycompany.a3.strategy;

import com.codename1.util.MathUtil;
import com.mycompany.a3.GameUtility;
import com.mycompany.a3.gameobject.Base;
import com.mycompany.a3.gameobject.GameObject;
import com.mycompany.a3.gameobject.Location;
import com.mycompany.a3.gameobject.NonPlayerRobot;
import com.mycompany.a3.objectcollection.GameObjectCollection;
import com.mycompany.a3.objectcollection.IIterator;

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
	 * Fixes over-steer. 
	 * Turns it 5 at a time, to future proof for when actual time 
	 * is implemented. Now, the NPR will turn left/right 40 at a time per
	 * tick until it reaches the targetHeading */
	public void calculateNewTarget(int targetHeading) {
		headingToTarget = makeValidHeadingValue(targetHeading);
		int currentHeading = nonPlayerRobot.getHeading();
		
		int difference = findDifference(headingToTarget, currentHeading);
		
		if (currentHeading == targetHeading) {} // do nothing
		else {
			if (makeValidHeadingValue((currentHeading + 180)) > headingToTarget) {
				while(nonPlayerRobot.canSteerRight() && (difference > 0)) {
					nonPlayerRobot.steerRight();
					difference -= GameUtility.STEER_AMOUNT;
				}
				if (difference < 0) { // account for oversteer
					nonPlayerRobot.steerLeft(Math.abs(difference));
				}
			}
			else {
				while(nonPlayerRobot.canSteerLeft() && (difference > 0)) {
					nonPlayerRobot.steerLeft();
					difference -= GameUtility.STEER_AMOUNT;
				}
				if (difference < 0) { // account for oversteer
					nonPlayerRobot.steerRight(Math.abs(difference));
				}
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
	
	/* Returns the difference between the two passed headings
	 * based on the smallest distance between the two angles 
	 * IE. 	if targetHeading = 340 and currentHeading = 10 
	 * 		returns 30, not 330 */
	private int findDifference(int targetHeading, int currentHeading) {
		int difference = 0;
		int iterator;

		// decides if it's closer to add or subtract 1 from currentHeading
		if (targetHeading > makeValidHeadingValue(currentHeading + 180))
			iterator = -1;
		else iterator = 1;
		
		// increments currentHeading until it's equal to targetHeading
		while(currentHeading != targetHeading) {
			currentHeading = currentHeading + iterator;
			difference++;
			if ((currentHeading == targetHeading) || (difference > 359))
				break;
			if (currentHeading < 0)
				currentHeading = 359;
			else if (currentHeading > 360) {
				currentHeading = 1;
			}
		}
		return difference;
	}
	
	public String toString() {
		return "NextBase(" + targetBase + ")";
	}
	
	
}
