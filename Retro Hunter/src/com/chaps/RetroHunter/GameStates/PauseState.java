package com.chaps.RetroHunter.GameStates;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.chaps.RetroHunter.GamePanel;
import com.chaps.RetroHunter.Manager.Content;
import com.chaps.RetroHunter.Manager.GameState;
import com.chaps.RetroHunter.Manager.GameStateManager;
import com.chaps.RetroHunter.Manager.JukeBox;
import com.chaps.RetroHunter.Manager.Keys;
import com.chaps.RetroHunter.Manager.Mouse;

public class PauseState extends GameState {
	// variables
	public BufferedImage bg;
	public Rectangle exit;
	
	public PauseState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		bg = Content.PAUSE_BG[0][0];
		exit = new Rectangle(27 + (GamePanel.WIDTH / 2) - (bg.getWidth() / 2), 531, 89, 39);
	}
	
	// called every frame
	public void update() {
		getInput();
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(bg, (GamePanel.WIDTH / 2) - (bg.getWidth() / 2), (GamePanel.HEIGHT / 2) - (bg.getHeight() / 2), null);
	}
	
	public void getInput() {
		if (Keys.isPressed(Keys.PAUSE)) {
			JukeBox.resumeLoop("play_bg");
			gsm.setPaused(false);
		}
		if (Mouse.getPos().intersects(exit) && Mouse.isClicked()) {
			gsm.setPaused(false);
			gsm.setState(GameStateManager.MENU);
		}
	}

}