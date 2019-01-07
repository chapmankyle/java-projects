// Used to draw game

package com.chaps.RetroHunter;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.chaps.RetroHunter.Manager.GameStateManager;
import com.chaps.RetroHunter.Manager.Keys;
import com.chaps.RetroHunter.Manager.Mouse;

@SuppressWarnings("serial") 
public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener {
	
	// game dimensions
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600; 
	
	// game loop variables
	private Thread thread;
	private boolean running;
	private final int FPS = 55;
	private final int TARGET_TIME = 1000 / FPS; 
	
	// drawing utensils
	private BufferedImage image;
	private Graphics2D g;
	
	// game state manager
	private GameStateManager gsm;
	
	// default constructor
	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
	}
	
	// creates and starts thread
	public void addNotify() {
		super.addNotify();
		
		if (thread == null) {
			addKeyListener(this); 
			addMouseListener(this); 
			thread = new Thread(this);
			thread.start();
		}
	}
	
	// main run loop
	public void run() {
		init();
		
		long start;
		long elapsed;
		long wait;
		
		while (running) {
			start = System.nanoTime();
			
			update();
			draw();
			render();
			
			elapsed = System.nanoTime() - start;
			wait = TARGET_TIME - (elapsed / 1000000); 
			
			// so that we at least wait a few milliseconds
			if (wait <= 0) {
				wait = 5;
			}
			
			try {
				Thread.sleep(wait);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
	} 
	
	// initialise function
	public void init() {
		running = true;
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		gsm = new GameStateManager(); 
	}
	
	// updates game to current game state
	public void update() {
		gsm.update();
		Keys.update();
	}
	
	// copies current game state to buffered image
	public void draw() {
		gsm.draw(g); 
	}
	
	// draws current game state
	public void render() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH, HEIGHT, null); 
		g2.dispose();
	}
	
	// key events
	public void keyPressed(KeyEvent e) {
		Keys.setKey(e.getKeyCode(), true);
	}
	
	public void keyReleased(KeyEvent e) {
		Keys.setKey(e.getKeyCode(), false); 
	}
	
	public void keyTyped(KeyEvent e) { }
	
	// mouse events
	public void mousePressed(MouseEvent e) { 
		Mouse.setPos(e.getX(), e.getY(), true); 
	}
	
	public void mouseReleased(MouseEvent e) { 
		Mouse.setPos(e.getX(), e.getY(), false);
	}
	
	public void mouseClicked(MouseEvent e) { 
		if (e.getClickCount() == 2) {
			Mouse.setDoubleClicked(true);
		}
		else {
			Mouse.setDoubleClicked(false);
		}
	}
	
	public void mouseEntered(MouseEvent e) { }
	
	public void mouseExited(MouseEvent e) { }
}
