// Main entry point of entire game
// Creates the frame

package com.chaps.RetroHunter;

import javax.swing.JFrame;

import com.chaps.RetroHunter.Manager.Content;

public class Game {
	
	public static void main(String[] args) {		
		JFrame window = new JFrame("Retro Hunter");
		
		window.add(new GamePanel());
		
		window.setIconImage(Content.ICON_32[0][0]);  
		window.setResizable(false);
		window.pack();
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setVisible(true); 
	}
	
}
