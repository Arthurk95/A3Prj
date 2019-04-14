package com.mycompany.a3.gameobject;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public interface ISelectable {
	public void setSelected(boolean yesNo);
	public boolean isSelected();
	public boolean contains(Point pointer, Point component);
	public void draw(Graphics g, Point component);
}
