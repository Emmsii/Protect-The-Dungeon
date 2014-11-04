package com.gpg.ptd.level;

import java.util.ArrayList;
import java.util.List;

import com.gpg.ptd.graphics.Screen;

public class WorldMap {

	protected List<Dungeon> dungeons = new ArrayList<Dungeon>();
	
	public WorldMap(){
		/**
		 *     - FOR TAM -
		 * Database Requirements
		 * 
		 * Load ALL dungeon info in the selected world, no need to load tile_ids, spawner info or trap info. 
		 * Player selects a dungeon to attack. THEN download the data.
		 * If player selects own dungeon, will go into EDIT mode. Still gotta download 'dat data.
		 * 
		 */
	}
	
	public void update(){
		
	}
	
	public void render(Screen screen){
		
	}
}
