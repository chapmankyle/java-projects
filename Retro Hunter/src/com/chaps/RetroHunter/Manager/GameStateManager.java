package com.chaps.RetroHunter.Manager;

import java.awt.Graphics2D;

import com.chaps.RetroHunter.GameStates.DeadState;
import com.chaps.RetroHunter.GameStates.MenuState;
import com.chaps.RetroHunter.GameStates.OptionsState;
import com.chaps.RetroHunter.GameStates.PauseState;
import com.chaps.RetroHunter.GameStates.PlayState;

public class GameStateManager {

	// holding state information
	private GameState[] gameState; 
	private int currState;
	private int prevState;

	private boolean paused;
	private PauseState pauseState;

	// states
	public static final int NUM_STATES = 4;
	public static final int MENU = 0;
	public static final int PLAY = 1;
	public static final int OPTIONS = 2;
	public static final int DEAD = 3;

	public GameStateManager() {
		JukeBox.init();  
		
		paused = false;
		pauseState = new PauseState(this);
		pauseState.init();

		gameState = new GameState[NUM_STATES];
		setState(MENU); 
	}

	// setting current state of game
	public void setState(int state) {
		prevState = currState;
		clearState(prevState);
		currState = state;

		if (state == MENU) {
			gameState[state] = new MenuState(this);
		} else if (state == PLAY) {
			gameState[state] = new PlayState(this);
		} else if (state == OPTIONS) {
			gameState[state] = new OptionsState(this);
		} else if (state == DEAD) {
			gameState[state] = new DeadState(this);
		}

		gameState[state].init();
	}

	// free up space
	public void clearState(int state) {
		gameState[state] = null;
	}

	// update to current state
	public void update() {
		if (paused) {
			pauseState.update();
		} else if (gameState[currState] != null) {
			gameState[currState].update();
		}
	}

	// draw current state
	public void draw(Graphics2D g) {
		if (paused) {
			pauseState.draw(g);
		} else if (gameState[currState] != null) {
			gameState[currState].draw(g);
		}
	}

	// manage pausing
	public void setPaused(boolean paused) {
		this.paused = paused;
	}

}
