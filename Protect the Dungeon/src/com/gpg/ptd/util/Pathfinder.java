package com.gpg.ptd.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.gpg.ptd.level.Dungeon;
import com.gpg.ptd.level.tile.Tile;

public class Pathfinder {

	private Dungeon dungeon;
	
	public Pathfinder(Dungeon dungeon){
		this.dungeon = dungeon;
	}
	
	public List<Node> findPath(Vector2i start, Vector2i end, int distance){
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		Node current = new Node(start, null, 0, getDistance(start, end));
		openList.add(current);
		while(openList.size() > 0){
			if(distance != 0) if(openList.size() > distance) return null;
			Collections.sort(openList, nodeSorter);
			current = openList.get(0);
			if(current.getTile().equals(end)){
				List<Node> path = new ArrayList<Node>();
				while(current.getParent() != null){
					path.add(current);
					current = current.getParent();
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			openList.remove(current);
			closedList.add(current);
			for(int i = 0; i < 9; i++){
				if(i == 0 || i == 2 || i == 4 || i == 6 || i == 8) continue;
				int x = current.getTile().getX();
				int y = current.getTile().getY();
				int xi = (i % 3) - 1;
				int yi = (i / 3) - 1;
				Tile t = dungeon.getTile(x + xi, y + yi);
				if(t.solid()) continue;
				if(dungeon.mob[x + xi][y + yi] != -1) continue;
				Vector2i a = new Vector2i(x + xi, y + yi);
				double gCost = current.getgCost() + (getDistance(current.getTile(), a) == 1 ? 1 : 0.95);
				double hCost = getDistance(a, end);
				Node node = new Node(a, current, gCost, hCost);
				if(vecInList(closedList, a) && gCost >= node.getgCost()) continue;
				if(!vecInList(openList, a) || gCost < node.getgCost()) openList.add(node);			
			}
		}
		return null;
	}

	public boolean los(Coord mob, Coord player){
		
		
		int x = 0, y = 0;
		int t, ax, ay, sx, sy, dx, dy;
		int px = player.getX();
		int py = player.getY();
		int mx = mob.getX();
		int my = mob.getY();
		
//		System.out.println("line between: " + mx + ", " + mx + " & " + px + ", " + py); 
		
		dx = px - mx;
		dy = py - my;
		
		ax = Math.abs(dx) << 1;
		ay = Math.abs(dy) << 1;
		
		sx = (int) Math.sin(dx);
		sy = (int) Math.sin(dy);
		
		x = mx;
		y = my;
		
		if(ax > ay){
			t = ay - (ax >> 1);
			do{
				if(t >= 0){
					y += sy;
					t -= ax;
				}
				
				x += sx;
				t += ay;
				
				if(x == px && y == py){
					return true;
				}
			}
			while(dungeon.getTile(x, y).solid);
			return false;
		}else{
			t = ax - (ay >> 1);
			do{
				if(t >= 0){
					x += sx;
					t -= ay;
				}
				
				y += sy;
				t += ax;
				if(x == px && y == py){
					return true;
				}
			}
			while(dungeon.getTile(x, y).solid);
			return false;
		}
	}
	
	public boolean line(Coord start, Coord end){
		List<Tile> line = new ArrayList<Tile>();
		
		int x0 = start.getX();
		int y0 = start.getY();
		int x1 = end.getX();
		int y1 = end.getY();
		
		 int dx = Math.abs(x1 - x0);
	        int dy = Math.abs(y1 - y0);
	 
	        int sx = x0 < x1 ? 1 : -1; 
	        int sy = y0 < y1 ? 1 : -1; 
	 
	        int err = dx-dy;
	        int e2;
	 
	        while (true) 
	        {
	            line.add(dungeon.getTile(x0, y0));
	 
	            if (x0 == x1 && y0 == y1) 
	                break;
	 
	            e2 = 2 * err;
	            if (e2 > -dy) 
	            {
	                err = err - dy;
	                x0 = x0 + sx;
	            }
	 
	            if (e2 < dx) 
	            {
	                err = err + dx;
	                y0 = y0 + sy;
	            }
	        }
		for(Tile t : line) if(t.solid) return true;
		return false;
	}
	
	/*
	 * Util
	 */
	
	private Comparator<Node> nodeSorter = new Comparator<Node>(){
		public int compare(Node n0, Node n1){
			if(n1.getfCost() < n0.getfCost()) return +1;
			if(n1.getfCost() > n0.getfCost()) return -1;
			return 0;
		}
	};
	
	private boolean vecInList(List<Node> list, Vector2i vector){
		for(Node n : list) if(n.getTile().equals(vector)) return true;
		return false;
	}
	
	public double getDistance(Vector2i tile, Vector2i end){
		double dx = tile.getX() - end.getX();
		double dy = tile.getY() - end.getY();
		return Math.sqrt(dx * dx + dy * dy);
	}

	public Dungeon getDungeon() {
		return dungeon;
	}

	public void setDungeon(Dungeon dungeon) {
		this.dungeon = dungeon;
	}

}
