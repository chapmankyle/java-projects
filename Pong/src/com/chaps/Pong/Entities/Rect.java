package com.chaps.Pong.Entities;

import java.awt.Rectangle;

public class Rect {
	public int x;
	public int y;
	public int width;
	public int height;
	public Rectangle bounds;
	
	// default constructor
	public Rect(int x, int y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		bounds = new Rectangle(x, y, width, height);
	}

	// accessors
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public Rectangle getBounds() {
		return bounds; 
	}

	// mutators
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	public void setBounds(int x, int y, int width, int height) {
		bounds = new Rectangle(x, y, width, height);
	}
}
