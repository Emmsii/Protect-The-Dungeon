package com.gpg.ptd.entity.mob;

import java.util.Random;

import com.gpg.ptd.entity.Entity;
import com.gpg.ptd.entity.item.Inventory;
import com.gpg.ptd.entity.particle.BloodParticle;
import com.gpg.ptd.entity.particle.TextParticle;
import com.gpg.ptd.graphics.Screen;
import com.gpg.ptd.level.Dungeon;
import com.gpg.ptd.util.Pathfinder;

public class Mob extends Entity{
	
	protected Pathfinder pathfinder;

	protected int lastX, lastY;
	
	
	protected float speed;
	protected final float MAX_SPEED = 4.0f;
	protected final float MIN_SPEED = 2.0f;

	protected int collision_width;
	protected int collision_w_offset;
	protected int collision_height;
	protected int collision_h_offset;
	
	protected boolean moving;	
	protected int dir = 0;
	protected int frame = 0;
	protected int maxFrames = 0;
	protected int attDir = -1;
	protected int attackTime;
	
	protected int sight = 10;

	protected float bob = 0.0f;
	protected boolean up = true;
	protected float knockback = 0.0f;
	protected int knockDir = 0;
		
	protected boolean dead;
	
	protected String name;
	protected int health;
	protected float energy;
	protected Inventory inventory;
	protected Mob target;
		
	protected int weight;
	
	protected final int SCORE_REWARD = 100;
	
	public Mob(int id, int x, int y, String name, int health, Dungeon dungeon, Pathfinder pathfinder, Random random) {
		super(id, x, y, dungeon, random);
		this.pathfinder = pathfinder;
		this.name = name;
		this.health = health;
		
		energy = 100.0f;
		speed = 1.0f;
		
		inventory = new Inventory();
		
	}

	public void update(){
		//IF MOB DIES, SET SPAWNER AMOUNT = SPAWNER - 1;
//		pathToEntity(new Vector2i(x / 32, y / 32), new Vector2i((dungeon.getPlayer().getX() + 16) / 32, (dungeon.getPlayer().getY() + 16) / 32));
	}
	
	public void render(int xScroll, int yScroll, Screen screen){
//		screen.render((x) - xScroll, (int) ((y) - yScroll - bob), 0 + frame, 1 + dir, 32, 1, SpriteSheet.mobs);
	}
	
	public void move(int xa, int ya){
		if(xa != 0 && ya != 0){
			move(xa, 0);
			move(0, ya);
			return;
		}	
				
		if(knockback <= 0){
			if(xa > 0) dir = 1;
			if(xa < 0) dir = 3;
			if(ya > 0) dir = 2;
			if(ya < 0) dir = 0;
		}
		
		if(attDir != -1) dir = attDir;
		
		if(tileCollision(xa, ya)) return;
		if(mobCollision(xa, ya)) return;
		
		moving = true;
		x += xa;
		y += ya;
		return;
	}
	
	/*
	 * Util Methods
	 */
	

	public void calculateKnockback(){
		if(knockDir == 0) move(0, (int) -knockback);
		if(knockDir == 1) move((int) knockback, 0);
		if(knockDir == 2) move(0, (int) knockback);
		if(knockDir == 3) move((int) -knockback, 0);
		
		knockback -= 0.3;
	}

	
	public void calculateBobbing(){
		float maxB = 3.5f;
		float inc = 0.25f;
		
		if(moving){
			if(up){
				if(bob + inc < maxB) bob += inc;
				else bob = maxB;
				if(bob == maxB) up = false;
			}else{
				if(bob - inc > 0.0f) bob -= inc;
				else bob = 0.0f;
				if(bob == 0.0f) up = true; 
			}
		}else{
			bob = 0.0f;
		}
	}
	
	public boolean mobCollision(int xa, int ya){
		xa = xa + x;
		ya = ya + y;
		
		int x0 = xa + collision_w_offset;
		int y0 = ya + collision_h_offset;
		int x1 = xa + collision_width + collision_w_offset;
		int y1 = ya + collision_height + collision_h_offset;
		
		Mob m = dungeon.getMob(x0, y0, x1, y1, this);
		if(m != null) return true;
		return false;
	}

	public boolean tileCollision(int xa, int ya){
		for(int c = 0; c < 4; c++){
			int xt = ((x + xa) + c % 2 * collision_width + collision_w_offset) / 32;
			int yt = ((y + ya) + c / 2 * collision_height + collision_h_offset) / 32;
			if(dungeon.getTile(xt, yt).solid()) return true;
		}
		return false;
	}

	public void attack(int attDir, int damage){
		if(attackTime == 0){
			checkForMob(x, y, attDir, damage);
			attackTime = 20;
		}
	}
	
	public void checkForMob(int x, int y, int attDir, int damage){
		Mob m = dungeon.checkAndGetMob((x) / 32, (y) / 32, attDir, this);
		if(m != null) m.damage(damage, attDir, this);
		
	}
	
	public void animation(){
		time++;
		
		if(time % 10 == 0){
			if(moving){
				if(frame >= 1) frame = 0;
				else frame++;
			}
		}
	}
	
	public void damage(int damage, int dir, Mob mob){
		if(target == null) target = mob;
		if(damage > 0){
			for(int i = 0; i < (damage * 3); i++) dungeon.addParticle(new BloodParticle(dungeon.getParticles().size(), x, y, 0xffE04141, 2, 0.4, dungeon, random));
			dungeon.addParticle(new TextParticle(dungeon.getParticles().size(), x, y, "-" + damage, 0xffE04141, 2, 0.4, dungeon, random));
		}
		else dungeon.addParticle(new TextParticle(dungeon.getParticles().size(), x, y, "-" + damage, 0xff41A3E0, 2, 0.4, dungeon, random));
		
		knockDir = dir;
		knockback = 3f;
		
		if(health - damage > 0) health -= damage;
		else{
			setDead(true);
			health = 0;
		}
	}
	
	public int convertHealth(){
		return (health * 32) / 100;
	}
	
	/*
	 * Getters and Setters
	 */
	
	public float getEnergy() {
		return energy;
	}

	public void setEnergy(float energy) {
		this.energy = energy;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public int getAttDir() {
		return attDir;
	}

	public void setAttDir(int attDir) {
		this.attDir = attDir;
	}

	public float getMAX_SPEED() {
		return MAX_SPEED;
	}

	public float getMIN_SPEED() {
		return MIN_SPEED;
	}

	public Pathfinder getPathfinder() {
		return pathfinder;
	}

	public void setPathfinder(Pathfinder pathfinder) {
		this.pathfinder = pathfinder;
	}

	public int getSight() {
		return sight;
	}

	public void setSight(int sight) {
		this.sight = sight;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getScore(){
		return SCORE_REWARD;
	}

	public int getCollision_width() {
		return collision_width;
	}

	public void setCollision_width(int collision_width) {
		this.collision_width = collision_width;
	}

	public int getCollision_w_offset() {
		return collision_w_offset;
	}

	public void setCollision_w_offset(int collision_w_offset) {
		this.collision_w_offset = collision_w_offset;
	}

	public int getCollision_height() {
		return collision_height;
	}

	public void setCollision_height(int collision_height) {
		this.collision_height = collision_height;
	}

	public int getCollision_h_offset() {
		return collision_h_offset;
	}

	public void setCollision_h_offset(int collision_h_offset) {
		this.collision_h_offset = collision_h_offset;
	}
}
