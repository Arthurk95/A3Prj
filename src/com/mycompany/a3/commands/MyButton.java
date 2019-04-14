package com.mycompany.a3.commands;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.Component;

/* Button subclass that simply adds some styling 
 * Makes the button a turquoise-ish color and adds
 * padding/margins */
public class MyButton extends Button{
	
	public MyButton(String name) {
		super(name);
		
		this.getAllStyles().setBgTransparency(255);
		
		this.getAllStyles().setPadding(Component.TOP, 2);
		this.getAllStyles().setPadding(Component.BOTTOM, 2);
		this.getAllStyles().setPadding(Component.RIGHT, 2);
		this.getAllStyles().setPadding(Component.LEFT, 2);
		
		this.getAllStyles().setMargin(Component.RIGHT, 4);
		this.getAllStyles().setMargin(Component.LEFT, 4);
		this.getAllStyles().setMargin(Component.TOP, 4);
		
		this.getAllStyles().setFgColor(ColorUtil.WHITE); // white text
		this.getPressedStyle().setBgColor(ColorUtil.rgb(100, 100, 100)); // gray
		this.getUnselectedStyle().setBgColor(ColorUtil.rgb(70, 140, 140)); // turquoise
		this.getDisabledStyle().setBgColor(ColorUtil.rgb(100, 100, 100)); // gray
		this.setEnabled(true);
	}
	
}
