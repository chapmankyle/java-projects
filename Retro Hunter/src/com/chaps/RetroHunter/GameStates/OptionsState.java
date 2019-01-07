package com.chaps.RetroHunter.GameStates;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.chaps.RetroHunter.Manager.Content;
import com.chaps.RetroHunter.Manager.GameState;
import com.chaps.RetroHunter.Manager.GameStateManager;
import com.chaps.RetroHunter.Manager.JukeBox;
import com.chaps.RetroHunter.Manager.Keys;
import com.chaps.RetroHunter.Manager.Mouse;

public class OptionsState extends GameState {

	// options background
	public BufferedImage bg;
	
	// check to see if mouse has clicked back
	public Rectangle back;
	public Rectangle apply;
	
	public OptionsState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		bg = Content.OPTIONS_BG[0][0];
		back = new Rectangle(14, 14, 88, 33);
		apply = new Rectangle(143, 479, 113, 39);
		JukeBox.resumeLoop("menu_bg");
	}
	
	public void update() {
		getInput();
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(bg, 0, 0, null);
	}
	
	public void getInput() {
		if ((Mouse.getPos().intersects(back) && Mouse.isClicked()) || Keys.isPressed(Keys.BACK)) {
			JukeBox.stop("menu_bg");
			JukeBox.play("enter"); 
			gsm.setState(GameStateManager.MENU); 
		}
		if (Mouse.getPos().intersects(apply) && Mouse.isClicked()) {
			JukeBox.stop("menu_bg");
			JukeBox.play("select");  
			gsm.setState(GameStateManager.MENU);  

		}
	}

}
