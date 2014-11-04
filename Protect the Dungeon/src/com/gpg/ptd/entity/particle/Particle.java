package com.gpg.ptd.entity.particle;

import java.util.Random;

import com.gpg.ptd.entity.Entity;
import com.gpg.ptd.graphics.Font;
import com.gpg.ptd.graphics.Screen;
import com.gpg.ptd.level.Dungeon;

public class Particle extends Entity{

	protected int life;
	protected boolean dead;
	protected int col;
	protected int size;
	
	protected double xa, ya, za;
	protected double xx, yy, zz;
	
	public Particle(int id, int x, int y, Dungeon dungeon, Random random) {
		super(id, x, y, dungeon, random);
	}

	public void update(){
		
	}
	
	public void render(int xScroll, int yScroll, Font font, Screen screen){
		
	}
		
	public void move(double xc, double yc) {
//		if(collision(xc, yc)){
//			this.xa *= 0.8;
//			this.ya *= 0.8;
//			this.za *= 0.8;
//
//		}
		
		this.x = (int) xc;
		this.y = (int) yc;
		this.zz += za;
	}
	
	public boolean collision(double xi, double yi){
//		System.out.println(xi + ","+  yi);
//		boolean solid = false;
//		for(int c = 0; c < 4; c++){
//			double xt = (xi - c % 2 * 32) / 32;
//			double yt = (yi - c / 2 * 32) / 32;
//			int ix = (int) Math.ceil(xt);
//			int iy = (int) Math.ceil(yt);
//			if(c % 2 == 0) ix = (int) Math.floor(xt);
//			if(c / 2 == 0) iy = (int) Math.floor(yt);
//			System.out.println(ix + ","+  iy);
//			if(dungeon.getTile((int)xt,(int) yt).solid()) solid = true;
//			
//		}
//		return solid;
//		System.out.println(xi /32 + "< " + yi / 32);
		if(dungeon.getTile((int)(xi / 32),(int) (yi / 32)).solid()) return true;
		return false;
	}
	
	public boolean isDead(){
		return dead;
	}
}
