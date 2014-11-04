package com.gpg.ptd.entity.item;

import java.util.Random;

import com.gpg.ptd.entity.particle.TextParticle;
import com.gpg.ptd.graphics.Screen;
import com.gpg.ptd.graphics.SpriteSheet;
import com.gpg.ptd.level.Dungeon;

public class Gold extends Item{

	protected int amount;
	
	
	public Gold(int id, int x, int y, int amount, Dungeon dungeon, Random random) {
		super(id, x, y, dungeon, random);
		this.amount = amount;
	}
	
	public void update(){
		if((dungeon.getPlayer().getX() + 16) / 32 == x / 32 && (dungeon.getPlayer().getY() + 16) / 32 == y / 32){
			dungeon.addParticle(new TextParticle(dungeon.getParticles().size(), x, y, "+" + amount, 0xffE0A341, 2, 0.4, dungeon, random));
			dungeon.getPlayer().addGold(amount);
			removed = true;
		}
	}
	
	public void render(int xScroll, int yScroll, Screen screen){
		screen.render(x - xScroll, y - yScroll, 0, 0, 32, 1, SpriteSheet.items);
	}
	
	public String getAmount(){
		String result = "";
		
		if(amount < 1000) result = Integer.toString(amount);
		if(amount >= 1000) result = Integer.toString(amount / 1000) + "k";
		if(amount >= 1000000) result = Integer.toString(amount / 1000000) + "m";
		if(amount >= 1000000000) result = Integer.toString(amount / 1000000000) + "b";
		
		return result;
	}

	
}
