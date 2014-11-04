package com.gpg.ptd.level;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import com.gpg.ptd.GameState;
import com.gpg.ptd.entity.item.Gold;
import com.gpg.ptd.entity.item.Item;
import com.gpg.ptd.entity.mob.HostileMob;
import com.gpg.ptd.entity.mob.Mob;
import com.gpg.ptd.entity.mob.Player;
import com.gpg.ptd.entity.particle.BloodParticle;
import com.gpg.ptd.entity.particle.Particle;
import com.gpg.ptd.graphics.Font;
import com.gpg.ptd.graphics.Screen;
import com.gpg.ptd.level.object.Spawner;
import com.gpg.ptd.util.Key;
import com.gpg.ptd.util.Mouse;

public class Fight extends Dungeon{
	
	protected List<Item> items = new ArrayList<Item>();
	
	private boolean fail = false;
	
	public Fight(GameState state, Mouse mouse, Key key, Font font, Screen screen, Random random) {
		super(state, mouse, key, font, screen, random);
		load();
		init();
	}
	
	private void init(){
		player = new Player(0, 5 * 32, 15 * 32, "name", 100, this, key, random); //TODO: *32 or something
//		mobs.add(new Orc(1, 12 * 32, 16 * 32, "orc", 100, this, pathfinder, random));
		xScroll = player.getX() - screen.width / 2;
		yScroll = player.getY() - screen.height/ 2;
		
		items.add(new Gold(items.size(), 4 * 32, 16 * 32, 54, this, random));
		
		
	}
	
	public void load(){
		
		/**
		 *     - FOR TAM -
		 * Database Requirements
		 * 
		 * Get dungeon data BASED OF USER ID.
		 * 
		 * NEED: Dungeon data - tile_ids (it will be a long string like below), spawner_str (shorter string containing spawner details), trap_srt (same as spawner string).
		 * 
		 */
		
		//TODO: This method will take in the data POSTed pack from the database. 
		String result = "22222222222222222222222222222222222222222222222222222222222222222222222222222222222222222200000000000022222222222222222222222222222222222222222000000000000111111111102222222222222222222222222222222222222222201111111115411111111110222222222222222222222222222222222222222220100000000001111111111000222222222222222222222222222222222222222030000000000111116111145022222222222222222222222222222222222222201111111110011111111110102222222222222222222222222222222222222220111111111001111111111010222222222222222222222222222222222222222011111111100111111111101022222222222222222222222222222222222222201111111110011111111110102222222222222222222222222222222222222220111161111000003000000010000000000000222222222222000000000002222011111115102220501111111011111111111020000000000001111111110222201111111110222010100000001111111111102011111111100111111111022220111111111022201010222011111111111110201111111110011111111102222000000000002220101022201111111116111020111111111001111111010000000000000000000010100000111115111111100011111111100111111101411151111111111111111010115411111111111111541111111110011111110100000000000000000000001010001116111111511000111111111001111111010222222201111111111111501020111111151111102011111111100111111111022222220300000000000000502011111111111110201111111110011111111102222222011111111111102010201151111111111020111111111000000000000222222201111111111110201000000000000000000000000000002222222222222222220111111111111020111111111151111111111111115102222222222222222222011111161511100000000000000000000000000000030222222222222222222201111111111111115411100011411000141111111111022222222222222222220111151111111111100010001000100010111111111102222222222222222222011111111111111110201000102010001011111111110222222222222222222201111111111111111020100010201000101116111111022222222222222222220000000000000000002011511020115110111111111102222222222222222222222222222222222222200000002000000011111111110222222222222222222222222222222222222222222222222222201111111111022222222222222222222222222222222222222222222222222220000000000002";		
		
		if(result.length() > width * height) throw new RuntimeException("Error: Result length is greater than tile array size.");
		//This converts a single string into the tile array.
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				int id = Integer.parseInt(String.valueOf(result.charAt(x + y * width)));
				setTile(x, y, id);
				if(id == 6) spawners.add(new Spawner(spawners.size(), x, y, this));
			}
		}
	}

	public void update(){
		if(player.isDead()) fail = true;
		if(fail) return;
		
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				mob[x][y] = -1;
			}
		}
		
		keys();
		mouse();
		
		for(Spawner s : spawners) s.update();
		for(Mob m : mobs){
			m.update();
			mob[m.getX() / 32][m.getY() / 32] = m.getId();
		}
		for(Item i : items) i.update();
		for(Particle p : particles) p.update();
		player.update();
		mob[player.getX() / 32][player.getY() / 32] = player.getId();
		
		removeParticles();
		removeMobs();
		removeItems();
		
		
		
		/*
		 * THIS SMOOTHLY INTERPOLATES BETWEEN TWO POINTS USING THE LERP METHOD.
		 * Compared to the EDIT MODE lerp function, this will keep the player centred. Can follow any mob, replace player name with mob.
		 */
		
		xa = player.getX() - screen.width / 2;
		ya = player.getY() - screen.height / 2;
		if(xa < 0) xa = 0;
		if(ya < 0) ya = 0;
		if(xa > screen.width / 1.663) xa = (int) (screen.width / 1.663);
		if(ya > screen.height / 2.33) ya = (int) (screen.height / 2.33);
		
		xScroll = (int) lerp(xScroll, xa, cubic_scerve3(0.15f));
		yScroll = (int) lerp(yScroll, ya, cubic_scerve3(0.15f));
	}
	
	public void render(Screen screen){	
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll / 32;
		int x1 = (xScroll + screen.width + 32) / 32;
		int y0 = yScroll / 32;
		int y1 = (yScroll + screen.height + 32) / 32;
		for(int y = y0; y < y1; y++){
			for(int x = x0; x < x1; x++){
				//Render the background tiles.
				getTile(x, y).render(x, y, -1, screen);

			}
		}
		
		for(Particle p : particles) p.render(xScroll, yScroll, font, screen);
		for(Item i : items) i.render(xScroll, yScroll, screen);
		for(Mob m : mobs) m.render(xScroll, yScroll, screen);
		player.render(xScroll, yScroll, screen);
		
		for(Spawner s : spawners){
			font.render(s.getAmount() + "", s.getX() * 32 - xScroll, s.getY() * 32 - yScroll, 0xffffffff, 4, true, screen);
		}
		
		font.render("WASD to move, arrow keys to attack.", 500, 710, 0xffffffff, 1, true, screen);
		font.render("Mob Count: " + mobs.size(), 10, 700, 0xffffffff, 1, true, screen);
		font.render("Particle Count: " + particles.size(), 10, 710, 0xffffffff, 1, true, screen);
				
//		font.render("Energy: " + (int)player.getEnergy() + "%", 10, 10, 0xffff00ff, 2f, true, screen);
//		font.render("Weight: " + player.getWeight(), 10, 30, 0xff6CC9F8, 1, true, screen);
//		font.render("Gold: " + player.getInventory().getAmount(), 10, 50, 0xffffffff, 1, true, screen);
//		
//		font.render("Energy: " + (int)player.getEnergy() + "%", 150, 10, 0xffffffff, 3, true, screen);
//		font.render("Weight: " + player.getWeight(), 150, 35, 0xffffffff, 3, true, screen);
//		font.render("Gold: " + player.getInventory().getAmount(), 150, 60, 0xfffff00f, 3, true, screen);
//		
//		font.render("Energy: " + (int)player.getEnergy() + "%", 450, 10, 0xff7fCfff, 3, false, screen);
//		font.render("Weight: " + player.getWeight(), 450, 35, 0xfff1Afff, 3, false, screen);
//		font.render("Gold: " + player.getInventory().getAmount(), 450, 60, 0xff22ffff, 3, false, screen);
		
		font.render("Score " + player.getScore(), 10, 10, 0xB03030, 3, true, screen);
		
		font.render("Gold:", 10, 40, 0xffffffff, 2, true, screen);
		font.render(player.getInventory().getAmount(), 85, 40, 0xfffff00f, 2, true, screen);
		
		if(fail){
			font.render("You Died!", 425, 250, 0xffff0000, 7, true, screen);
			font.render("Moron", 615, 315, 0xffff0000, 1, true, screen);
		}
	}

	private void keys(){
		
	}
	
	private void mouse(){
		
	}
	
	private void removeMobs(){
		for(ListIterator<Mob> i = mobs.listIterator(); i.hasNext();){
			Mob mob = i.next();
			if(mob.isDead()){
				((HostileMob)mob).getSpawner().remove(1);
				i.remove();
				for(int j = 0; j < 5; j++) addParticle(new BloodParticle(getParticles().size(), mob.getX(), mob.getY(), 0xff6E2120, 8, 1.1, this, random));
				player.addScore(mob.getScore());
				continue;
			}
		}
	}
	
	private void removeItems(){
		for(ListIterator<Item> i = items.listIterator(); i.hasNext();){
			Item item = i.next();
			if(item.isRemoved()){
				i.remove();
				continue;
			}
		}
	}
	
	private void removeParticles(){
		for(ListIterator<Particle> i = particles.listIterator(); i.hasNext();){
			Particle particle = i.next();
			if(particle.isDead()){
				i.remove();
				continue;
			}
		}
	}
		
}
