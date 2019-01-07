package com.chaps.Pong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.chaps.Pong.Entities.Ball;
import com.chaps.Pong.Entities.Player;

public class Game extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private static final int CANVAS_WIDTH = 800;
	private static final int CANVAS_HEIGHT = 600;
	private static final Color CANVAS_BG_COLOR = new Color(78, 78, 78);
	private static final int SCALE = 1;
	private static final String NAME = "Pong: Dream Edition";
	
	private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	private static final int SCREEN_WIDTH = (int)SCREEN_SIZE.getWidth();
	private static final int SCREEN_HEIGHT = (int)SCREEN_SIZE.getHeight();
	
	private static final int PLAYER_WIDTH = 10;
	private static final int PLAYER_HEIGHT = 100;
	private static final int BALL_SIZE = 10;
	
	private float playerSpeed = 2f;
	private float ballSpeed = 2f;
	
	private DrawCanvas canvas;
	private JLabel lblScore;
	private Player player1;
	private Player player2;
	private Ball ball;
	private InputHandler input;
	
	private boolean movingUp = true;
	private boolean movingLeft = true;
	
	private int score1 = 0;
	private int score2 = 0;
	
	public boolean running = false;
	
	public Game() {
		player1 = new Player(20, (CANVAS_HEIGHT / 2) - (PLAYER_HEIGHT / 2), PLAYER_WIDTH, PLAYER_HEIGHT);
		player2 = new Player(CANVAS_WIDTH - PLAYER_WIDTH - 20, (CANVAS_HEIGHT / 2) - (PLAYER_HEIGHT / 2), PLAYER_WIDTH, PLAYER_HEIGHT);
		ball = new Ball((CANVAS_WIDTH / 2) - (BALL_SIZE / 2), (CANVAS_HEIGHT / 2) - (BALL_SIZE / 2), BALL_SIZE, BALL_SIZE);
		input = new InputHandler(this);
		lblScore = new JLabel(score1 + "  |  " + score2);
		lblScore.setFont(new Font("Arial", Font.BOLD, 18));
		lblScore.setForeground(Color.WHITE);
		lblScore.setLocation(CANVAS_WIDTH / 2 - 20, 25);
		
		// creating canvas to work with
		canvas = new DrawCanvas();
		canvas.setPreferredSize(new Dimension(CANVAS_WIDTH * SCALE, CANVAS_HEIGHT * SCALE));
		
		add(canvas); // add canvas to JFrame
		
		// creating JFrame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation((SCREEN_WIDTH / 2) - (CANVAS_WIDTH / 2), (SCREEN_HEIGHT / 2) - (CANVAS_HEIGHT / 2));
		setResizable(false);
		setTitle(NAME);
		pack();
		setVisible(true);
		requestFocus();
		
		canvas.add(lblScore);
	}
	
	// main game loop
	public void run() {
		while (running) {
			update();
			
			repaint();
			try {
				Thread.sleep(11);
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	public void update() {
		checkCollisions();
		
		movePlayer();
		moveBall();
	}
	
	public void movePlayer() {
		if (input.W.isPressed()) {
			if (player1.y >= 0) {
				player1.y -= playerSpeed;
			}
		}
		if (input.S.isPressed()) {
			if (player1.y <= (CANVAS_HEIGHT - player1.height)) {
				player1.y += playerSpeed;
			}
		}
		if (input.up.isPressed()) {
			if (player2.y >= 0) {
				player2.y -= playerSpeed;
			}
		}
		if (input.down.isPressed()) {
			if (player2.y <= (CANVAS_HEIGHT - player2.height)) {
				player2.y += playerSpeed;
			}
		}
		
		player1.setBounds(player1.x, player1.y, player1.width, player1.height);
		player2.setBounds(player2.x, player2.y, player2.width, player2.height);
	}
	
	public void moveBall() {
		checkCollisions();
		
		if (movingLeft) {
			if (ball.x > 0) {
				ball.x -= ballSpeed;
			}
			else {
				score2++;
				reset();
			}
		}
		if (!movingLeft) {
			if (ball.x < CANVAS_WIDTH - BALL_SIZE) {
				ball.x += ballSpeed;
			}
			else {
				score1++;
				reset();
			}
		}
		
		if (movingUp) {
			if (ball.y > 0) {
				ball.y -= ballSpeed;
			}
			else {
				movingUp = false;
			}
		}
		if (!movingUp) {
			if (ball.y < CANVAS_HEIGHT - BALL_SIZE) {
				ball.y += ballSpeed;
			}
			else {
				movingUp = true;
			}
		}
		
		ball.setBounds(ball.x, ball.y, ball.width, ball.height);
	}
	
	public void checkCollisions() {
		if (player1.getBounds().intersects(ball.getBounds())) {
			movingLeft = false;
		}
		if (player2.getBounds().intersects(ball.getBounds())) {
			movingLeft = true;
		}
	}
	
	public void reset() {
		ball.x = (CANVAS_WIDTH / 2) - (BALL_SIZE / 2);
		ball.y = (CANVAS_HEIGHT / 2) - (BALL_SIZE / 2);
		
		player1.x = 20;
		player2.x = (CANVAS_WIDTH - 20) - PLAYER_WIDTH;
		player1.y = (CANVAS_HEIGHT / 2) - (PLAYER_HEIGHT / 2);
		player2.y = (CANVAS_HEIGHT / 2) - (PLAYER_HEIGHT / 2);
		player1.setBounds(player1.x, player1.y, player1.width, player1.height);
		player2.setBounds(player2.x, player2.y, player2.width, player2.height);
		lblScore.setText(score1 + "  |  " + score2);
	}
	
	public synchronized void start() {
		running = true;
		new Thread(this).start();
	}
	
	public synchronized void stop() {
		running = false;
	}
	
	private class DrawCanvas extends JPanel {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setBackground(CANVAS_BG_COLOR);
			player1.paint(g);
			player2.paint(g);
			ball.paint(g);
		}
	}
	
	public static void main(String[] args) {
		new Game().start();
	}
}
