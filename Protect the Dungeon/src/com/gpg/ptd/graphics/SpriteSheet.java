package com.gpg.ptd.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private String path;
	private String name;
	public final int SIZE;
	public int[] pixels;
	
	public static SpriteSheet textures = new SpriteSheet("/textures.png", "textures", 512);
	public static SpriteSheet items = new SpriteSheet("/items.png", "items", 512);
	public static SpriteSheet mobs = new SpriteSheet("/mobs.png", "mobs", 512);
	public static SpriteSheet font = new SpriteSheet("/font.png", "font", 128);
	public static SpriteSheet fontOutline = new SpriteSheet("/font_out.png", "font_outline", 128);
	public static SpriteSheet gui = new SpriteSheet("/gui.png", "gui", 256);
	
	//public static SpriteSheet mask = new SpriteSheet("/mask.png", "gui", 128);
	
	public SpriteSheet(String path, String name, int size){
		this.path = path;
		this.name = name;
		this.SIZE = size;
		pixels = new int[size * size];
		load();
	}
	
	private void load(){
		try{
			BufferedImage image = ImageIO.read(this.getClass().getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		}catch(IOException e){
			System.err.println("Error: Could not load texture " + name + " @ " + path);
			e.printStackTrace();
		}
	}
	
	public String getName(){
		return name;
	}
}
