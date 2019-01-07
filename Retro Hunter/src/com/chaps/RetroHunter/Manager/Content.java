package com.chaps.RetroHunter.Manager;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Content {
	
	// creating all accessible items
	public static BufferedImage[][] ICON_16 = load("/icons/icon_16.png", 16, 31); 
	public static BufferedImage[][] ICON_32 = load("/icons/icon_32_outline.png", 32, 46);
	
	public static BufferedImage[][] MENU_BG = load("/HUD/bgmenu.png", 800, 600);
	public static BufferedImage[][] OPTIONS_BG = load("/HUD/options.png", 800, 600);
	public static BufferedImage[][] PAUSE_BG = load("/HUD/pause.png", 400, 600);
	
	public static BufferedImage[][] PLAYER = load("/sprites/player.png", 16, 30);
	public static BufferedImage[][] PLAYER_HALFPOINT = load("/sprites/player_halfpoint.png", 24, 40);
	public static BufferedImage[][] PLAYER_POINT = load("/sprites/player_point.png", 24, 40); 
	public static BufferedImage[][] PLAYER_FALL = load("/sprites/player_fall.png", 24, 40); 
	public static BufferedImage[][] PLAYER_LAND1 = load("/sprites/player_land1.png", 24, 40); 
	public static BufferedImage[][] PLAYER_LAND2 = load("/sprites/player_land2.png", 22, 37); 
	
	// used to load items into workspace
	public static BufferedImage[][] load(String path, int w, int h) {
		BufferedImage[][] item;
		
		try {
			BufferedImage sheet = ImageIO.read(Content.class.getResourceAsStream(path));
			int width = sheet.getWidth() / w;
			int height = sheet.getHeight() / h;
			//System.out.println("sheet width = " + sheet.getWidth() + ", sheet height = " + sheet.getHeight() + ", width = " + width + ", height = " + height);
			item = new BufferedImage[height][width];
			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					item[i][j] = sheet.getSubimage(j * w, i * h, w, h);
				}
			}
			return item; 
		}
		catch(Exception e) {
			System.err.println("Graphics error: " + e.getMessage());
			System.exit(0);
		}
		return null;
	}
	
}
