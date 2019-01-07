// Holds current mouse position

package com.chaps.RetroHunter.Manager;

import java.awt.Rectangle;

public class Mouse {
	
	public static int x = 0;
	public static int y = 0;
	public static boolean clicked = false;
	public static boolean double_click = false;
	public static Rectangle pos = new Rectangle(x, y, 2, 2);
	
	public static void setPos(int xx, int yy, boolean isClicked) {
		x = xx;
		y = yy; 
		clicked = isClicked;
		pos = new Rectangle(x, y, 2, 2);
	}
	
	// getters
	public static int getX() {
		return x;
	}
	
	public static int getY() {
		return y; 
	}
	
	public static boolean isClicked() {
		return clicked; 
	}
	
	public static boolean isDoubleClicked() {
		return double_click; 
	}
	
	public static Rectangle getPos() {
		return pos; 
	}
	
	// setter
	public static void setDoubleClicked(boolean click) {
		double_click = click; 
	}
	
}
