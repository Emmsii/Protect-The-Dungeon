package com.gpg.ptd.level.object;

import com.gpg.ptd.level.Dungeon;

public class Trap extends Obj{
	
	protected Dungeon dungeon;
	
	public Trap(int id, int x, int y, Dungeon dungeon) {
		super(id, x, y);
		this.dungeon = dungeon;
	}

	public void update(){
		
	}

}
