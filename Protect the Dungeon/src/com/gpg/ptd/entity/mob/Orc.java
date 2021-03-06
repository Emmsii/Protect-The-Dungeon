package com.gpg.ptd.entity.mob;

import java.util.Random;

import com.gpg.ptd.graphics.Screen;
import com.gpg.ptd.graphics.SpriteSheet;
import com.gpg.ptd.level.Dungeon;
import com.gpg.ptd.util.Pathfinder;
import com.gpg.ptd.util.Vector2i;

public class Orc extends HostileMob{
	
	boolean canSeePlayer;
	public Orc(int id, int x, int y, String name, int health, Dungeon dungeon, Pathfinder pathfinder, Random random) {
		super(id, x, y, name, health, dungeon, pathfinder, random);
		weight = 10;

		collision_width = 15;
		collision_height = 27;
		collision_w_offset = 8;
		collision_h_offset = 3;
	}

	public void update(){
		if(lastX == x && lastY == y) moving = false;
		lastX = x;
		lastY = y;
		
		if(attackTime > 0){
			attackTime--;
			weaponFrame++;
			speed = speed / 2;
		}
		
		if(pathfinder.line(x + 16, y + 16, dungeon.getPlayer().getX() + 16, dungeon.getPlayer().getY() + 16)){
			canSeePlayer = false;
			target = null;
		}
		else{
			canSeePlayer = true;
			target = dungeon.getPlayer();
		}
		
		
		if(target == null){
			wanderSpawner();
		}else{
			pathTo(new Vector2i(x / 32, y / 32), new Vector2i(target.getX() / 32, target.getY() / 32), 0);
//			if(attackTime == 0){
//				attDir = -1;
//				weaponFrame = 0;
//			}
//			
//			if(attackTime == 0){
//				attDir = dir;
//			}
//			
//			if(attackTime == 10){
//				attack(attDir, random.nextInt(5));
//			}
		
		
		}
		animation();
		calculateBobbing();

		if(knockback > 0.0f) calculateKnockback();
		if(attackTime > 0) attackTime--;

		time++;
		tileX = (x + 16) / 32;
		tileY = (y + 16) / 32;

	}
	
	public void render(int xScroll, int yScroll, Screen screen){
		if(attDir == 0) screen.render(x - xScroll, (int) (y - yScroll - 15 - bob), 1, 2, 32, 1, SpriteSheet.textures);
		if(attDir == 1) screen.render(x - xScroll + 19, (int) (y - yScroll + 6 - bob), 0, 2, 32, 1, SpriteSheet.textures);
		if(attDir == 3) screen.render(x - xScroll - 19, (int) (y - yScroll + 6 - bob), 3, 2, 32, 1, SpriteSheet.textures);
		screen.render((x) - xScroll, (int) ((y) - yScroll - bob), 0 + frame, 1 + dir, 32, 1, SpriteSheet.mobs);
		if(attDir == 2) screen.render(x - xScroll - 1, (int) (y - yScroll + 23 - bob), 2, 2, 32, 1, SpriteSheet.textures);
		
		//Render collision box
//		if(canSee){
//			for(int c = 0; c < 4; c++){
//				int xt = ((x - xScroll) + c % 2 * collision_width + collision_w_offset);
//				int yt = ((y - yScroll) + c / 2 * collision_height + collision_h_offset);
//				screen.renderPixel(xt, yt + 1, 0xffffff00);
//				screen.renderPixel(xt, yt - 1, 0xffffff00);
//				screen.renderPixel(xt + 1, yt, 0xffffff00);
//				screen.renderPixel(xt - 1, yt, 0xffffff00);
//			}
//		}
		
		if(canSeePlayer){
			for(int c = 0; c < 4; c++){
				int xt = ((x - xScroll) + c % 2 * collision_width + collision_w_offset);
				int yt = ((y - yScroll) + c / 2 * collision_height + collision_h_offset);
				screen.renderPixel(xt, yt + 1, 0xffffff00);
				screen.renderPixel(xt, yt - 1, 0xffffff00);
				screen.renderPixel(xt + 1, yt, 0xffffff00);
				screen.renderPixel(xt - 1, yt, 0xffffff00);
			}
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
	

	
}
