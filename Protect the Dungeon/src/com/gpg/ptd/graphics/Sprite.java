package com.gpg.ptd.graphics;

public class Sprite {

	public int size;
	public int w, h;
	public int x, y;
	public int[] pixels;
	private SpriteSheet sheet;
	
	public static Sprite voidTile = new Sprite(32, 0x00CC99FF);
	
	public static Sprite wallTile = new Sprite(32, 0, 0, SpriteSheet.textures);
	public static Sprite floorTile = new Sprite(32, 1, 0, SpriteSheet.textures);
	public static Sprite rockTile = new Sprite(32, 2, 0, SpriteSheet.textures);
	public static Sprite doorTD = new Sprite(32, 3, 0, SpriteSheet.textures);
	public static Sprite doorLR = new Sprite(32, 4, 0, SpriteSheet.textures);
	
	public static Sprite trap = new Sprite(32, 0, 1, SpriteSheet.textures);
	public static Sprite spawner = new Sprite(16, 4, 2, SpriteSheet.gui);
	
	public static Sprite doorIcon = new Sprite(16, 2, 2, SpriteSheet.gui);
	public static Sprite trapIcon = new Sprite(16, 3, 2, SpriteSheet.gui);
	public static Sprite spawnerIcon = new Sprite(16, 4, 2, SpriteSheet.gui);
	public static Sprite deleteIcon = new Sprite(16, 2, 3, SpriteSheet.gui);
	
	public static Sprite player = new Sprite(32, 0, 0, SpriteSheet.mobs);
	
	public Sprite(int size, int x, int y, SpriteSheet sheet){
		this.size = size; 
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		pixels = new int[size * size];
		load();
	}
	
	public Sprite(int size, int color){
		this.size = size;
		pixels = new int[size * size];
		setColor(color);
	}
	
	private void load(){
		for(int y = 0; y < size; y++){
			for(int x = 0; x < size; x++){
				pixels[x + y * size] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE];
			}
		}
	}
	
	public void setColor(int color){
		for(int i = 0; i < size * size; i++) pixels[i] = color;
	}
	
}
