package com.gpg.ptd.level.object;

import com.gpg.ptd.graphics.Screen;
import com.gpg.ptd.graphics.Sprite;

public class Obj {

	protected int id;
	protected int x, y;
	
	protected boolean valid;
	protected boolean selected;

	
	public Obj(int id, int x, int y){
		this.id = id;
		this.x = x;
		this.y = y;
	}
	
	public void update(){
		
	}
	
	public void render(int x, int y, Screen screen){
		
	}

	public void updatePos(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
