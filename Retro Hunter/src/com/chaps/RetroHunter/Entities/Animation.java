package com.chaps.RetroHunter.Entities;

import java.awt.image.BufferedImage;

public class Animation {

	// variables
	private BufferedImage[] frames; 
	private int currFrame;
	private int numFrames;
	
	private long start;
	private long delay;
	
	private int timesPlayed;
	
	// default constructor
	public Animation() {
		timesPlayed = 0;
	}
	
	public void update() {
		if (delay == -1) return; 
		
		long elapsed = (System.nanoTime() - start) / 1000000;
		
		if (elapsed > delay) {
			currFrame++;
			start = System.nanoTime();
		}
		if (currFrame == numFrames) {
			currFrame = 0;
			timesPlayed++;
		}
	}
	
	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
		numFrames = frames.length;
		if (currFrame >= numFrames) currFrame = 0; 
	}
	
	// setters
	public void setDelay(long delay) { this.delay = delay; }
	public void setFrame(int frame) { currFrame = frame; } 
	
	// getters
	public int getFrame() { return currFrame; }
	public BufferedImage getImage() { return frames[currFrame]; }
	public boolean hasPlayedOnce() { return timesPlayed > 0; }
	public boolean hasPlayed(int i) { return timesPlayed == i; }
	
}
