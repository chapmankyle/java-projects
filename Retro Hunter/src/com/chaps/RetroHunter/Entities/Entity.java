package com.chaps.RetroHunter.Entities;

import java.awt.Graphics2D;

public abstract class Entity {

	// dimensions
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	// movement
	protected boolean moving;
	protected boolean up;
	protected boolean down;
	protected boolean left;
	protected boolean right;
	
	// default constructor
	public Entity(int x, int y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	// update and draw entity
	public abstract void update();
	
	public abstract void draw(Graphics2D g);
}
