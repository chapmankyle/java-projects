package com.chaps.RetroHunter.Manager;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class JukeBox {

	// allows storage of key and value (key is string and value is clip)
	private static HashMap<String, Clip> clips; 
	private static int frame;
	
	// creates new HashMap
	public static void init() {
		clips = new HashMap<String, Clip>();
		frame = 0;
	}
	
	// loads audio in path and stores in key
	public static void load(String path, String key) {
		if(clips.get(key) != null) return;
		
		Clip clip;
		try {
			InputStream in = JukeBox.class.getResourceAsStream(path);
			InputStream bin = new BufferedInputStream(in);
			AudioInputStream ais =
				AudioSystem.getAudioInputStream(bin);
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
				AudioFormat.Encoding.PCM_SIGNED,
				baseFormat.getSampleRate(),
				16,
				baseFormat.getChannels(),
				baseFormat.getChannels() * 2,
				baseFormat.getSampleRate(),
				false
			);
			
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
			clips.put(key, clip);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	// play functions
	public static void play(String key) {
		play(key, frame);
	}
	
	public static void play(String key, int frame) {
		Clip c = clips.get(key);
		if (c == null) return;
		if (c.isRunning()) c.stop();
		c.setFramePosition(frame);
		while (!c.isRunning()) c.start(); 
	}
	
	// stop audio
	public static void stop(String key) {
		if(clips.get(key) == null) return;
		if(clips.get(key).isRunning()) clips.get(key).stop(); 
	}
	
	// resume functions
	public static void resume(String key) {
		if (clips.get(key).isRunning()) return; 
		clips.get(key).start();
	}
	
	public static void resumeLoop(String key) {
		Clip c = clips.get(key); 
		if(c == null) return;
		c.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	// loop functions
	public static void loop(String key) {
		loop(key, frame, frame, clips.get(key).getFrameLength() - 1);
	}
	
	public static void loop(String key, int currFrame) {
		loop(key, currFrame, frame, clips.get(key).getFrameLength() - 1); 
	}
	
	public static void loop(String key, int start, int end) {
		loop(key, frame, start, end);
	}
	
	public static void loop(String key, int frame, int start, int end) {
		Clip c = clips.get(key);
		if(c == null) return;
		if(c.isRunning()) c.stop();
		c.setLoopPoints(start, end);
		c.setFramePosition(frame);
		c.loop(Clip.LOOP_CONTINUOUSLY);  
	}
	
	// set position in audio file
	public static void setPosition(String s, int frame) {
		clips.get(s).setFramePosition(frame);
	}
	
	public static void close(String s) {
		stop(s);
		clips.get(s).close();
	}
	
	public static void setVolume(String s, float f) {
		Clip c = clips.get(s);
		if(c == null) return;
		FloatControl vol = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
		vol.setValue(f);
	}
	
	public static boolean isPlaying(String s) {
		Clip c = clips.get(s);
		if(c == null) return false;
		return c.isRunning();
	}
	
	public static int getFrames(String s) { return clips.get(s).getFrameLength(); }
	
	public static int getPosition(String s) { return clips.get(s).getFramePosition(); }
	
}
