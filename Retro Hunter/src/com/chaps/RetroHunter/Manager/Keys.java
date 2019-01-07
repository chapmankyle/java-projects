// Holds the current and previous key state

package com.chaps.RetroHunter.Manager;

import java.awt.event.KeyEvent;

public class Keys {

	public static final int NUM_KEYS = 9;

	public static boolean keyState[] = new boolean[NUM_KEYS];
	public static boolean prevKeyState[] = new boolean[NUM_KEYS];

	public static int UP = 0;
	public static int DOWN = 1;
	public static int LEFT = 2;
	public static int RIGHT = 3;
	public static int SPACE = 4;
	public static int ENTER = 5;
	public static int PAUSE = 6; 
	public static int BACK = 7;
	public static int EXIT = 8;

	public static void setKey(int state, boolean pressed) {
		if (state == KeyEvent.VK_UP || state == KeyEvent.VK_W) {
			keyState[UP] = pressed;
		}
		if (state == KeyEvent.VK_DOWN || state == KeyEvent.VK_S) {
			keyState[DOWN] = pressed;
		}
		if (state == KeyEvent.VK_LEFT || state == KeyEvent.VK_A) {
			keyState[LEFT] = pressed;
		}
		if (state == KeyEvent.VK_RIGHT || state == KeyEvent.VK_D) {
			keyState[RIGHT] = pressed;
		}
		if (state == KeyEvent.VK_SPACE) {
			keyState[SPACE] = pressed;
		}
		if (state == KeyEvent.VK_ENTER) {
			keyState[ENTER] = pressed;
		}
		if (state == KeyEvent.VK_ESCAPE || state == KeyEvent.VK_P) {
			keyState[PAUSE] = pressed;
		}
		if (state == KeyEvent.VK_BACK_SPACE || state == KeyEvent.VK_DELETE) {
			keyState[BACK] = pressed;
		}
		if (state == KeyEvent.VK_F1) {
			keyState[EXIT] = pressed;
		}
	}

	// checks state of key
	public static void update() {
		for (int i = 0; i < NUM_KEYS; i++) {
			prevKeyState[i] = keyState[i];
		}
	}

	public static boolean isPressed(int key) {
		return keyState[key] && !prevKeyState[key];
	}

	public static boolean isDown(int key) {
		return keyState[key];
	}

	public static boolean anyKeyDown() {
		for (int i = 0; i < NUM_KEYS; i++) {
			if (keyState[i])
				return true;
		}
		return false;
	}

	public static boolean anyKeyPress() {
		for (int i = 0; i < NUM_KEYS; i++) {
			if (keyState[i] && !prevKeyState[i])
				return true;
		}
		return false;
	}
}
