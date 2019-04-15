/* -----------------------
 * Robo-Track
 * Author: Arthur Kharit
 * CSC 133, Spring 2019
 * -------------------- */

package com.mycompany.a3;

import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.UITimer;
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
public class Game extends Form implements Runnable{
	
	private GameWorld gameWorld;
	private MapView mapView;
	private ScoreView scoreView;
	private UITimer timer;
	private CMDAccelerate accelerateCMD;
	private CMDBrake brakeCMD;
	private CMDTurnLeft turnLeftCMD;
	private CMDTurnRight turnRightCMD;
	private CMDStrategies strategiesCMD;
	private CMDExit exitCMD;
	private CMDSound soundCMD;
	private CMDAboutInfo aboutInfoCMD;
	private CMDHelp helpCMD;
	private CMDPause pauseCMD;
	private CMDPosition positionCMD;
	private MyButton accelerateBTN, brakeBTN, turnRightBTN, turnLeftBTN,
						strategiesBTN, positionBTN, pauseBTN;
	private CheckBox soundCB;
	
	public Game() {
		gameWorld = new GameWorld();
		mapView = new MapView();
		scoreView = new ScoreView();
		gameWorld.addObserver(mapView);
		gameWorld.addObserver(scoreView);
		this.setLayout(new BorderLayout());
		timer = new UITimer(this);
		initCommands();
		initButtons();

		makeToolbar();
		this.add(BorderLayout.NORTH, scoreView);
		this.add(BorderLayout.WEST, makeLeftColumn());
		this.add(BorderLayout.CENTER, mapView);
		this.add(BorderLayout.EAST, makeRightColumn());
		this.add(BorderLayout.SOUTH, makeBottomRow());
		
		this.show();
		GameUtility.setGameSize(mapView.getWidth(), mapView.getHeight());
		gameWorld.init();
		
		
		timer.schedule(GameUtility.TICK_RATE, true, this);
	}
	
	public void run() {
		gameWorld.clockTick();
	}
	
	/* Creates the Toolbar for this with a SideMenu, a title,
	 * and a help button on the right.  */
	private void makeToolbar() {
		Toolbar toolbar = new Toolbar();
		this.setToolbar(toolbar);
		Label title = new Label("- RoboTrack -");
		toolbar.setTitleComponent(title);
		
		Toolbar.setOnTopSideMenu(false);
		
		soundCB = new CheckBox("Sound");
		soundCB.getAllStyles().setBgTransparency(255);
		soundCB.getAllStyles().setBgColor(ColorUtil.LTGRAY);
		soundCB.getAllStyles().setPadding(Component.TOP, 5);
		soundCB.getAllStyles().setPadding(Component.BOTTOM, 5);
		soundCB.setCommand(soundCMD);
		soundCB.setSelected(true);
		
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

		leftPanel.add(accelerateBTN);
		
		leftPanel.add(brakeBTN);
		leftPanel.add(turnLeftBTN);
		leftPanel.add(turnRightBTN);
		leftPanel.getAllStyles().setPadding(Component.RIGHT, 4);
		leftPanel.getAllStyles().setPadding(Component.LEFT, 4);
		return leftPanel;
	}
	
	/* Entire bottom panel containing the Collision commands */
	private Container makeBottomRow() {
		// Create the row containers
		Container bottomPanel = new Container();
		bottomPanel.setLayout(new FlowLayout(Component.CENTER));

		bottomPanel.add(pauseBTN);
		bottomPanel.add(positionBTN);
		bottomPanel.getAllStyles().setMargin(Component.BOTTOM, 5);
		bottomPanel.getAllStyles().setMargin(Component.TOP, 5);
		bottomPanel.getAllStyles().setPadding(Component.RIGHT, 4);
		bottomPanel.getAllStyles().setPadding(Component.LEFT, 4);
		return bottomPanel;
	}
	
	/* Creates the right column of the UI */
	private Container makeRightColumn() {
		Container rightColumn = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		
		rightColumn.add(strategiesBTN);
		return rightColumn;
	}
	
	/* Creates a MyButton and adds a KeyListener if keyListener is NOT 0 
	 * Returns the newly created MyButton */
	private MyButton newButton(Command cmd, char keyListener) {
		MyButton button = new MyButton(cmd.getCommandName());
		button.setCommand(cmd);
		if (keyListener != (char)0){
			addKeyListener(keyListener, cmd);
			keyRepeated(keyListener);
		}
		return button; 
	}
	
	/* Creates all the buttons */
	private void initButtons() {
		accelerateBTN = newButton(accelerateCMD, 'a');
		brakeBTN = newButton(brakeCMD, 'b');
		turnLeftBTN = newButton(turnLeftCMD, 'l');
		turnRightBTN = newButton(turnRightCMD, 'r');
		pauseBTN = newButton(pauseCMD, (char) 0);
		pauseCMD.setButton(pauseBTN);
		strategiesBTN = newButton(strategiesCMD, (char)0);
		positionBTN = newButton(positionCMD, (char) 0);
		positionBTN.setEnabled(false);
	}
	
	/* Initializes all commands */
	private void initCommands() {
		accelerateCMD = new CMDAccelerate(gameWorld);
		brakeCMD = new CMDBrake(gameWorld);
		turnLeftCMD = new CMDTurnLeft(gameWorld);
		turnRightCMD = new CMDTurnRight(gameWorld);
		strategiesCMD = new CMDStrategies(gameWorld);
		exitCMD = new CMDExit();
		soundCMD = new CMDSound(gameWorld);
		helpCMD = new CMDHelp();
		aboutInfoCMD = new CMDAboutInfo();
		positionCMD = new CMDPosition(gameWorld);
		positionCMD.setEnabled(false);
		pauseCMD = new CMDPause(this, timer, gameWorld);
	}
	
	/* Disables commands that are irrelevant when paused */
	public void pauseGame() {
		accelerateCMD.setEnabled(false);
		accelerateBTN.setEnabled(false);
		brakeBTN.setEnabled(false);
		turnLeftBTN.setEnabled(false);
		turnRightBTN.setEnabled(false);
		strategiesBTN.setEnabled(false);
		positionBTN.setEnabled(true);
		removeKeyListener('a', accelerateCMD);
		removeKeyListener('b', brakeCMD);
		removeKeyListener('l', turnLeftCMD);
		removeKeyListener('r', turnRightCMD);
	}
	
	/* Re-enables all relevant buttons */
	public void unPauseGame() {
		accelerateCMD.setEnabled(true);
		accelerateBTN.setEnabled(true);
		brakeBTN.setEnabled(true);
		turnLeftBTN.setEnabled(true);
		turnRightBTN.setEnabled(true);
		strategiesBTN.setEnabled(true);
		positionBTN.setEnabled(false);
		addKeyListener('a', accelerateCMD);
		addKeyListener('b', brakeCMD);
		addKeyListener('l', turnLeftCMD);
		addKeyListener('r', turnRightCMD);
	}
}
