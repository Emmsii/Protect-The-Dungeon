package com.gpg.ptd.entity.item;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.gpg.ptd.entity.mob.Player;

public class Inventory {

	protected Player player;
	
	protected int gold;
	protected List<Item> items = new ArrayList<Item>();
	
	public Inventory(Player player){
		this.player = player;
	}
	
	public Inventory(){
		
	}
	
	public void update(){
		removeItems();
	}
	
	public void addItem(Item item){
		items.add(item);
	}
	
	public Item takeItem(int id){
		for(Item i : items){
			if(id == i.getId()){
				Item toTake = i;
				i.setRemoved(true);
				return toTake;
			}
		}
		return null;
	}
	
	public String getAmount(){
		String result = "";
		
		if(gold < 1000) result = Integer.toString(gold);
		if(gold >= 1000) result = Integer.toString(gold / 1000) + "k";
		if(gold >= 1000000) result = Integer.toString(gold / 1000000) + "m";
		if(gold >= 1000000000) result = Integer.toString(gold / 1000000000) + "b";
		
		return result;
	}
	
	public void addGold(int g){
		gold += g;
	}
	
	public int takeGold(int g){
		if(gold - g >- 0){
			gold -= g;
			return g;
		}else{
			return -1;
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
}
