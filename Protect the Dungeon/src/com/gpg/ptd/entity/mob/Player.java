package com.gpg.ptd.entity.mob;

import java.util.Random;

import com.gpg.ptd.entity.item.Inventory;
import com.gpg.ptd.graphics.Screen;
import com.gpg.ptd.graphics.SpriteSheet;
import com.gpg.ptd.level.Dungeon;
import com.gpg.ptd.util.Key;

public class Player extends Mob{

	protected Key key;
	
	protected String name;
	protected int strength;
	protected int archery;
	protected int magic;
	
	protected int score;
		
	public Player(int id, int x, int y, String name, int health, Dungeon dungeon, Key key, Random random) {
		super(id, x, y, name, health, dungeon, null, random);
		this.name = name;
		this.key = key;
		
		health = 100000;
		
		collision_width = 22;
		collision_height = 22;
		collision_w_offset = 6;
		collision_h_offset = 6;
		
		weight = 10;
		
		/**
		 *     - FOR TAM -
		 * Database Requirements
		 * 
		 * Need to store and load character inventory.
		 * 
		 */
		
		inventory = new Inventory(this);
	}

	public void update(){
		key.update();
		int xa = 0;
		int ya = 0;
		
		if(knockback > 0.0f) calculateKnockback();
		animation();
		calculateBobbing();
		
		
		if(key.shift && moving){
			if(energy > 0.0f){
				if(speed < MAX_SPEED) speed += 0.05f;
				else speed = MAX_SPEED;
				float e = 0.01f + ((weight * 0.01f) + (speed * 0.01f) * 0.25f);
				if(energy - e > 0.0f) energy -= e;
				else energy = 0.0f;
			}else{
				speed = MIN_SPEED;
			}
		}else{
			if(speed > MIN_SPEED) speed -= 0.05f;
			else speed = MIN_SPEED;
			float e = 0.01f;
			if(energy + e < 100.0f) energy += e;
			else energy = 100.0f;
		}
		
		if(!moving){
			float e = 0.0175f;
			if(energy + e < 100.0f) energy += e;
			else energy = 100.0f;
		}
		
		if(attackTime > 0){
			attackTime--;
			speed = speed / 2;
		}
		
		if(health <= 0) setDead(true);
		
		if(key.w) ya -= speed;
		if(key.s) ya += speed;
		if(key.a) xa -= speed;
		if(key.d) xa += speed;
				
		attDir = -1;
		if(key.up) attDir = 0;
		if(key.right) attDir = 1;
		if(key.down) attDir = 2;
		if(key.left) attDir = 3;
		if(attDir != -1){
			attack(attDir, random.nextInt(20));
			dir = attDir;
		}
		
		if(xa != 0 || ya != 0) move(xa, ya);
		else moving = false;

		tileX = (x + 16) / 32;
		tileY = (y + 16) / 32;
	}
		
	public void render(int xScroll, int yScroll, Screen screen){
		if(attackTime > 0){
			if(attDir == 0) screen.render(x - xScroll, (int) (y - yScroll - 15 - bob), 1, 2, 32, 1, SpriteSheet.textures);
			if(attDir == 1) screen.render(x - xScroll + 19, (int) (y - yScroll + 6 - bob), 0, 2, 32, 1, SpriteSheet.textures);
			if(attDir == 3) screen.render(x - xScroll - 19, (int) (y - yScroll + 6 - bob), 3, 2, 32, 1, SpriteSheet.textures);
		}
		
		screen.render((x) - xScroll, (int) ((y) - yScroll - bob), 2 + frame, 1 + dir, 32, 1, SpriteSheet.mobs);
		
		if(attackTime > 0){
			if(attDir == 2) screen.render(x - xScroll - 1, (int) (y - yScroll + 23 - bob), 2, 2, 32, 1, SpriteSheet.textures);
		}
		
		for(int c = 0; c < 4; c++){
			int xt = ((x - xScroll) + c % 2 * collision_width + collision_w_offset);
			int yt = ((y - yScroll) + c / 2 * collision_height + collision_h_offset);
			screen.renderPixel(xt, yt + 1, 0xffffff00);
			screen.renderPixel(xt, yt - 1, 0xffffff00);
			screen.renderPixel(xt + 1, yt, 0xffffff00);
			screen.renderPixel(xt - 1, yt, 0xffffff00);
		}
		
		if(health < 100){
			for(int ya = 0; ya < 3; ya++){
				for(int xa = 0; xa < 32; xa++){
					screen.renderPixel(x + xa - xScroll, 32 + y + ya- yScroll, 0xffff0000);
				}
			}
			
			for(int ya = 0; ya < 3; ya++){
				for(int xa = 0; xa < convertHealth(); xa++){
					screen.renderPixel(x + xa - xScroll, 32 + y + ya- yScroll, 0xff00ff00);
				}
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addGold(int g){
		inventory.addGold(g);
	}
	
	public int takeGold(int g){
		return inventory.takeGold(g);
	}

	public int getAttackTime() {
		return attackTime;
	}

	public void setAttackTime(int attackTime) {
		this.attackTime = attackTime;
	}

	public int getScore(){
		return score;
	}
	
	public void addScore(int s){
		score += s;
	}
}

