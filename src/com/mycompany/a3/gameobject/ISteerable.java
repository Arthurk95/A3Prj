/* -----------------------
 * Robo-Track
 * Author: Arthur Kharit
 * CSC 133, Spring 2019
 * A1Prj
 * -------------------- */

package com.mycompany.a3.gameobject;

/* Interface that allows a GameObject to be steerable */
public interface ISteerable{
	public void steerLeft();
	public void steerLeft(int amount);
	public void steerRight();
	public void steerRight(int amount);
	public void resetSteering();
	public int getSteeringAmount();
	public boolean canSteerLeft();
	public boolean canSteerRight();
}