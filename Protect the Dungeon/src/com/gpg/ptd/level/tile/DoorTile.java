package com.gpg.ptd.level.tile;

import com.gpg.ptd.graphics.Screen;
import com.gpg.ptd.graphics.Sprite;

public class DoorTile extends Tile{

	public DoorTile(Sprite sprite, String name) {
		super(sprite, name);
	}
	
	public void update(){
		
	}

	public void render(int x, int y, int col, Screen screen){
		screen.renderTile(x * 32, y * 32, col, this);
		
	}
	
	
	
}
