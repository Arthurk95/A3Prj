/* -----------------------
 * Robo-Track
 * Author: Arthur Kharit
 * CSC 133, Spring 2019
 * A1Prj
 * -------------------- */

package com.mycompany.a3.gameobject;

import com.codename1.ui.geom.Point;
import com.codename1.ui.Graphics;
import com.mycompany.a3.GameUtility;

/* Movable and Steerable player-controlled GameObject.
 * Follows Singleton design pattern - only one instance ever.
 * Implements the ISteerable interface, allowing this
 * GameObject to be steered. That is, the player can tell
 * the Robot to steer the Robot either left or right, which
 * will affect the Robot's heading when it moves accordingly.
 * 
 * A Robot's size cannot be changed after initialization.*/

public class Robot extends Movable implements ISteerable{
	private int steeringDirection = 0;
	private int maximumSpeed = GameUtility.MAX_SPEED;
	private int energyLevel = 100;
	private int energyConsumptionRate = 10;
	private int damageLevel = 0;
	private int MAX_DAMAGE = 100;
	private int lastBaseReached = 1;

	public Robot(float startingX, float startingY, int[] color, int size) {
		super(startingX, startingY, color, size); // initial values passed to Movable
		setSpeed(20);
		setHeading(0);
		damageLevel = 0;
	}
	
	/* Checks to make sure the speed is valid and
	* updates the heading based on steeringDirection
	* before calling the parent move() method. */
	public void move() {
		checkSpeed();
		updateHeading();
		super.move();
		energyLevel = energyLevel - energyConsumptionRate;
	}
	
	/* Makes sure the speed is within a valid range
	 * I.E. 0-maximumSpeed */
	private void checkSpeed() {
		if(getSpeed() > maximumSpeed) {
			setSpeed(maximumSpeed);
		}
		else if(getSpeed() < 0) {
			setSpeed(0);
		}
	}
	
	/* Increases damageLevel by amount, lowers the
	 * maximumSpeed, and fades the Robot's color. */
	public void damageTaken(int amount) {
		damageLevel = damageLevel + amount;
		if (damageLevel > MAX_DAMAGE)
			damageLevel = MAX_DAMAGE;
		else System.out.println("Sustained " + amount + " damage");
		maximumSpeed = GameUtility.MAX_SPEED - damageLevel;
		checkSpeed();
		fadeColor();
	}
	
	/* Fades the color of the robot by 10 when damage has been taken.
	 * See the fadeColor() method comment in EnergyStation to see why 
	 * I did it like this */
	private void fadeColor() {
		int r = getColor()[0];
		int g = getColor()[1];
		int b = getColor()[2];
		
		if (r > 10)
			r = r - 10;
		else r = 0;
		
		if (g > 10)
			g = g - 10;
		else g = 10;
		
		if (b > 10)
			b = b - 10;
		else b = 0;

		int[] rgb = {r,g,b};
		this.setColor(rgb);
	}
	
	public int getDamage() {return damageLevel;}
	public int getEnergy() {return energyLevel;}
	public int getLastBaseReached() {return lastBaseReached;}
	
	public boolean canSteerLeft() {
		if (steeringDirection <= GameUtility.MAX_STEER_LEFT)
			return false;
		else return true;
	}
	
	public boolean canSteerRight() {
		if (steeringDirection >= GameUtility.MAX_STEER_RIGHT)
			return false;
		else return true;
	}

	/* steerLeft/steerRight add -5 or 5, respectively, to
	 * the Robot's steeringDirection, up to -40 or 40.
	 * Returns true if it can still steer, false if not 
	 * 
	 * Amount steered is dictated by GameUtility.STEER_AMOUNT */
	public void steerLeft() {
		if (canSteerLeft()) {
			steeringDirection = steeringDirection - GameUtility.STEER_AMOUNT;
		}
	}
	public void steerRight() {
		if(canSteerRight())
			steeringDirection = steeringDirection + GameUtility.STEER_AMOUNT;
	}
	
	/* Takes in a positive amount to steer left
	 * Max left steer is -40. If the new steeringDirection is 
	 * GREATER than -40, set the steeringDirection to the new one.
	 * Otherwise, the limit has been reached. */
	public void steerLeft(int amount) {
		if(amount > 0) {
			int newSteeringDirection = steeringDirection - amount;
			if (newSteeringDirection > GameUtility.MAX_STEER_LEFT)
				steeringDirection = newSteeringDirection;
		}
	}
	
	/* Same as steerLeft(int amount) but the max is 40 */
	public void steerRight(int amount) {
		if(amount > 0) {
			int newSteeringDirection = steeringDirection + amount;
			if(newSteeringDirection < GameUtility.MAX_STEER_RIGHT) {
				steeringDirection = newSteeringDirection;
			}
		}
	}
	
	public void resetSteering() { steeringDirection = 0; }
	public int getSteeringAmount() { return steeringDirection; }
	/* Increases speed of Robot by 5, up to maximumSpeed */
	public boolean accelerate() {
		if(getSpeed() < maximumSpeed) {
			setSpeed(getSpeed() + 5);
			return true;
		}
		return false;
		
	}
	
	/* Reduces speed by 5,*/
	public void brake() {
		setSpeed(getSpeed() - 5);
		System.out.println("Decelerated by " + 5);
	}
	
	/* Adds the steeringDirection to the Robot's heading,
	 * and makes sure the heading is still between 0 and 360 */
	public void updateHeading() {
		if (steeringDirection > GameUtility.MAX_STEER_RIGHT)
			steeringDirection = GameUtility.MAX_STEER_RIGHT;
		else if (steeringDirection < GameUtility.MAX_STEER_LEFT)
			steeringDirection = GameUtility.MAX_STEER_LEFT;
		
		int newHeading = getHeading() + steeringDirection;
		if (newHeading < 0) {
			newHeading += 360;
		}
		else if (newHeading > 359) {
			newHeading -= 360;
		}
		setHeading(newHeading);
	}
	
	/* Adds stationCapacity to the Robot's energyLevel, up to 100 */
	public void collisionWithEnergyStation(int stationCapacity) {
		energyLevel += stationCapacity;
		if (energyLevel > 100) {
			energyLevel = 100;
		}
	}
	
	/* Checks if baseReached is one ahead of the current
	 * lastBaseReached, increments lastBaseReached if yes */
	public void updateLastBase(int baseReached) {
		if (baseReached == lastBaseReached + 1) {
			lastBaseReached = baseReached;
			System.out.println("Base " + baseReached + " reached");
		}
		else System.out.println("Base " + baseReached + " cannot be reached");
	}
	
	public void draw(Graphics g, Point pCmpRelPrnt) {
	}
	
	public String toString() {
		String parentDesc = super.toString();
		String thisDesc = " energyLevel=" + energyLevel + " damageLevel=" + damageLevel;
		return parentDesc + thisDesc;
	}
}