/* -----------------------
 * Robo-Track
 * Author: Arthur Kharit
 * CSC 133, Spring 2019
 * A1Prj
 * -------------------- */

package com.mycompany.a3.gameworld;


import java.util.Observable;

import com.mycompany.a3.GameUtility;
import com.mycompany.a3.gameobject.*;
import com.mycompany.a3.objectcollection.GameObjectCollection;
import com.mycompany.a3.objectcollection.IIterator;
import com.mycompany.a3.strategy.*;

/* This is where the GameWorld is stored. All interactions
* between different objects are controlled within this class.
* 
* A GameObjectCollection is stored in this class, which is where
* all of the GameObjects are contained.
* 
* The entire map is initialized at the beginning of the game from
* the init() method, and again if the player loses a life.
* 
* There are a wide variety of constants in this game, such as
* the number of Bases, EnergyStation size, the colors of the 
* objects, etc., all of which are stored within the GameObjectUtil
* class, making it easy to edit any of those values.
* 
* Tracks game time and the number of lives the player has.
*/
public class GameWorld extends Observable {
	private int gameClock = 0;
	private boolean isSoundOn = false;
	private int livesRemaining = 3;
	private GameObjectCollection objectsCollection = new GameObjectCollection();
	
	@Override
	public void notifyObservers() {
		setChanged();
		super.notifyObservers();
	}
	
	/* Initializes the GameWorld by creating all of the
	 * objects within the game.
	 * 
	 * The PLAYER Robot is ALWAYS at index 0 in the allObjects list */
	public void init() {
		objectsCollection = new GameObjectCollection();
		createBases();
		createEnergyStations();	
		createDrones();
		createNPRs();
		createRobot();
		changeNPRStrategies();
		notifyObservers();
	}
	
	public boolean isSoundOn() {return isSoundOn;}
	
	public void flickSound(boolean newState) {
		isSoundOn = newState;
		notifyObservers();
	}
	
	public int getLivesRemaining() { return livesRemaining; }
	public int getGameTime() { return gameClock; }
	public GameObjectCollection getObjectCollection() { return objectsCollection; }
	
	/* Outputs the player Robot's information */
	public void displayGamePlayerState() {
		System.out.println("lives=" + livesRemaining + " clock=" + gameClock +
				" lastBaseReached=" + PlayerRobot.getPlayerRobot().getLastBaseReached() 
				+ " energy=" + PlayerRobot.getPlayerRobot().getEnergy() +
				" damage=" + PlayerRobot.getPlayerRobot().getDamage());
	}
	
	/* Outputs every object in the GameWorld */
	public String outputGameMap() {
		String gameMap = "";
		IIterator allObjects = objectsCollection.getIterator();
		while (allObjects.hasNext()) {
			gameMap += (allObjects.getNext().toString() + "\n");
		}
		return gameMap;
	}
	
	/* Creates a single Robot and adds it to the allObjects list */
	private void createRobot() {
		PlayerRobot.resetRobot();
		objectsCollection.add(PlayerRobot.getPlayerRobot());
		notifyObservers();
	}
	
	/* Creates 3 NonPlayerRobots around the Robot's starting location */
	private void createNPRs() {
		int size = GameUtility.ROBOT_SIZE;
		createNPR(GameUtility.startX() + (size*2), GameUtility.startY() + (size), GameUtility.NPR_COLOR, size);
		createNPR(GameUtility.startX() - (size*2), GameUtility.startY() + (size*4), GameUtility.NPR_COLOR, size);
		createNPR(GameUtility.startX() - (size*3), GameUtility.startY(), GameUtility.NPR_COLOR, size);
	}
	
	private void createNPR(float startingX, float startingY, int[] color, int size) {
		objectsCollection.add(new NonPlayerRobot(startingX, startingY, color, size));
		notifyObservers();
	}
	
	/* Creates 5 bases with locations chosen by me 
	 * Map zig zags to the top of the gameworld, then goes straight down
	 * for the final base. */
	private void createBases() {
		createBase(GameUtility.startX(), GameUtility.startY(), 1);
		createBase(GameUtility.gameSizeX() / 8, GameUtility.gameSizeY() / 3, 2);
		createBase(GameUtility.gameSizeX() - 400, GameUtility.gameSizeY() / 2, 3);
		createBase(GameUtility.gameSizeX()/2, (int)(GameUtility.gameSizeY() * 0.8), 4);
		createBase(GameUtility.gameSizeX()/2, GameUtility.gameSizeY()/10, 5);
	}
	
	/* Creates a single Base and adds it to the allObjects list */
	private void createBase(float x, float y, int seq) {
		objectsCollection.add(new Base(x, y, seq, GameUtility.BASE_COLOR, GameUtility.BASE_SIZE));
		notifyObservers();
	}
	
	/* Creates a random number of Drones */
	private void createDrones() {
		for (int i = 0; i < GameUtility.NUM_DRONES; i++) {
			createDrone();
		}
	}
	
	/* Creates a single Drone and adds it to the allObjects list */
	private void createDrone() {
		objectsCollection.add(new Drone(
				GameUtility.randomX(), GameUtility.randomY(), 
				GameUtility.DRONE_COLOR, GameUtility.randomInt(30, 50)));
		notifyObservers();
	}
	
	/* Creates INITIAL_ENERGY_STATIONS EnergyStations in the world */
	private void createEnergyStations() {
		for (int i = 0; i < GameUtility.INITIAL_ENERGY_STATIONS; i++) {
			createEnergyStation();
		}
	}
	
	/* Creates a single, randomly located EnergyStation and adds it 
	 * to the allObjects list.
	 * Passes a random value between MIN_STATION_SIZE and MAX_STATION_SIZE
	 * to determine station size/capacity */
	private void createEnergyStation() {
		objectsCollection.add(new EnergyStation(
				GameUtility.randomX(), GameUtility.randomY(), GameUtility.ENERGYSTATION_COLOR,
				GameUtility.randomInt(GameUtility.MIN_STATION_SIZE, GameUtility.MAX_STATION_SIZE)));
		notifyObservers();
	}
	
	/* Method that progresses the game world. 
	 * Calls each movable object (robot and drone) to move, progress gameClock,
	 * and checks the state of the game for a win/loss */
	public void clockTick() {
		IIterator allObjects = objectsCollection.getIterator();
		while(allObjects.hasNext()) {
			GameObject currentObject = (GameObject)allObjects.getNext();
			if (currentObject instanceof Movable) {
				Movable m = (Movable)currentObject;
				m.move(GameUtility.TICK_RATE); // Calls the Movable CHILD's move() method
				notifyObservers();
			}
			if (checkIfLifeLost())
				break;	
		}
		checkForCollisions();
		gameClock++;
		notifyObservers();
		checkIfGameOver();
	}
	
	/* It only matters if a Robot is colliding with something, so this goes
	 * through the list of objects and only checks each Robot against every
	 * other object in the list. If the other object is an EnergyStation, 
	 * creates a new one if the current one is not empty */
	private void checkForCollisions() {
		IIterator allObjects = objectsCollection.getIterator();
		while (allObjects.hasNext()) {
			GameObject currentObject = (GameObject)allObjects.getNext();
			if(currentObject instanceof Robot) {
				Robot currentRobot = (Robot)currentObject;
				IIterator otherObjects = objectsCollection.getIterator();
				
				while (otherObjects.hasNext()) {
					GameObject currentOtherObject = (GameObject)otherObjects.getNext();
					if (currentRobot == currentOtherObject) {} // do nothing
					else if(currentRobot.collidesWith(currentOtherObject) &&
								currentOtherObject.collidesWith(currentRobot)) {
						if ((currentOtherObject instanceof EnergyStation) && 
								(((EnergyStation)currentOtherObject).getCapacity() != 0)) {
							createEnergyStation();
						}
						currentRobot.handleCollision(currentOtherObject);
						notifyObservers();
					}
				}
			}
		}
	}
	
	/* Returns true if player Robot has take too much damage
	 * or run out of energy */
	private boolean checkIfLifeLost() {
		if (playerAtMaxDamage() || playerOutOfEnergy()) {
			playerLostLife();
			return true;
		}
		else return false;
	}
	
	/* Game is over when either livesRemaining < 0,
	 * the player Robot reached the final Base,
	 * or an NPR has reached the final base */
	private void checkIfGameOver() {
		if (PlayerRobot.getPlayerRobot().getLastBaseReached() == GameUtility.NUM_BASES) {
			System.out.println("Last base reached, good job!");
			System.out.println("Total time: " + gameClock);
			System.exit(0);
		}
		if (livesRemaining < 0) {
			System.out.println("Game over, you failed!");
			System.exit(0);
		}
		
		/* Checks NPRs to see if any have reached the last base */
		IIterator allObjects = objectsCollection.getIterator();
		while(allObjects.hasNext()) {
			GameObject currentObject = (GameObject)allObjects.getNext();
			if(currentObject instanceof NonPlayerRobot) {
				NonPlayerRobot npr = (NonPlayerRobot)currentObject;
				if(npr.getLastBaseReached() == GameUtility.NUM_BASES) {
					System.out.println("An NPR reached the last base, you lose!");
					System.exit(0);
				}
			}
		}
	}

	/* Returns true if player has sustained max damage */
	public boolean playerAtMaxDamage() {
		if (PlayerRobot.getPlayerRobot().getDamage() >= 100) {
			System.out.println("\nSustained too much damage! Lost 1 life");
			return true;
		}
		else return false;
	}
	
	/* Returns true if player has 0 or less Energy remaining */
	public boolean playerOutOfEnergy() {
		if (PlayerRobot.getPlayerRobot().getEnergy() <= 0) {
			System.out.println("\nRan out of energy! Lost 1 life");
			return true;
		}
		else return false;
	}
	
	/* Decrements lives remaining and checks if player is out of lives. 
	 * Re-initializes game if not out of lives */
	private void playerLostLife() {
		livesRemaining--;
		notifyObservers();
		System.out.println("LIVES REMAINING: " + livesRemaining);
		checkIfGameOver();
		System.out.println("\nGame re-initialized!");
		init();
	}
	
	/* Raises speed of Robot by 5 */
	public void accelerate() {
		PlayerRobot.getPlayerRobot().accelerate();
		notifyObservers();
	}
	
	/* Lowers speed of Robot by 5 */
	public void brake() {
		PlayerRobot.getPlayerRobot().brake();
		notifyObservers();
	}
	
	/* Subtracts 5 from Robot's steeringDirection */
	public void turnLeft() {
		PlayerRobot.getPlayerRobot().steerLeft();
		notifyObservers();
	}
	
	/* Adds 5 to Robot's steeringDirection */
	public void turnRight() {
		PlayerRobot.getPlayerRobot().steerRight();
		notifyObservers();
	}
	
	/* Player Robot collided with Drone, so takes half the damage of colliding 
	 * with another Robot */
	public void collisionWithDrone() {
		PlayerRobot.getPlayerRobot().damageTaken(GameUtility.COLLISION_DAMAGE/2);
		notifyObservers();
		System.out.println("Drone collided with player, did " + GameUtility.COLLISION_DAMAGE/2 + " damage");
	}
	
	/* Robot has collided with an EnergyStation. 
	 * Gets the first EnergyStation in the list, makes sure it hasn't been
	 * collided with before (more than 0 capacity), and passes its capacity
	 * to the Robot. Then calls a method in EnergyStation to set its capacity 
	 * to 0 and fade its color, indicating it is inactive.
	 * Creates a new randomly generated EnergyStation */
	public void collisionWithEnergyStation() {
		IIterator allObjects = objectsCollection.getIterator();
		while(allObjects.hasNext()) {
			GameObject currentObject = (GameObject)allObjects.getNext();
			if (currentObject instanceof EnergyStation) {
				EnergyStation e = (EnergyStation)currentObject;
				if (e.getCapacity() == 0) {} // skip
				else {
					PlayerRobot.getPlayerRobot().collisionWithEnergyStation(e.getCapacity());
					notifyObservers();
					e.collisionWithRobot();
					notifyObservers();
					createEnergyStation();
					System.out.println("Collided with EnergyStation of size " + e.getSize());
					break;
				}
			}
		}
	}
	
	/* Robot collided with a Base */
	public void collisionWithBase(int baseNum) {
		PlayerRobot.getPlayerRobot().updateLastBase(baseNum);
		notifyObservers();
	}
	
	/* Changes every NPRs Strategy.
	 * Goes through the GameObjectCollection and changes each
	 * one's Strategy */
	public void changeNPRStrategies() {
		IIterator allObjects = objectsCollection.getIterator();
		
		while(allObjects.hasNext()) {
			GameObject currentObject = (GameObject)allObjects.getNext();
			if(currentObject instanceof NonPlayerRobot) {
				NonPlayerRobot nonPlayerRobot = (NonPlayerRobot)currentObject;
				Strategy oldStrategy = nonPlayerRobot.getStrategy();
				Strategy newStrategy = null;
				if(oldStrategy == null){
					nonPlayerRobot.setStrategy(new NextBaseStrategy(nonPlayerRobot, objectsCollection));
				}
				else {
					while((newStrategy == null) || (newStrategy.getClass().equals(oldStrategy.getClass()))) {
						int randomNum = GameUtility.randomInt(1, 4);
						
						if(randomNum == 1) {
							newStrategy = new AttackStrategy(nonPlayerRobot);
						}
						else if(randomNum == 2) {
							newStrategy = new NextBaseStrategy(nonPlayerRobot, objectsCollection);
						}
						else if(randomNum == 3) {
							newStrategy = new DroneStrategy(nonPlayerRobot);
						}
						nonPlayerRobot.setStrategy(newStrategy);
					}
				}
				notifyObservers();
			}
		}
	}

	/* Robot collided with another Robot. 
	 * Robot takes damage which reduces max speed,
	 * and a random NPR takes damage as well */
	public void collisionWithRobot() {
		PlayerRobot.getPlayerRobot().damageTaken(GameUtility.COLLISION_DAMAGE);
		IIterator objects = objectsCollection.getIterator();
		int randomNum = GameUtility.randomInt(1, 4);
		while(objects.hasNext()) {
			GameObject currentObject = (GameObject)objects.getNext();
			// random of the three NPRs
			for(int i = 1; currentObject instanceof NonPlayerRobot; i++) {
				NonPlayerRobot npr = (NonPlayerRobot)currentObject;
				if(randomNum == i) {
					npr.damageTaken(GameUtility.COLLISION_DAMAGE);
				}
				else currentObject = (GameObject)objects.getNext();
			}
		}
		notifyObservers();
	}
}