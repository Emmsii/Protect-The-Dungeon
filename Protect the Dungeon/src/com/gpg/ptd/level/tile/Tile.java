package com.gpg.ptd.level.tile;

import com.gpg.ptd.graphics.Screen;
import com.gpg.ptd.graphics.Sprite;

public class Tile {

	public int x, y;
	public boolean solid;
	public Sprite sprite;
	public int color;
	
	public String name;
	
	public static Tile voidTile = new SolidTile(Sprite.voidTile, "void");
	
	public static Tile wallTile = new SolidTile(Sprite.wallTile, "wall");
	public static Tile floorTile = new EmptyTile(Sprite.floorTile, "floor");
	public static Tile rockTile = new SolidTile(Sprite.rockTile, "rock");
	
	public static Tile trapTile = new EmptyTile(Sprite.trap, "trap");
	public static Tile spawnerTile = new SolidTile(Sprite.spawner, "spawner");
	
	public static Tile doorTileTD = new DoorTile(Sprite.doorTD, "doorTD");
	public static Tile doorTileLR = new DoorTile(Sprite.doorLR, "doorLR");
	
	public Tile(Sprite sprite, String name){
		this.sprite = sprite;
		this.name = name;
	}

	public void update(){
		
	}
	
	public void render(int x, int y, int color, Screen screen){
		
	}
	
	public void play(){
		
	}
	
	public boolean solid(){
		return solid;
	}
	
	public String getName(){
		return name;
	}
}
