package com.chaps.RetroHunter.Entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.chaps.RetroHunter.GamePanel;
import com.chaps.RetroHunter.Manager.Content;
import com.chaps.RetroHunter.Manager.Keys;

public class Player extends Entity {
	// player dimensions
	public Rectangle bounds;
	
	// movement
	private float maxSpeed = 5f;	
	private float delta = 28f / 30f;
	private float accelerate = 0.2f; 
	private float decelerate = 0.3f; 
	private float speedUp = 0;
	private float speedDown = 0;
	private float speedLeft = 0;
	private float speedRight = 0; 
	
	// animation of player
	private Animation anim;
	private int delay = 200; 
	
	private BufferedImage[] idle;
	private BufferedImage[] up_down;
	private BufferedImage[] left;
	private BufferedImage[] right;

	public Player(int x, int y, int width, int height) {
		super(x, y, width, height);
		bounds = new Rectangle(x, y, width, height);
		anim = new Animation();
		
		idle = Content.PLAYER[0];
		up_down = Content.PLAYER[1];
		left = Content.PLAYER[2];
		right = Content.PLAYER[3];
		
		anim.setFrames(idle);
		anim.setDelay(500);
	}
	
	// update and draw abstract methods from entity
	public void update() {
		checkUp();
		checkDown();
		checkLeft();
		checkRight(); 
		
		setBounds(x, y);
		setAnimation();
	}
	
	// movement
	public void draw(Graphics2D g) {
		g.drawImage(anim.getImage(), bounds.x, bounds.y, null);
	}
	
	// setting player position (method overloading) 
	public void setBounds(Rectangle rect) {
		bounds = rect;
	}
	
	public void setBounds(int x, int y) {
		setBounds(x, y, this.width, this.height);
	}
	
	public void setBounds(int x, int y, int width, int height) { 
		bounds = new Rectangle(x, y, width, height);
	}
	
	public void setAnimation() {
		if (Keys.anyKeyDown()) { 
			if (Keys.isDown(Keys.UP) || Keys.isDown(Keys.DOWN)) {
				anim.setFrames(up_down); 
				anim.setDelay(delay); 
			}
			if (Keys.isDown(Keys.LEFT)) {
				anim.setFrames(left); 
				anim.setDelay(delay); 
			}
			if (Keys.isDown(Keys.RIGHT)) {
				anim.setFrames(right);  
				anim.setDelay(delay); 
			}
		}
		else {
			anim.setFrames(idle); 
			anim.setDelay(500); 
		}
		anim.update(); 
	}
	
	// checking if pressed is within bounds
	public void checkUp() {
		float moveUp = (float) (speedUp * delta);
		int yy = y; 
		
		if (yy >= 0) { 
			if (Keys.isDown(Keys.UP)) {
				if (speedUp < maxSpeed) {
					speedUp += accelerate;
				}
				else {
					speedUp = maxSpeed; 
				}
				
				yy -= moveUp;
				if (yy >= 0) {
					y -= moveUp;
				}
				if (yy < 0) {
					y = 0;
				}
			}
			else {				
				if (speedUp != 0 && yy >= 0) {
					speedUp -= decelerate;
					
					if (speedUp < 0) {
						speedUp = 0; 
					}
				}
				
				yy -= moveUp;
				if (yy >= 0) {
					y -= moveUp; 
				}
				if (yy < 0) {
					y = 0;
				}
			}
		}
	}
	
	public void checkDown() {
		float moveDown = (float) (speedDown * delta);
		int yy = y;
		
		if (yy <= GamePanel.HEIGHT - height) {
			if (Keys.isDown(Keys.DOWN)) {
				if (speedDown < maxSpeed) {
					speedDown += accelerate;
				}
				else {
					speedDown = maxSpeed; 
				}
				
				yy += moveDown;
				if (yy <= GamePanel.HEIGHT - height) {
					y += moveDown;
				}
				if (yy > GamePanel.HEIGHT - height) {
					y = GamePanel.HEIGHT - height; 
				}
			}
			else {
				if (speedDown != 0) {
					speedDown -= decelerate;
					
					if (speedDown < 0) {
						speedDown = 0; 
					}
				}
				
				yy += moveDown;
				if (yy <= GamePanel.HEIGHT - height) {
					y += moveDown;
				}
				if (yy > GamePanel.HEIGHT - height) {
					y = GamePanel.HEIGHT - height; 
				}
			}
		}
	}
	
	public void checkLeft() {
		float moveLeft = (float) (speedLeft * delta);
		int xx = x;
		
		if (xx >= 0) {
			if (Keys.isDown(Keys.LEFT)) {
				if (speedLeft < maxSpeed) {
					speedLeft += accelerate;
				}
				else {
					speedLeft = maxSpeed;
				}
				
				xx -= moveLeft;
				if (xx >= 0) {
					x -= moveLeft;
				}
				if (xx < 0) {
					x = 0;
				}
			}
			else {
				if (speedLeft != 0) {
					speedLeft -= decelerate;
					
					if (speedLeft < 0) {
						speedLeft = 0;
					}
				}
				
				xx -= moveLeft;
				if (xx >= 0) {
					x -= moveLeft;
				}
				if (xx < 0) {
					x = 0;
				}
			}
		}
	}
	
	public void checkRight() {
		float moveRight = (float) (speedRight * delta);
		int xx = x;
		
		if (xx < GamePanel.WIDTH - width - 1) {
			if (Keys.isDown(Keys.RIGHT)) {
				if (speedRight < maxSpeed) {
					speedRight += accelerate;
				}
				else {
					speedRight = maxSpeed;
				}
				
				xx += moveRight;
				if (xx <= GamePanel.WIDTH - width) {
					x += moveRight;
				}
				if (xx > GamePanel.WIDTH - width) {
					x = GamePanel.WIDTH - width; 
				}
			}
			else {
				if (speedRight != 0) {
					speedRight -= decelerate; 
					
					if (speedRight < 0) {
						speedRight = 0; 
					}
				}
				
				xx += moveRight;
				if (xx <= GamePanel.WIDTH - width) {
					x += moveRight;
				}
				if (xx > GamePanel.WIDTH - width) {
					x = GamePanel.WIDTH - width; 
				}
			}
		}
	}

}
