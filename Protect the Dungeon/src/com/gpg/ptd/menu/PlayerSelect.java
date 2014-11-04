package com.gpg.ptd.menu;

import java.util.Random;

import com.gpg.ptd.Game;
import com.gpg.ptd.entity.mob.Player;
import com.gpg.ptd.graphics.Font;
import com.gpg.ptd.graphics.Screen;
import com.gpg.ptd.graphics.SpriteSheet;
import com.gpg.ptd.level.tile.Tile;
import com.gpg.ptd.util.Key;
import com.gpg.ptd.util.Mouse;

public class PlayerSelect extends Menu{

	private Game game;
	private Random random;
	
	protected Player[] players = new Player[3];
	protected Player selectedPlayer = null;
	
	public PlayerSelect(Game game, Font font, Mouse mouse, Key key, Random random) {
		super(font, mouse, key);
		this.game = game;
		this.random = random;
		init();
	}
	
	private void init(){
		
		/**
		 *     - FOR TAM -
		 * Database Requirements
		 * 
		 * Get all the characters and character info that the player owns, BASED OFF USER ID.
		 * Not all character info is in the database yet.
		 * If character doesn't exist, player will create one (I haven't made character creation screen yet).
		 * 
		 * When the player selects a character.
		 * 
		 */
		
		//loadCharacters(userId);
		//If no characters are found. place empty characters
		for(int i = 0; i < 3; i++){
			players[i] = new Player(i, -1, -1, "Empty", 100, null, key, random);
		}
		
		selectedPlayer = players[1];
	}
	
	public void update(){
		
	}
	
	public void render(Screen screen){
		for(int y = 0; y < (screen.height / 32) + 32; y++){
			for(int x = 0; x < (screen.width / 32); x++){
				screen.renderTile(x * 32, y * 32, -1, Tile.rockTile);
			}
		}
		
		font.render("Character Select", -1, 10, 0xffffffff, 2, true, screen);
		
		for(int i = 0; i < 3; i++){
			renderPlayerBg(screen, i);
			//Render player icon in right position
			font.render(players[i].getName() + " " + players[i].getId(), 14 * (i * 32) + 20, 50, 0xffffff, 1, true, screen);
		}
		
		if(selectedPlayer != null){
			font.render(selectedPlayer.getName(), -1, 535, 0xffffffff, 3, true, screen);
		}
		
	}
	
	private void renderPlayerBg(Screen screen, int i){
		screen.render(14 * (i * 32) + 50, 40, 3, 0, 32, 10, SpriteSheet.gui);
		screen.render(14 * (i * 32) + 50, 40 + (32 * 10), 3, 1, 32, 10, SpriteSheet.gui);
	}

}
