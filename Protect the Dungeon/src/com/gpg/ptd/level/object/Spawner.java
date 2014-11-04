package com.gpg.ptd.level.object;

import com.gpg.ptd.level.Dungeon;

public class Spawner extends Obj{

	protected Dungeon dungeon;
		
	protected int amount;
	
	int time = 0;
	
	public Spawner(int id, int x, int y, Dungeon dungeon){
		super(id, x, y);
		this.dungeon = dungeon;
	}

	
	public void update(){
		time++;
		if(time % 60 == 0) spawnMobs();
	}
	
	private void spawnMobs(){
		if(amount > 6) return;
		if(amount < 2){
			for(int i = 0; i < 6; i++){
				dungeon.addMob(x, y, this);
			}
		}
	}

	public void remove(int a){
		amount -= a;
	}

	public int getAmount() {
		return amount;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}
}
