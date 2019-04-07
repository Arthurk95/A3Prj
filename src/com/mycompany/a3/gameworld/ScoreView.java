package com.mycompany.a3.gameworld;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.mycompany.a3.gameobject.PlayerRobot;
import com.mycompany.a3.gameobject.Robot;
import com.mycompany.a3.gameworld.GameWorld;

public class ScoreView extends Container implements Observer{
	private Label livesLeft = new Label("3");
	private Label gameTime = new Label("0");
	private Label lastBaseReached = new Label("1");
	private Label energy = new Label("100");
	private Label damageLevel = new Label("0");
	private Label sound = new Label("OFF");
	
	public ScoreView() {
		this.setLayout(new FlowLayout(Component.CENTER));
		
		formatLabels();
		
		this.add(formatTextLabel(new Label("Lives left ")));
		this.add(livesLeft);
		this.add(formatTextLabel(new Label("Game time ")));
		this.add(gameTime);
		this.add(formatTextLabel(new Label("Last base ")));
		this.add(lastBaseReached);
		this.add(formatTextLabel(new Label("Energy level ")));
		this.add(energy);
		this.add(formatTextLabel(new Label("Damage level ")));
		this.add(damageLevel);
		this.add(formatTextLabel(new Label("Sound ")));
		this.add(sound);
		
		this.getAllStyles().setMargin(Component.TOP, 20);
		this.getAllStyles().setMargin(Component.BOTTOM, 20);
	}
	
	public void update(Observable o, Object arg) {
		GameWorld gw = (GameWorld)o;
		
		
		livesLeft.setText(String.valueOf(gw.getLivesRemaining()));
		gameTime.setText(String.valueOf(gw.getGameTime()));
		lastBaseReached.setText(String.valueOf(PlayerRobot.getPlayerRobot().getLastBaseReached()));
		energy.setText(String.valueOf(PlayerRobot.getPlayerRobot().getEnergy()));
		damageLevel.setText(String.valueOf(PlayerRobot.getPlayerRobot().getDamage()));
		if(gw.isSoundOn())
			sound.setText("ON");
		else sound.setText("OFF");
		this.repaint();
	}
	
	private void formatLabels() {
		livesLeft = formatValueLabel(livesLeft);
		gameTime = formatValueLabel(gameTime);
		lastBaseReached = formatValueLabel(lastBaseReached);
		energy = formatValueLabel(energy);
		damageLevel = formatValueLabel(damageLevel);
		sound = formatValueLabel(sound);
	}
	
	private Label formatValueLabel(Label label) {
		label.getAllStyles().setBgTransparency(30);
		label.getAllStyles().setFgColor(ColorUtil.rgb(100,100,100));
		label.getAllStyles().setBgColor(ColorUtil.rgb(200, 200, 200));
		label.getAllStyles().setPadding(Component.LEFT, 4);
		label.getAllStyles().setPadding(Component.RIGHT, 4);
		label.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.rgb(180, 180, 180)));
		label.getAllStyles().setAlignment(CENTER);
		return label;
	}
	
	private Label formatTextLabel(Label label) {
		label.getAllStyles().setPadding(Component.LEFT, 4);
		label.getAllStyles().setFgColor(ColorUtil.rgb(85,188,255)); // baby blue
		return label;
	}
}
