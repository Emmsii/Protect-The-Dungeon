package com.gpg.ptd.entity.item;

import java.util.Random;

import com.gpg.ptd.entity.Entity;
import com.gpg.ptd.graphics.Screen;
import com.gpg.ptd.level.Dungeon;

public class Item extends Entity{

	protected boolean removed;
	
	public Item(int id, int x, int y, Dungeon dungeon, Random random) {
		super(id, x, y, dungeon, random);
	}


	public void render(int xScroll, int yScroll, Screen screen){
		
	}

	public void update(){
		
	}

	public boolean isRemoved() {
		return removed;
	}


	public void setRemoved(boolean removed) {
		this.removed = removed;
	}
}
