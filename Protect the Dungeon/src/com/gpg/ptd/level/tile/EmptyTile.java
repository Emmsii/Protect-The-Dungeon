package com.gpg.ptd.level.tile;

import com.gpg.ptd.graphics.Screen;
import com.gpg.ptd.graphics.Sprite;

public class EmptyTile extends Tile{

	public EmptyTile(Sprite sprite, String name) {
		super(sprite, name);
		solid = false;
	}
	
	public void render(int x, int y, int color, Screen screen){
		screen.renderTile(x * 32, y * 32, color, this);
	}

}
