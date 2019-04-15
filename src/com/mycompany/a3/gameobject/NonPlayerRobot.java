package com.mycompany.a3.gameobject;

import com.mycompany.a3.GameUtility;
import com.mycompany.a3.strategy.Strategy;


/* A Robot that can't be controlled directly by the player. 
 * Contains a Strategy which determines its target.
 * Follows the Strategy pattern */
public class NonPlayerRobot extends Robot{
	private Strategy currentStrategy;
	private int energyLevel = GameUtility.NPR_ENERGY_LEVEL;
	
	public NonPlayerRobot(float startingX, float startingY, int[] color, int size) {
		super(startingX, startingY, color, size);
	}
	
	public Strategy getStrategy() {
		return currentStrategy;
	}
	
	public void setStrategy(Strategy s) {
		currentStrategy = s;
	}

	public void invokeStrategy() {
		currentStrategy.apply();
	}

	// NPRs sustain less damage so they can compete longer
	private void collisionWithDrone() { 
		damageTaken(GameUtility.COLLISION_DAMAGE/4); 
		recentlyCollided();
	}
	public void collisionWithRobot()  { 
		damageTaken(GameUtility.COLLISION_DAMAGE/2); 
		recentlyCollided();
	}
	
	public void move(int tickRate) {
		invokeStrategy();
		super.move(tickRate);
		super.resetSteering();
		energyLevel = GameUtility.NPR_ENERGY_LEVEL; // never runs out of energy
	}
	
	public String toString() {
		String parentDesc = super.toString();
		String thisDesc;
		try {
			thisDesc = " strategy: " + getStrategy().toString();
		}
		catch(Exception e) {
			thisDesc = " strategy=" + "none";
		}
		return "NPR: " + parentDesc + thisDesc;
	}
}
