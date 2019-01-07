package com.chaps.RetroHunter.GameStates;

import java.awt.Color;
import java.awt.Graphics2D;

import com.chaps.RetroHunter.GamePanel;
import com.chaps.RetroHunter.Entities.Player;
import com.chaps.RetroHunter.Manager.Content;
import com.chaps.RetroHunter.Manager.GameState;
import com.chaps.RetroHunter.Manager.GameStateManager;
import com.chaps.RetroHunter.Manager.JukeBox;
import com.chaps.RetroHunter.Manager.Keys;

public class PlayState extends GameState {
	protected Player player;

	// default constructor
	public PlayState(GameStateManager gsm) {
		super(gsm);
	}
	
	// all abstract methods
	public void init() {
		player = new Player(GamePanel.WIDTH / 2 - 10, GamePanel.HEIGHT / 2 - 10, Content.PLAYER[0][0].getWidth(), Content.PLAYER[0][0].getHeight());
		JukeBox.load("/music/bg_loop.wav", "play_bg");
		JukeBox.play("play_bg");
		JukeBox.setVolume("play_bg", -10f); 
		JukeBox.loop("play_bg"); 
	}
	
	public void update() {
		getInput();
		player.update();
	}
	
	public void draw(Graphics2D g) {
		g.setColor(new Color(10, 240, 150));
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT); 
		player.draw(g);
	}
	
	public void getInput() {
		if (Keys.isPressed(Keys.EXIT)) {
			JukeBox.stop("play_bg");
			gsm.setState(GameStateManager.MENU);
		}
		if (Keys.isPressed(Keys.PAUSE)) {
			JukeBox.stop("play_bg");
			gsm.setPaused(true);
		}
	}

}
