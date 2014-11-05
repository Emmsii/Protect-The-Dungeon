package com.gpg.ptd.level;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import com.gpg.ptd.GameState;
import com.gpg.ptd.graphics.Font;
import com.gpg.ptd.graphics.Screen;
import com.gpg.ptd.graphics.SpriteSheet;
import com.gpg.ptd.level.object.Spawner;
import com.gpg.ptd.level.object.Trap;
import com.gpg.ptd.level.tile.Tile;
import com.gpg.ptd.util.Coord;
import com.gpg.ptd.util.Key;
import com.gpg.ptd.util.Mouse;
import com.gpg.ptd.util.Node;
import com.gpg.ptd.util.Vector2i;

public class Edit extends Dungeon{

	private int tileX;
	private int tileY;
	private int dragX;
	private int dragY;
	
	private int selection = 1;
	private boolean tileValid;
	private boolean saved = false;
	private boolean checked = false;
	private boolean accepted = false;
	
	private int minTraps = 10;
	private int maxTraps = 20;
	private int minSpawners = 4;
	private int maxSpawners = 8;
	
	private Coord start = new Coord(2, 16);
	private Coord end = new Coord(62, 16);
					
	public Edit(GameState state, Mouse mouse, Key key, Font font, Screen screen, Random random) {
		super(state, mouse, key, font, screen, random);
		init();
	}
	
	private void init(){
		//Fill dungeon with walls.
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				setTile(x, y, 2);
			}
		}
		
		//Place start and end rooms.
		for(int y = 0; y < 9; y++){
			int ya = y + (32 / 2) - (9 / 2);
			for(int x = 0; x < 9; x++){
				int xa = x + (64 - 9);
				setTile(x + 1, ya, 1);
				setTile(xa - 1, ya, 1);
			}
		}
		
//		setTile(start.getX(), start.getY(), 6);
//		setTile(end.getX(), end.getY(), 6);
		
		placeWalls();
	}
	
	public void update(){
		mouse();
		keys();
		
		//VALID LOGIC
		tileValid = true;
		switch(selection){
			case 0:
				if(!checkStartEndBounds(tileX, tileY)) tileValid = false;
				break;
			case 1:
				if(getTile(tileX, tileY).equals(Tile.floorTile) || !checkTilePos(tileX, tileY)) tileValid = false;
				break;
			case 2:
				if(getTile(tileX, tileY).equals(Tile.wallTile) || !checkTilePos(tileX, tileY)) tileValid = false;
				break;
			case 3:
				if(!getTile(tileX, tileY).equals(Tile.floorTile) || checkTrapLocation(tileX, tileY)) tileValid = false;
				break;
			case 4:
				if(getTile(tileX, tileY).equals(Tile.doorTileLR) || getTile(tileX, tileY).equals(Tile.doorTileTD) || !getTile(tileX, tileY).equals(Tile.floorTile) || getDoorPos() == 0) tileValid = false;
				break;
			case 5:
				if(!getTile(tileX, tileY).equals(Tile.floorTile) || !checkSpawnerLocation(tileX, tileY)) tileValid = false;
				break;
			default:
				break;
		}
		
		if(!checkStartEndBounds(tileX, tileY)) tileValid = false;
		checkDoors();
		
		//THIS SMOOTHLY INTERPOLATES BETWEEN TWO POINTS USING THE LERP METHOD.
		xScroll = (int) lerp(xScroll, xa, cubic_scerve3(0.25f));
		yScroll = (int) lerp(yScroll, ya, cubic_scerve3(0.25f));
//		xScroll = (int) lerp(xScroll, xa, 0.25f);
//		yScroll = (int) lerp(yScroll, ya, 0.25f);
				
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
				int col = -1;			
				if(x == tileX && y == tileY && !tileValid) col = 0xffb2626;
				getTile(x, y).render(x, y, col, screen);
				if(!checkBounds(x, y)) continue;

			}
		}

		if(selection != 5) screen.render((tileX * 32) - xScroll, (tileY * 32) - yScroll, 0, 1, 32, 1, SpriteSheet.gui);
		else{
			screen.render(((tileX - spawnerRadius) * 32) - xScroll, ((tileY - spawnerRadius) * 32) - yScroll, 0, 2, 16, 2, SpriteSheet.gui);
			screen.render(((tileX + spawnerRadius) * 32) - xScroll, ((tileY - spawnerRadius) * 32) - yScroll, 1, 2, 16, 2, SpriteSheet.gui);
			screen.render(((tileX + spawnerRadius) * 32) - xScroll, ((tileY + spawnerRadius) * 32) - yScroll, 1, 3, 16, 2, SpriteSheet.gui);
			screen.render(((tileX - spawnerRadius) * 32) - xScroll, ((tileY + spawnerRadius) * 32) - yScroll, 0, 3, 16, 2, SpriteSheet.gui);
		}
		
		//TODO: Render a selection row of possible tiles to draw with.
		font.render("Selection: " + selection, 10, 200, 0xffffff, 1, true, screen);
		font.render("Traps: " + minTraps + "-" + maxTraps, 10, 220, 0xffffff, 1, true, screen);
		font.render("Spawners: " + minSpawners + "-" + maxSpawners, 10, 240, 0xffffff, 1, true, screen);
		
		int col1 = 0xff7DA845;
		if(traps.size() < minTraps) col1 = 0xffB32020;
		font.render("(" + traps.size() + ")", 200, 220, col1, 1, true, screen);
		int col2 = 0xff7DA845;
		if(spawners.size() < minSpawners) col2 = 0xffB32020;
		font.render("(" + spawners.size() + ")", 220, 240, col2, 1, true, screen);
		
		screen.render(50, 10, 2, 3, 16, 4, SpriteSheet.gui);
		screen.render(150, 10, 1, 0, 32, 2, SpriteSheet.textures);
		screen.render(250, 10, 0, 0, 32, 2, SpriteSheet.textures);
		screen.render(350, 10, 0, 1, 32, 2, SpriteSheet.textures);
		screen.render(450, 10, 3, 0, 32, 2, SpriteSheet.textures);
		screen.render(550, 10, 4, 2, 16, 4, SpriteSheet.gui);
		
		font.render("Clear", 47, 80, 0xffffff, 1, true, screen);
		font.render("Floor", 146, 80, 0xffffff, 1, true, screen);
		font.render("Wall", 253, 80, 0xffffff, 1, true, screen);
		font.render("Trap", 352, 80, 0xffffff, 1, true, screen);
		font.render("Door", 453, 80, 0xffffff, 1, true, screen);
		font.render("Spawner", 532, 80, 0xffffff, 1, true, screen);
		
		screen.render(50 + (100 * (selection)), 10, 0, 1, 32, 2, SpriteSheet.gui);
		
		font.render(tileX + "," + tileY, 600, 70, 0xffffff, 1, true, screen);
		
		if(saved) font.render("SAVED: Result pasted to clipboard.", 300, 300, 0xffffff, 1, true, screen);
	}
	
	private void mouse(){
		if(key.shift){
			dragX = (mouse.getX() + xScroll) / 32;
			dragY = (mouse.getY() + yScroll) / 32;
		}else{
			tileX = (mouse.getX() + xScroll) / 32;
			tileY = (mouse.getY() + yScroll) / 32;
		}
		
		if(mouse.getButton() == 1){
			//Start placing selected tile at tileX & tileY
			checked = false;
			switch(selection){
				case 0: //Clear
					if(tileValid){
						setTile(tileX, tileY, 2);
						checkDeleteTrap(tileX, tileY);
					}
					break;
				case 1: //Floor
					if(tileValid) setTile(tileX, tileY, 1);
					break;
				case 2: //Wall
					if(tileValid) setTile(tileX, tileY, 0);
					break;
				case 3://Trap
					if(tileValid && traps.size() < maxTraps){
						traps.add(new Trap(traps.size(), tileX, tileY, null));
						setTile(tileX, tileY, 5);
					}
					break;
				case 4://Door
					if(tileValid) setTile(tileX, tileY, getDoorPos());
					break;
				case 5://Spawner
					if(tileValid){
						spawners.add(new Spawner(spawners.size(), tileX, tileY, null));
						setTile(tileX, tileY, 6);
					}
					break;
				default:
					break;
			}
			if(selection != 0) placeWalls();	
		}
		
		if(mouse.getButton() == 3){
			if(getTile(tileX, tileY).equals(Tile.trapTile)){
				checkDeleteTrap(tileX, tileY);
				setTile(tileX, tileY, 1);
			}
		}
		
		if(mouse.getButton() != 1 && selection == 0) placeWalls();
		
	}
	
	private void keys(){
		key.update();
		
		//TODO: Shift mode - draw out rooms holding shift to make life easier. Doesn't make coding life easier...
//		if(key.shift){
//			if(mouse.getButton() == 1){
//				fillTempTiles();
//				for(int y = tileY; y < dragY; y++){
//					for(int x = tileX; x < dragX; x++){
//						tempTiles[x][y] = 1;
//					}
//				}
//			}else{
//				for(int y = 0; y < height; y++){
//					for(int x = 0; x < width; x++){
//						if(tempTiles[x][y] == 1) setTile(x, y, 1);
//					}
//				}
//				placeWalls();
//			}
//		}
		
		int speed = 10;
		if(key.w && ya > 0) ya -= speed;
		if(key.s && ya < screen.height / 2.33) ya += speed;
		if(key.a && xa > 0) xa -= speed;
		if(key.d && xa < screen.width / 1.663) xa += speed;
		
		if(key.one) selection = 0;
		if(key.two) selection = 1;
		if(key.three) selection = 2;
		if(key.four) selection = 3;
		if(key.five) selection = 4;
		if(key.six) selection = 5;
		
		if(key.e) save();
	}
	
	private void save(){
		if(!checked){
			checked = true;
			if(!checkPath()){
				System.err.println("Path to end of dungeon could not be found, please connect start and end 'doors'.");
				System.err.println("Dungeon has not been saved.");
				return;
			}else{
				System.out.println("Path to the end of dungeon has been found!");
				accepted = true;
			}
		}
		
		if(saved) return;
		if(!accepted) return;
		
		if(traps.size() < minTraps || spawners.size() < minSpawners){
			System.err.println("Minium trap and/or spawner requirement not met. Will place warning on screen later.");
			accepted = false;
			return;
		}
		
		String tilesStr = "";
		String trapsStr = "";
		String spawnersStr = "";
		
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				tilesStr = tilesStr + Integer.toString(getTileInt(x, y));
			}
		}
		
		for(Trap t : traps) trapsStr = trapsStr + ".tr" + t.getId() + "t0" + "x" + t.getX() + "y" + t.getY();		
		for(Spawner s : spawners) spawnersStr = spawnersStr + ".sp" + s.getId() + "t0" + "x" + s.getX() + "y" + s.getY();		
		
		StringSelection string = new StringSelection(tilesStr);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(string, string);
		
		System.out.println("SAVE RESULT.");
		System.out.println("uid:0");
		System.out.println(tilesStr);
		System.out.println(trapsStr);
		System.out.println(spawnersStr);
		saved = true;
		
		/*
		 * Final output string is 4kb per dungeon.
		 * 100 dungeons would be 400kb.
		 * 1,000,000 dungeons could be stored in 1GB space.
		 * Possibility to store dungeons as .txt files?
		 */
		
	}
	
	private boolean checkPath(){
		List<Node> path = pathfinder.findPath(new Vector2i(start.getX(), start.getY()), new Vector2i(end.getX(), end.getY()), 0);
		if(path == null) return false;
		return true;
	}
	
	/*
	 * Util Methods:
	 */
	
	private void checkDeleteTrap(int x, int y){
		//delete traps.
		for(ListIterator<Trap> i = traps.listIterator(); i.hasNext();){
			Trap t = i.next();
			if(t.getX() == x && t.getY() == y){
				i.remove();
				continue;
			}
		}
	}
	
	private boolean checkTrapLocation(int x, int y){
		for(Trap t : traps) if(t.getX() == x && t.getY() == y) return true;
		return false;
	}
	
	private boolean checkSpawnerLocation(int x, int y){
		for(int ya = y - spawnerRadius; ya < y + spawnerRadius + 1; ya++){
			for(int xa = x - spawnerRadius; xa < x + spawnerRadius + 1; xa++){
				if(!checkBounds(xa, ya)) continue;
				if(!getTile(xa, ya).equals(Tile.floorTile)) return false;
			}
		}
		return true;
	}
	
	private int getDoorPos(){
		//TODO: EITHER check if floors are on both sides of the door. OR let doors be placed in places with one exit, easter egg saying "You've walked into a brick wall". - Tamfoolery
		if(getTile(tileX + 1, tileY).equals(Tile.wallTile) && getTile(tileX - 1, tileY).equals(Tile.wallTile)) return 3;
		if(getTile(tileX, tileY + 1).equals(Tile.wallTile) && getTile(tileX, tileY - 1).equals(Tile.wallTile)) return 4;
		return 0;
	}
	
	private void placeWalls(){
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				if(x == 0 || y == 0 || x == width - 1 || y == height - 1) if(getTile(x, y).equals(Tile.floorTile)) setTile(x, y, 0);
				if(getTile(x, y).equals(Tile.floorTile)) checkAndPlace(x, y, 0);
			}
		}
	}
	
	private void checkAndPlace(int x, int y, int id){
		if(getTile(x - 1, y) == Tile.rockTile) setTile(x - 1, y, id);
		if(getTile(x + 1, y) == Tile.rockTile) setTile(x + 1, y, id);
		if(getTile(x, y + 1) == Tile.rockTile) setTile(x, y + 1, id);
		if(getTile(x, y - 1) == Tile.rockTile) setTile(x, y - 1, id);
		
		if(getTile(x - 1, y - 1) == Tile.rockTile) setTile(x - 1, y - 1, id);
		if(getTile(x + 1, y - 1) == Tile.rockTile) setTile(x + 1, y - 1, id);
		if(getTile(x - 1, y + 1) == Tile.rockTile) setTile(x - 1, y + 1, id);
		if(getTile(x + 1, y + 1) == Tile.rockTile) setTile(x + 1, y + 1, id);
	}

	public void checkDoors(){
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				if(getTile(x, y).equals(Tile.doorTileTD)) if(!getTile(x - 1, y).equals(Tile.wallTile) || !getTile(x + 1, y).equals(Tile.wallTile)) setTile(x, y, 1);
				if(getTile(x, y).equals(Tile.doorTileLR)) if(!getTile(x, y - 1).equals(Tile.wallTile) || !getTile(x, y + 1).equals(Tile.wallTile)) setTile(x, y, 1);
			}
		}
	}
	
	private boolean checkTilePos(int xp, int yp){
		if(xp <= 0 || yp <= 0 || xp >= width - 1|| yp >= height - 1) return false;

		//TODO: Detect if tile is in start and end rooms, return false if true.
		return true;
	}
	
	private boolean checkStartEndBounds(int xp, int yp){
		//TODO: Still prettu buggy, can still erase edges of start and end rooms.
		for(int y = 0; y < 9; y++){
			int ya = y + (32 / 2) - (9 / 2);
			for(int x = 0; x < 10; x++){
				int xa = x - 1 + (64 - 10);
				if(xp == x && yp == ya) return false;
				if(xp == xa && yp == y) return false;
				if(xp == xa && yp == ya) return false;
			}
			
		}
		return true;
	}
}

