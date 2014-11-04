package com.gpg.ptd.entity.particle;

import java.util.Random;

import com.gpg.ptd.graphics.Font;
import com.gpg.ptd.graphics.Screen;
import com.gpg.ptd.level.Dungeon;

public class TextParticle extends Particle{
	
	private String msg;
		
	public TextParticle(int id, int x, int y, String msg, int col, int size, double force, Dungeon dungeon, Random random) {
		super(id, x, y, dungeon, random);
		this.msg = msg;
		this.col = col;
		this.size = size;
		
		xx = x;
		yy = y;
		zz = 20;
		xa = random.nextGaussian() * force;
		ya = random.nextGaussian() * force;
		za = random.nextFloat();
	}

	public void update(){
		life++;
		if(life > 50) dead = true;
		
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
		
		x = (int) xx;
		y = (int) yy;
		
	}
	
	public void render(int xScroll, int yScroll, Font font, Screen screen){
		
		font.render(msg, (x) - xScroll , (y) - yScroll - life , col, size, true, screen);
	}
}
