package com.gpg.ptd.entity;

import java.util.Random;

import com.gpg.ptd.level.Dungeon;
import com.gpg.ptd.util.Rect;

public class Entity {

	protected Dungeon dungeon;
	protected Random random;
	
	protected int id;
	protected int x, y;
	protected int tileX, tileY;
	protected int time;
	
	protected Rect collision;
	
	public Entity(int id, int x, int y, Dungeon dungeon, Random random){
		this.id = id;
		this.x = x;
		this.y = y;
		this.dungeon = dungeon;
		this.random = random;
		
		collision = new Rect(x + 5, y + 5, 25, 25);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getTileX() {
		return tileX;
	}

	public void setTileX(int tileX) {
		this.tileX = tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public void setTileY(int tileY) {
		this.tileY = tileY;
	}

	public Rect getCollision() {
		return collision;
	}

	public void setCollision(Rect collision) {
		this.collision = collision;
	}
}
