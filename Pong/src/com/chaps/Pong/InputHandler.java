package com.chaps.Pong;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {
	
	public InputHandler(Game game) {
		game.addKeyListener(this);
	}
	
	public class Key {
		private boolean pressed = false;
		
		public boolean isPressed() {
			return pressed; 
		}

		public void toggle(boolean pressed) {
			this.pressed = pressed;
		}
	}
	
	public Key W = new Key();
	public Key S = new Key();
	public Key up = new Key();
	public Key down = new Key();
	
	public void toggleKey(int keyCode, boolean pressed) {
		if (keyCode == KeyEvent.VK_W) {
			W.toggle(pressed);
		}
		if (keyCode == KeyEvent.VK_S) {
			S.toggle(pressed);
		}
		if (keyCode == KeyEvent.VK_UP) {
			up.toggle(pressed);
		}
		if (keyCode == KeyEvent.VK_DOWN) {
			down.toggle(pressed); 
		}
	}
	
	public void keyPressed(KeyEvent e) {
		toggleKey(e.getKeyCode(), true);
	}

	public void keyReleased(KeyEvent e) {
		toggleKey(e.getKeyCode(), false);
	}
	
	public void keyTyped(KeyEvent e) { }
	
}
