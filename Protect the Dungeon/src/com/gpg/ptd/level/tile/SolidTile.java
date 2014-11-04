package com.gpg.ptd.level.tile;

import com.gpg.ptd.graphics.Screen;
import com.gpg.ptd.graphics.Sprite;

public class SolidTile extends Tile{

	public SolidTile(Sprite sprite, String name){
		super(sprite, name);
		solid = true;
	}

	public void render(int x, int y, int color, Screen screen){
		screen.renderTile(x * 32, y * 32, color, this);
	}

}
