package com.gpg.ptd.entity.mob;

import java.util.List;
import java.util.Random;

import com.gpg.ptd.level.Dungeon;
import com.gpg.ptd.level.object.Spawner;
import com.gpg.ptd.level.tile.Tile;
import com.gpg.ptd.util.Node;
import com.gpg.ptd.util.Pathfinder;
import com.gpg.ptd.util.Vector2i;

public class HostileMob extends Mob{

	protected Spawner spawner;

	protected List<Node> path = null; 
	protected int targetX = 0, targetY = 0;
	
	protected float chaseSpeed;
	protected boolean chasing;
	protected boolean clever;
		
	public HostileMob(int id, int x, int y, String name, int health, Dungeon dungeon, Pathfinder pathfinder, Random random) {
		super(id, x, y, name, health, dungeon, pathfinder, random);
		
		chaseSpeed = 1.5f;
		giveItems();
	}

	/*
	 * Util Methods
	 */
	
	public void wanderSpawner(){
		if(time % 10 + random.nextInt(25) == 0) pickWanderLocation();
				
		if(targetX != 0 && targetY != 0) pathTo(new Vector2i(x / 32, y / 32), new Vector2i(targetX, targetY));
	}
	
	private void pickWanderLocation(){
		if(spawner == null) return;
		int sx = spawner.getX();
		int sy = spawner.getY();

		if(x == targetX && y == targetY){
			targetX = 0;
			targetY = 0;
			moving = false;
		}

		int tries = 0;
		while(true){
			tries++;
			if(tries >= 50) break;
			targetX = (x / 32) + random.nextInt(6) - 3;
			targetY = (y / 32) + random.nextInt(6) - 3;

			double dist = pathfinder.getDistance(new Vector2i(sx, sy), new Vector2i(targetX, targetY));

			if(dist < 5.5f){
				if(dungeon.getTile(targetX, targetY).equals(Tile.floorTile) && !dungeon.checkMob(targetX, targetY, this)) break;
			}
	
		}
		
	}
	
	public void pathTo(Vector2i start, Vector2i end){
		if(time % 4 == 0) path = pathfinder.findPath(start, end, 0);
		if(path != null){
			if(path.size() > 0){
				Vector2i vec = path.get(path.size() - 1).getTile();
				int xa = 0;
				int ya = 0;
				if(chasing) speed = chaseSpeed;
				if(x < vec.getX() * 32) xa += speed;
				if(x > vec.getX() * 32) xa -= speed;
				if(y < vec.getY() * 32) ya += speed;
				if(y > vec.getY() * 32) ya -= speed;
								
				if(xa != 0 || ya != 0) move(xa, ya);
				
			}else{
				moving = false;
			}
		}
	}
	
	private void giveItems(){
		inventory.addGold(random.nextInt(150));
	}
	
	/*
	 * Getters and Setters
	 */
	
	public Spawner getSpawner() {
		return spawner;
	}

	public void setSpawner(Spawner spawner) {
		this.spawner = spawner;
	}

	public List<Node> getPath() {
		return path;
	}

	public void setPath(List<Node> path) {
		this.path = path;
	}

	public int getRx() {
		return targetX;
	}

	public void setRx(int rx) {
		this.targetX = rx;
	}

	public int getRy() {
		return targetY;
	}

	public void setRy(int ry) {
		this.targetY = ry;
	}

	public float getChaseSpeed() {
		return chaseSpeed;
	}

	public void setChaseSpeed(float chaseSpeed) {
		this.chaseSpeed = chaseSpeed;
	}

	public boolean isChasing() {
		return chasing;
	}

	public void setChasing(boolean chasing) {
		this.chasing = chasing;
	}

	public boolean isClever() {
		return clever;
	}

	public void setClever(boolean clever) {
		this.clever = clever;
	}

}
