package com.chaps.Dodger;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Rect {

	public Color colour = Color.BLACK;
	
	public Line(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public Color getColour() {
		return colour;
	}

	public void setColour(Color colour) {
		this.colour = colour;
	}
	
	public void paint(Graphics g) {
		g.setColor(colour);
		g.fillRect(x, y, width, height);
	}
	
}
