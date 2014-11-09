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
//				if(dungeon.mob[x + xi][y + yi] != -1) continue;
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

	public boolean line(int x0, int y0, int x1, int y1){
		int w = x1 - x0;
		int h = y1 - y0;
		int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
		if(w < 0) dx1 = -1;
		else if(w > 0) dx1 = 1;
		if(h < 0) dy1 = -1;
		else if(h > 0) dy1 = 1;
		if(w < 0) dx2 = -1;
		else if(w > 0) dx2 = 1;
		
		int longest = Math.abs(w);
		int shortest = Math.abs(h);
		
		if(!(longest > shortest)){
			longest =Math.abs(h);
			shortest = Math.abs(w);
			if(h < 0) dy2 = -1;
			else if(h > 0) dy2 = 1;
			dx2 = 0;
		}
		
		int numerator = longest >> 1;
		for(int i = 0; i <= longest; i++){
			if(dungeon.getTile(x0 / 32, y0 / 32).solid) return true;	
			numerator += shortest;
			if(!(numerator < longest)){
				numerator -= longest;
				x0 += dx1;
				y0 += dy1;
			}else{
				x0 += dx2;
				y0 += dy2;
			}
		}
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
