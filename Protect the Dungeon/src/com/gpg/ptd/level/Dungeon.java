package com.gpg.ptd.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.gpg.ptd.GameState;
import com.gpg.ptd.entity.mob.HostileMob;
import com.gpg.ptd.entity.mob.Mob;
import com.gpg.ptd.entity.mob.Orc;
import com.gpg.ptd.entity.mob.Player;
import com.gpg.ptd.entity.particle.Particle;
import com.gpg.ptd.graphics.Font;
import com.gpg.ptd.graphics.Screen;
import com.gpg.ptd.level.object.Spawner;
import com.gpg.ptd.level.object.Trap;
import com.gpg.ptd.level.tile.Tile;
import com.gpg.ptd.util.Key;
import com.gpg.ptd.util.Mouse;
import com.gpg.ptd.util.Pathfinder;

public class Dungeon {

	protected Pathfinder pathfinder;
	protected GameState state;
	protected Random random;
	protected Mouse mouse;
	protected Key key;
	protected Font font;
	protected Screen screen;
	
	protected int xScroll, yScroll;
	protected int xa, ya;
	
	public int width = 64;
	public int height = 32;
	
	public int spawnerRadius = 3;
	
	private int[][] tiles;
	public int[][] mob = new int[width][height];

	
	protected Player player;
	protected List<Mob> mobs = new ArrayList<Mob>();
	protected List<Trap> traps = new ArrayList<Trap>();
	protected List<Spawner> spawners = new ArrayList<Spawner>();	
	protected List<Particle> particles = new ArrayList<Particle>();
		
	public Dungeon(GameState state, Mouse mouse, Key key, Font font, Screen screen, Random random){
		this.state = state;
		this.random = random;
		this.mouse = mouse;
		this.key = key;
		this.screen = screen;
		this.font = font;
		pathfinder = new Pathfinder(this);
		tiles = new int[width][height];
	}
	
	public void loadDungeon(){
		
	}
	
	public void update(){
		
	}
	
	public void input(){
		
	}
	
	public void render(Screen screen){
		
	}
	
	/*
	 * Util Methods
	 */
	
	public void addMob(int xp, int yp, Spawner spawner){	
		for(int y = yp - spawnerRadius; y < yp + spawnerRadius + 1; y++){
			for(int x = xp - spawnerRadius; x < xp + spawnerRadius + 1; x++){
				if(spawner.getAmount() >= 6) return;
				if(x == xp && y == yp) continue;
				if(!getTile(x, y).equals(Tile.floorTile)) continue;
				for(Mob m : mobs) if(m.getX() == x && m.getY() == y) continue;
				if(random.nextInt(10) == 0){
					//TODO: Make mob based of spawner type.
					Mob newMob = new Orc(mobs.size(), x * 32, y * 32, "orc", 100, this, pathfinder, random);
					((HostileMob) newMob).setSpawner(spawner);
					mobs.add(newMob);
					spawner.setAmount(spawner.getAmount() + 1);
				}
			}
		}
	}
	
	public float lerp(float p1, float p2, float alpha){
		return p1 + alpha * (p2 - p1);
	}
	
	public float cubic_scerve3(float alpha){
		return alpha * alpha * (3.0f - 2.0f * alpha);
	}
	
	public Mob getMob(int xa, int ya, Mob mob){
		for(Mob m : mobs){
			if(m.equals(mob)) continue;
			int xb = m.getX() + m.getCollision_w_offset();
			int yb = m.getY() + m.getCollision_h_offset();
			int wb = xb + m.getCollision_width();
			int hb = yb + m.getCollision_height(); 
			
			for(int y = yb; y < hb; y++) for(int x = xb; x < wb; x++) if(xa == x && ya == y) return m;
		}
		return null;
	}
	
	public Mob getMob(int x0, int y0, int x1, int y1, Mob mob){
		for(Mob m : mobs){
			if(m.equals(mob)) continue;
			
			int mx = m.getX() + m.getCollision_w_offset();
			int my = m.getY() + m.getCollision_h_offset();
			int mw = mx + m.getCollision_width();
			int mh = my + m.getCollision_height();
			
			/*
			 * x0 = topLeft
			 * y0 = topRight
			 * x1 = bottomRight
			 * y1 = bottomLeft
			 * 
			 * mx = topLeft
			 * my = topRight
			 * mw = bottomRight
			 * mh = bottomLeft
			 */
			
			if(x0 > mx && x0 < mw && y0 > my && y0 < mh) return m;
			if(x1 > mx && x1 < mw && y1 > my && y1 < mh) return m;
			if(x0 > mx && x0 < mw && y1 > my && y1 < mh) return m;
			if(x1 > mx && x1 < mw && y0 > my && y0 < mh) return m;
			
			if(mx > x0 && mw < x0 && my > y0 && mh < y0) return m;
			if(mx > x1 && mw < x1 && my > y1 && mh < y1) return m;
			if(mx > x0 && mw < x0 && my > y1 && mh < y1) return m;
			if(mx > x1 && mw < x1 && my > y0 && mh < y0) return m;
			
			
		}
		
		int px = player.getX() + player.getCollision_w_offset();
		int py = player.getY() + player.getCollision_h_offset();
		int pw = px + player.getCollision_width();
		int ph = py + player.getCollision_height();
		
		if(x0 > px && x0 < pw && y0 > py && y0 < ph) return player;
		if(x1 > px && x1 < pw && y1 > py && y1 < ph) return player;
		if(x0 > px && x0 < pw && y1 > py && y1 < ph) return player;
		if(x1 > px && x1 < pw && y0 > py && y0 < ph) return player;
		
		if(px > x0 && pw < x0 && py > y0 && ph < y0) return player;
		if(px > x1 && pw < x1 && py > y1 && ph < y1) return player;
		if(px > x0 && pw < x0 && py > y1 && ph < y1) return player;
		if(px > x1 && pw < x1 && py > y0 && ph < y0) return player;
		
		
		return null;
	}
	
	public Tile getTile(int x, int y){
		if(x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;
		if(tiles[x][y] == 0) return Tile.wallTile;
		if(tiles[x][y] == 1) return Tile.floorTile;
		if(tiles[x][y] == 2) return Tile.rockTile;
		if(tiles[x][y] == 3) return Tile.doorTileTD;
		if(tiles[x][y] == 4) return Tile.doorTileLR;
		if(tiles[x][y] == 5) return Tile.trapTile;
		if(tiles[x][y] == 6) return Tile.spawnerTile;
		return Tile.voidTile;
	}

	public Mob checkAndGetMob(int x, int y, int dir, Mob mob){
		
		for(Mob m : mobs){
			if(m.equals(mob)) continue;
			if((m.getX() + 16)  / 32 == x && (m.getY() + 16) / 32 == y) return m;
			if(dir == 0) if((m.getX() + 16) / 32 == x && (m.getY() + 16) / 32 == y - 1) return m;
			if(dir == 1) if((m.getX() + 16)  / 32 == x + 1 && (m.getY() + 16) / 32 == y) return m;
			if(dir == 2) if((m.getX() + 16)  / 32 == x && (m.getY() + 16) / 32 == y + 1) return m;
			if(dir == 3) if((m.getX() + 16)  / 32 == x  - 1 && (m.getY() + 16) / 32 == y) return m;
		}
		
		if(player.equals(mob)) return null;
		
		int px = player.getX();
		int py = player.getY();
		
		if((px + 16) / 32 == x && (py + 16) / 32 == y) return player;
		if(dir == 0) if((px + 16) / 32 == x && (py + 16) / 32 == y - 1) return player;
		if(dir == 1) if((px + 16) / 32 == x + 1 && (py + 16) / 32 == y) return player;
		if(dir == 2) if((px + 16) / 32 == x && (py + 16) / 32 == y + 1) return player;
		if(dir == 3) if((px + 16) / 32 == x - 1 && (py + 16) / 32 == y) return player;
		
		return null;
	}
	
	public boolean checkMob(int x, int y, Mob mob){
		for(Mob m : mobs){
//			System.out.println(m.getX() / 32 + ", " + m.getY() / 32);
//			System.out.println(x + ", " + y);
			if(m.equals(mob)) continue;
			if(m.getX() / 32 == x && m.getY() / 32 == y) return true;
		}
		
		return false;
	}
		
	public void addParticle(Particle p){
		particles.add(p);
	}
	
	public List<Particle> getParticles(){
		return particles;
	}
	
	public boolean checkBounds(int x, int y){
		if(x < 0 || y < 0 || x >= width || y >= height) return false;
		return true;
	}
	
	public int getTileInt(int x, int y){
		return tiles[x][y];
	}
		
	public void setTile(int x, int y, int tile){
		if(!checkBounds(x, y)) return;
		tiles[x][y] = tile;
	}
	
	public int[][] getTiles(){
		return tiles;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	
}
