/* -----------------------
 * Robo-Track
 * Author: Arthur Kharit
 * CSC 133, Spring 2019
 * A1Prj
 * -------------------- */

package com.mycompany.a3;

import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.mycompany.a3.commands.*;
import com.mycompany.a3.gameworld.GameWorld;
import com.mycompany.a3.gameworld.MapView;
import com.mycompany.a3.gameworld.ScoreView;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;

/* Controller class for GameWorld. 
 * Initializes a GameWorld and the BorderLayout UI.
 * Creates commands to perform in-game actions within the GameWorld */
public class Game extends Form{
	
	private GameWorld gameWorld;
	private MapView mapView;
	private ScoreView scoreView;
	private CMDCollisionNPR collisionNPRCMD;
	private CMDCollisionDrone collisionDroneCMD;
	private CMDCollisionStation collisionStationCMD;
	private CMDCollisionBase collisionBaseCMD;
	private CMDAccelerate accelerateCMD;
	private CMDBrake brakeCMD ;
	private CMDTurnLeft turnLeftCMD;
	private CMDTurnRight turnRightCMD;
	private CMDTick tickCMD;
	private CMDStrategies strategiesCMD;
	private CMDExit exitCMD;
	private CMDSound soundCMD;
	private CMDAboutInfo aboutInfoCMD;
	private CMDHelp helpCMD;
	
	public Game() {
		gameWorld = new GameWorld();
		mapView = new MapView();
		scoreView = new ScoreView();
		gameWorld.addObserver(mapView);
		gameWorld.addObserver(scoreView);
		this.setLayout(new BorderLayout());
		initCommands();

		makeToolbar();
		this.add(BorderLayout.NORTH, scoreView);
		this.add(BorderLayout.WEST, makeLeftColumn());
		this.add(BorderLayout.CENTER, mapView);
		this.add(BorderLayout.EAST, makeRightColumn());
		this.add(BorderLayout.SOUTH, makeBottomRow());
		
		this.show();
		GameUtility.setGameSize(mapView.getWidth(), mapView.getHeight());
		gameWorld.init();
	}
	
	/* Creates the Toolbar for this with a SideMenu, a title,
	 * and a help button on the right.  */
	private void makeToolbar() {
		Toolbar toolbar = new Toolbar();
		this.setToolbar(toolbar);
		Label title = new Label("- RoboTrack -");
		toolbar.setTitleComponent(title);
		
		Toolbar.setOnTopSideMenu(false);
		
		CheckBox soundCB = new CheckBox("Sound");
		soundCB.getAllStyles().setBgTransparency(255);
		soundCB.getAllStyles().setBgColor(ColorUtil.LTGRAY);
		soundCB.getAllStyles().setPadding(Component.TOP, 5);
		soundCB.getAllStyles().setPadding(Component.BOTTOM, 5);
		soundCB.setCommand(soundCMD);
		
		toolbar.addComponentToSideMenu(soundCB);
		toolbar.addCommandToSideMenu(accelerateCMD);
		toolbar.addCommandToSideMenu(aboutInfoCMD);
		toolbar.addCommandToSideMenu(exitCMD);
		
		toolbar.addCommandToRightBar(helpCMD);
	}
	
	/* Creates the left column using a Y_AXIS BoxLayout, containing
	 * the Robot control commands */
	private Container makeLeftColumn() {
		Container leftPanel = new Container(); // main panel
		leftPanel.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

		leftPanel.add(newButton(accelerateCMD, 'a'));
		leftPanel.add(newButton(brakeCMD, 'b'));
		leftPanel.add(newButton(turnLeftCMD, 'l'));
		leftPanel.add(newButton(turnRightCMD, 'r'));
		leftPanel.getAllStyles().setPadding(Component.RIGHT, 4);
		leftPanel.getAllStyles().setPadding(Component.LEFT, 4);
		return leftPanel;
	}
	
	/* Entire bottom panel containing the Collision commands */
	private Container makeBottomRow() {
		// Create the row containers
		Container bottomPanel = new Container();
		bottomPanel.setLayout(new FlowLayout(Component.CENTER));
		
		bottomPanel.add(newButton(collisionNPRCMD, (char)0));
		bottomPanel.add(newButton(collisionDroneCMD, 'g'));
		bottomPanel.add(newButton(collisionStationCMD, 'e'));
		bottomPanel.add(newButton(collisionBaseCMD, (char)0));

		bottomPanel.getAllStyles().setMargin(Component.BOTTOM, 5);
		bottomPanel.getAllStyles().setMargin(Component.TOP, 5);
		bottomPanel.getAllStyles().setPadding(Component.RIGHT, 4);
		bottomPanel.getAllStyles().setPadding(Component.LEFT, 4);
		return bottomPanel;
	}
	
	/* Creates the right column of the UI */
	private Container makeRightColumn() {
		Container rightColumn = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		
		rightColumn.add(newButton(tickCMD, 't'));
		rightColumn.add(newButton(strategiesCMD, (char)0));
		return rightColumn;
	}
	
	/* Creates a MyButton and adds a KeyListener if keyListener is NOT 0 
	 * Returns the newly created MyButton */
	private MyButton newButton(Command cmd, char keyListener) {
		MyButton button = new MyButton(cmd.getCommandName());
		button.setCommand(cmd);
		if (keyListener != (char)0){
			addKeyListener(keyListener, cmd);
		}
		return button; 
	}
	
	private void initCommands() {
		collisionNPRCMD = new CMDCollisionNPR(gameWorld);
		collisionDroneCMD = new CMDCollisionDrone(gameWorld);
		collisionStationCMD = new CMDCollisionStation(gameWorld);
		collisionBaseCMD = new CMDCollisionBase(gameWorld);
		accelerateCMD = new CMDAccelerate(gameWorld);
		brakeCMD = new CMDBrake(gameWorld);
		turnLeftCMD = new CMDTurnLeft(gameWorld);
		turnRightCMD = new CMDTurnRight(gameWorld);
		tickCMD = new CMDTick(gameWorld);
		strategiesCMD = new CMDStrategies(gameWorld);
		exitCMD = new CMDExit();
		soundCMD = new CMDSound(gameWorld);
		helpCMD = new CMDHelp();
		aboutInfoCMD = new CMDAboutInfo();
	}
}
