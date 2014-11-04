package com.gpg.ptd.entity.particle;

import java.util.Random;

import com.gpg.ptd.graphics.Font;
import com.gpg.ptd.graphics.Screen;
import com.gpg.ptd.level.Dungeon;

public class BloodParticle extends Particle {
	
	public BloodParticle(int id, int x, int y, int col, int size, double force,  Dungeon dungeon, Random random) {
		super(id, x, y, dungeon, random);
		this.size = size + random.nextInt(2);
		
		int r = random.nextInt(10);
		if(r <= 10) this.col = 0xff752323;
		if(r <= 7) this.col = 0xff912C2C;
		if(r <= 3) this.col = 0xff6E1010;
		if(r <= 1) this.col = 0xff4A0C0C;
				
		xx = x;
		yy = y;
		zz = 20;
		xa = random.nextGaussian() * force;
		ya = random.nextGaussian() * force;
		za = random.nextFloat();
	}
	
	public void update(){
		life++;

		xx += xa;
		yy += ya;
		zz += za;
				
		if(zz < 0){
			zz = 0;
			za *= -0.5;
			xa *= 0.3;
			ya *= 0.3;
		}
		
		za -= 0.1;
		
//		x = (int) xx;
//		y = (int) yy;
		move(xx, yy);
	}
	
	public void render(int xScroll, int yScroll, Font font, Screen screen){
		
		for(int yb = 0; yb < size; yb++){
			for(int xb = 0; xb < size; xb++){
				screen.renderPixel((xb + x + 16) - xScroll, (yb + y + 16) - yScroll - (int) (zz), col);
			}
		}
		
//		for(int c = 0; c < 4; c++){
//			int xt = ((x - xScroll) + c % 2 * 1 + 4);
//			int yt = ((y - yScroll) + c / 2 * 1 + 4);
//			screen.renderPixel(xt, yt + 1, 0xffffff00);
//			screen.renderPixel(xt, yt - 1, 0xffffff00);
//			screen.renderPixel(xt + 1, yt, 0xffffff00);
//			screen.renderPixel(xt - 1, yt, 0xffffff00);
//		}
	}
}
