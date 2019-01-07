package com.chaps.Dodger;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Game extends JFrame {

	private static final long serialVersionUID = 1L;

	protected DecimalFormat df = new DecimalFormat("#.#");
	protected List<Double> times = new ArrayList<>(); // record all times

	public static final int CANVAS_WIDTH = 1280;
	public static final int CANVAS_HEIGHT = 720;
	public static final Color CANVAS_BG_COLOR = Color.WHITE;

	public static final int PAUSE_WIDTH = 400;
	public static final int PAUSE_HEIGHT = 500;

	public static final int PLAYER_WIDTH = 150;
	public static final int PLAYER_HEIGHT = 70;
	public static final int LINE_WIDTH = CANVAS_WIDTH;
	public static final int LINE_HEIGHT = 10;

	private DrawCanvas canvas;
	private Player player;
	private Line line1, line2, line3, line4;
	private Traffic traffic1, traffic2;
	private JPanel pauseScreen;

	protected int spdA, spdB;
	protected int first, second;

	protected int jump, tim, sec, i, pauseHit, countA, countB;
	protected int ync = 0; // y - yes, n - no, c - cancel

	protected String time;
	protected String playerPath = "./res/blue.png";
	protected String trafficPath = "./res/orange.png";
	protected double currentTime;
	protected double bestTime = 0;
	protected boolean beat = false;
	protected boolean paused = true;

	protected BufferedImage img;
	protected JLabel lblBg;

	// default constructor
	public Game() {
		player = new Player(PLAYER_WIDTH, CANVAS_HEIGHT / 2
				- (PLAYER_HEIGHT / 2), PLAYER_WIDTH, PLAYER_HEIGHT,
				playerPath);
		line1 = new Line(0, CANVAS_HEIGHT / 3 - (LINE_HEIGHT / 2),
				LINE_WIDTH, LINE_HEIGHT);
		line2 = new Line(0,
				(int) (CANVAS_HEIGHT / 1.5) - (LINE_HEIGHT / 2),
				LINE_WIDTH, LINE_HEIGHT);
		line3 = new Line(0, 0, LINE_WIDTH, LINE_HEIGHT);
		line4 = new Line(0, CANVAS_HEIGHT - LINE_HEIGHT, LINE_WIDTH,
				LINE_HEIGHT);
		spdA = 2;
		spdB = 1;
		makeTrafficA();
		makeTrafficB();
		jump = line2.y - line1.y;

		PrintWriter pw = null;
		try {
			pw = new PrintWriter("info.txt");
			pw.println("size: " + CANVAS_WIDTH + "p  x  " + CANVAS_HEIGHT
					+ "p");
			pw.println("line1: (" + line1.x + ", " + line1.y + ") ... "
					+ line1.width + "p  x  " + line1.height + "p");
			pw.println("line2: (" + line2.x + ", " + line2.y + ") ... "
					+ line2.width + "p  x  " + line2.height + "p");
			pw.println("line3: (" + line3.x + ", " + line3.y + ") ... "
					+ line3.width + "p  x  " + line3.height + "p");
			pw.print("line4: (" + line4.x + ", " + line4.y + ") ... "
					+ line4.width + "p  x  " + line4.height + "p");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		pw.close();

		// preparing the canvas
		canvas = new DrawCanvas();
		canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

		// adding canvas to frame
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(canvas, BorderLayout.CENTER);

		// adding pauseScreen
		pauseScreen = new JPanel();
		pauseScreen.setSize(PAUSE_WIDTH, PAUSE_HEIGHT);
		pauseScreen.setBackground(Color.CYAN);
		pauseScreen.setLocation((CANVAS_WIDTH / 2) - (PAUSE_WIDTH / 2),
				(CANVAS_HEIGHT / 2) - (PAUSE_HEIGHT / 2));

		// adding KeyListener
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE
						|| evt.getKeyCode() == KeyEvent.VK_P) {
					pause(true);
					pauseHit++;
				} else if (!paused) {
					switch (evt.getKeyCode()) {
					case KeyEvent.VK_W:
					case KeyEvent.VK_UP:
						moveUp(true);
						break;
					case KeyEvent.VK_S:
					case KeyEvent.VK_DOWN:
						moveDown(true);
						break;
					}
				}
			}
		});

		// preparing the frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(960 - (CANVAS_WIDTH / 2), 540 - (CANVAS_HEIGHT / 2));
		setTitle("Dodge The Traffic");
		pack(); // pack all the components into frame
		setVisible(true);
		setResizable(false);
		requestFocus();

		move();
	}

	private void moveUp(boolean isPressed) {
		if (isPressed
				&& (!isHit(player, traffic1) || !isHit(player, traffic2))) {
			int lastY = player.y;

			if (!(lastY - jump < 0)) {
				player.y -= jump;

				canvas.repaint(player.x, lastY, player.width,
						player.height);
				canvas.repaint(player.x, player.y, player.width,
						player.height);
			}
		}
	}

	private void moveDown(boolean isPressed) {
		if (isPressed
				&& (!isHit(player, traffic1) || !isHit(player, traffic2))) {
			int lastY = player.y;
			if (!(lastY + jump > CANVAS_HEIGHT)) {
				player.y += jump;

				canvas.repaint(player.x, lastY, player.width,
						player.height);
				canvas.repaint(player.x, player.y, player.width,
						player.height);
			}
		}
	}

	private void pause(boolean isPressed) {
		if (isPressed) {
			paused = true;
		}
	}

	private void makeTrafficA() {
		first = (int) (Math.random() * 3) + 1;

		int x = CANVAS_WIDTH;
		int y = CANVAS_HEIGHT / 2 - (PLAYER_HEIGHT / 2);
		int width = player.width;
		int height = player.height;

		if (countA >= 4) {
			countA = 0;
			spdA += (int) (Math.random() * 2) + 1;
		}

		if (spdA >= 10) {
			spdA = 10;
		}

		switch (first) {
		case 1:
			traffic1 = new Traffic(x, y - jump, width, height, spdA,
					trafficPath);
			break;
		case 2:
			traffic1 = new Traffic(x, y, width, height, spdA, trafficPath);
			break;
		case 3:
			traffic1 = new Traffic(x, y + jump, width, height, spdA,
					trafficPath);
			break;
		}
	}

	private void makeTrafficB() {
		second = (int) (Math.random() * 3) + 1;

		int x = CANVAS_WIDTH;
		int y = CANVAS_HEIGHT / 2 - (PLAYER_HEIGHT / 2);
		int width = player.width;
		int height = player.height;

		if (countB >= 3) {
			countB = 0;
			spdB += (int) (Math.random() * 3) + 1;

			while (spdB == spdA) {
				spdB += (int) (Math.random() * 3) + 1;
			}
		}

		if (spdB >= 10) {
			spdB = 10;
		}

		switch (second) {
		case 1:
			traffic2 = new Traffic(x, y - jump, width, height, spdB,
					trafficPath);
			break;
		case 2:
			traffic2 = new Traffic(x, y, width, height, spdB, trafficPath);
			break;
		case 3:
			traffic2 = new Traffic(x, y + jump, width, height, spdB,
					trafficPath);
			break;
		}
	}

	private void move() {
		tim = 0;
		sec = 0;
		times.add(0.0);
		i = 0;

		Thread thr = new Thread() {
			@Override
			public void run() {
				while (ync == 0) {
					makeTrafficA();
					makeTrafficB();
					player.x = PLAYER_WIDTH;
					player.y = (CANVAS_HEIGHT / 2)
							- (PLAYER_HEIGHT / 2);
					sec = 0;
					beat = false;
					countA = 0;
					countB = 0;
					while (true) {
						update();
						repaint();

						if (!paused) {
							try {
								Thread.sleep(5);
								tim += 5;
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}

						boolean hit = isHit(player, traffic1)
								|| isHit(player, traffic2);
						if (hit) {
							break;
						}

						if (tim >= 1000) {
							tim = 0;
							sec++;
						}

					}

					time = sec + "." + (tim / 100);
					currentTime = Double.parseDouble(time);
					times.add(currentTime);
					i++;

					boolean zero = false;
					if (bestTime == 0) {
						zero = true;
					}

					if (currentTime > bestTime) {
						bestTime = currentTime;
						beat = true;
					}

					if (beat && !zero) {
						JOptionPane.showMessageDialog(
								null,
								"You BEAT your best time!\t\t \n\nNEW BEST TIME: "
										+ bestTime
										+ " sec\nPrevious time: "
										+ times.get(i - 1)
										+ " sec");
					} else {
						if (sec >= 60) {
							JOptionPane.showMessageDialog(null,
									"You lasted:\n"
											+ (sec / 60)
											+ " min "
											+ (sec % 60)
											+ " sec");
						} else {
							JOptionPane.showMessageDialog(null,
									"You lasted:\n" + time
											+ " seconds");
						}
					}
					ync = JOptionPane.showConfirmDialog(null,
							"Have another go?\n "); // yes - 0, no
											// - 1, cancel
											// - 2
					spdA = 2;
					spdB = 1;
				}

				System.exit(0);
			}
		};
		thr.start();
	}

	private void update() {
		if (paused) {
			addPause();

			if (pauseHit != 1) {
				pauseHit = 0;
				paused = false;
				int a = traffic1.x;
				int b = traffic2.x;

				a -= spdA;
				b -= spdB;
				checkTraffic();

				if (a + traffic1.width <= 0) {
					a = CANVAS_WIDTH + 2;
					makeTrafficA();
					countA++;
					// spdA += //(int)(Math.random()*3);
				}
				if (b + traffic2.width <= 0) {
					b = CANVAS_WIDTH + 2;
					makeTrafficB();
					countB++;
					// spdB += (int)(Math.random()*3);
				}

				traffic1.x = a;
				traffic2.x = b;

				if (pauseScreen != null) {
					destroyPause();
				}
			}
		} else {
			int a = traffic1.x;
			int b = traffic2.x;

			a -= spdA;
			b -= spdB;
			checkTraffic();

			if (a + traffic1.width <= 0) {
				a = CANVAS_WIDTH + 2;
				makeTrafficA();
				countA++;
				// spdA += (int)(Math.random()*3);
			}
			if (b + traffic2.width <= 0) {
				b = CANVAS_WIDTH + 2;
				makeTrafficB();
				countB++;
				// spdB += (int)(Math.random()*3);
			}

			traffic1.x = a;
			traffic2.x = b;

			if (pauseScreen != null) {
				destroyPause();
			}
		}
	}

	public void checkTraffic() {
		if (traffic1.y == traffic2.y) {
			if (traffic1.x < traffic2.x) {
				switch (traffic2.y) {
				case 85:
					traffic2.y = 325;
					break;
				case 325:
					traffic2.y = 85;
					break;
				case 565:
					traffic2.y = 325;
					break;
				}
			} else if (traffic2.x < traffic1.x) {
				switch (traffic1.y) {
				case 85:
					traffic1.y = 325;
					break;
				case 325:
					traffic1.y = 85;
					break;
				case 565:
					traffic1.y = 325;
					break;
				}
			}

		}
	}

	public boolean isHit(Player p1, Traffic p2) {
		if (p2.getTrafficBounds().intersects(p1.getPlayerBounds())) {
			return true;
		} else {
			return false;
		}
	}

	public void addPause() {
		canvas.add(pauseScreen);
	}

	public void destroyPause() {
		canvas.remove(pauseScreen);
		canvas.repaint();
	}

	class DrawCanvas extends JPanel {
		private static final long serialVersionUID = 1L;

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setBackground(CANVAS_BG_COLOR);
			player.paint(g);
			line1.paint(g);
			line2.paint(g);
			line3.paint(g);
			line4.paint(g);
			traffic1.paint(g);
			traffic2.paint(g);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Game(); // start the new game
			}
		});
	}

}
