package com.chaps.RetroHunter.GameStates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.chaps.RetroHunter.Manager.Content;
import com.chaps.RetroHunter.Manager.GameState;
import com.chaps.RetroHunter.Manager.GameStateManager;
import com.chaps.RetroHunter.Manager.JukeBox;
import com.chaps.RetroHunter.Manager.Keys;
import com.chaps.RetroHunter.Manager.Mouse;

public class MenuState extends GameState {

	// menu background and player
	private BufferedImage bg;
	private BufferedImage player;

	// handles mouse clicking
	private Rectangle overall;
	private Rectangle rec_play;
	private Rectangle rec_options;
	private Rectangle rec_quit;

	// handles choosing options
	private int currOption = 0;
	private String[] options = { 
			"PLAY", 
			"OPTIONS", 
			"QUIT" 
	};

	public MenuState(GameStateManager gsm) {
		super(gsm);
	}

	// initialising music and content
	public void init() {
		bg = Content.MENU_BG[0][0];
		player = Content.PLAYER_POINT[0][0];

		overall = new Rectangle(296, 303, 202, 248);
		rec_play = new Rectangle(345, 306, 107, 43);
		rec_options = new Rectangle(304, 404, 190, 43);
		rec_quit = new Rectangle(344, 502, 109, 48);

		JukeBox.load("/music/bg_slow.wav", "menu_bg");
		JukeBox.setVolume("menu_bg", -14f);
		JukeBox.play("menu_bg");
		JukeBox.loop("menu_bg");

		JukeBox.load("/sfx/select.wav", "select");
		JukeBox.load("/sfx/blop.wav", "enter");
		JukeBox.setVolume("select", -8f);
	}

	public void update() {
		getInput();
	}

	public void draw(Graphics2D g) {
		g.drawImage(bg, 0, 0, null);

		g.setFont(new Font("Calibri Light", Font.PLAIN, 15));
		g.setColor(Color.WHITE);
		g.drawString("Pre - Alpha", 5, 17);
		g.drawString("Development Build", 5, 32);
		g.drawString("Version 03", 5, 47);

		if (currOption == 0)
			g.drawImage(player, 190, 307, null);
		else if (currOption == 1)
			g.drawImage(player, 190, 405, null);
		else if (currOption == 2)
			g.drawImage(player, 190, 503, null);
	}

	public void getInput() {
		if (Keys.isPressed(Keys.UP)) {
			JukeBox.play("select");
			currOption--;

			if (currOption < 0) {
				currOption = options.length - 1;
			}
		} else if (Keys.isPressed(Keys.DOWN)) {
			JukeBox.play("select");
			currOption++;

			if (currOption >= options.length) {
				currOption = 0;
			}
		}

		if (!Mouse.isDoubleClicked()) {
			if (Mouse.getPos().intersects(rec_play)/* && Mouse.isClicked()*/)
				currOption = 0;
			if (Mouse.getPos().intersects(rec_options)/* && Mouse.isClicked()*/)
				currOption = 1;
			if (Mouse.getPos().intersects(rec_quit)/* && Mouse.isClicked()*/)
				currOption = 2;
		}
		else if (Mouse.isDoubleClicked() && Mouse.getPos().intersects(overall)) {
			Mouse.setDoubleClicked(false);
			JukeBox.play("enter");
			select();
		}

		if (Keys.isPressed(Keys.ENTER)) {
			JukeBox.play("enter");
			select();
		}
	}

	public void select() {
		if (currOption == 0) {
			JukeBox.stop("menu_bg");
			gsm.setState(GameStateManager.PLAY);
		} else if (currOption == 1) {
			JukeBox.stop("menu_bg");
			gsm.setState(GameStateManager.OPTIONS);
		} else {
			System.exit(0);
		}
	}

}
