package com.chaps.Pong.Entities;

import java.awt.Color;
import java.awt.Graphics;

public class Ball extends Rect {
	public Ball(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval(x, y, width, height);
	}
}
