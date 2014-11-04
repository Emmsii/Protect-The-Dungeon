package com.gpg.ptd.util;

public class Rect {

	protected int x, y;
	protected int width, height;
	
	public Rect(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public boolean intersects(Rect r){
		return !(y + height < r.getY() || y > r.getY() + r.getHeight() || x + width < r.getX() || x > r.getX() + r.getWidth());
	}
	
	public void setPos(int x, int y){
		this.x = x;
		this.y = y;
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
