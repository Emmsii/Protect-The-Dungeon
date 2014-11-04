package com.gpg.ptd.entity.mob;

import java.util.Random;

import com.gpg.ptd.graphics.Screen;
import com.gpg.ptd.graphics.SpriteSheet;
import com.gpg.ptd.level.Dungeon;
import com.gpg.ptd.util.Pathfinder;
import com.gpg.ptd.util.Vector2i;

public class Orc extends HostileMob{
	
	public Orc(int id, int x, int y, String name, int health, Dungeon dungeon, Pathfinder pathfinder, Random random) {
		super(id, x, y, name, health, dungeon, pathfinder, random);
		weight = 10;
		
		collision_width = 16;
		collision_height = 28;
		collision_w_offset = 8;
		collision_h_offset = 2;
		
	}

	public void update(){
		if(lastX == x && lastY == y) moving = false;
		lastX = x;
		lastY = y;
		collision.setPos(x + 5, y + 5);
		if(target == null) wanderSpawner();
		else{
			pathTo(new Vector2i(x / 32, y / 32), new Vector2i((target.getX() + 16)/ 32, (target.getY() + 16) / 32));	
			attDir = dir;
			attack(attDir, random.nextInt(4));
		}
		animation();
		calculateBobbing();
		if(knockback > 0.0f) calculateKnockback();
		
		if(attackTime > 0) attackTime--;
		
//		pathTo(new Vector2i(x / 32, y / 32), new Vector2i(dungeon.getPlayer().getX() / 32, dungeon.getPlayer().getY() / 32));

		time++;
		tileX = (x + 16) / 32;
		tileY = (y + 16) / 32;
	}
	
	public void render(int xScroll, int yScroll, Screen screen){
		if(attackTime > 0){
			if(attDir == 0) screen.render(x - xScroll, (int) (y - yScroll - 15 - bob), 1, 2, 32, 1, SpriteSheet.textures);
			if(attDir == 1) screen.render(x - xScroll + 19, (int) (y - yScroll + 6 - bob), 0, 2, 32, 1, SpriteSheet.textures);
			if(attDir == 3) screen.render(x - xScroll - 19, (int) (y - yScroll + 6 - bob), 3, 2, 32, 1, SpriteSheet.textures);
		}
		screen.render((x) - xScroll, (int) ((y) - yScroll - bob), 0 + frame, 1 + dir, 32, 1, SpriteSheet.mobs);
		if(attackTime > 0){
			if(attDir == 2) screen.render(x - xScroll - 1, (int) (y - yScroll + 23 - bob), 2, 2, 32, 1, SpriteSheet.textures);
		}
		
		//Render collision box
//		for(int c = 0; c < 4; c++){
//			int xt = ((x - xScroll) + c % 2 * collision_width + collision_w_offset);
//			int yt = ((y - yScroll) + c / 2 * collision_height + collision_h_offset);
//			screen.renderPixel(xt, yt + 1, 0xffffff00);
//			screen.renderPixel(xt, yt - 1, 0xffffff00);
//			screen.renderPixel(xt + 1, yt, 0xffffff00);
//			screen.renderPixel(xt - 1, yt, 0xffffff00);
//		}
		
		for(int i = 0; i < 4; i++) screen.renderPixel(collision.getX() - xScroll + i, collision.getY() - yScroll + i, 0xffffff00);
		for(int i = 0; i < 4; i++) screen.renderPixel(collision.getX() - xScroll + i, collision.getY() + collision.getHeight() - yScroll + i, 0xffffff00);
		for(int i = 0; i < 4; i++) screen.renderPixel(collision.getX() + collision.getWidth() - xScroll + i, collision.getY() - yScroll + i, 0xffffff00);
		for(int i = 0; i < 4; i++) screen.renderPixel(collision.getX() + collision.getWidth() - xScroll + i, collision.getY() + collision.getHeight() - yScroll + i, 0xffffff00);
		
		
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
	

	
}
