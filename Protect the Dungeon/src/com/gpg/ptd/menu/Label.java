package com.gpg.ptd.menu;

import com.gpg.ptd.graphics.Font;
import com.gpg.ptd.graphics.Screen;

public class Label {
	
	private Font font;

	protected String text;
	protected int x, y;
	protected int color;
	protected int scale;
	
	public Label(String text, int x, int y, int color, int scale, Font font){
		this.text = text;
		this.x = x - ((text.length() / 2) * 14);
		this.y = y;
		this.color = color;
		this.scale = scale;
		this.font = font;
	}
	
	public void render(Screen screen){
		font.render(text, x, y, color, scale, true, screen);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}
}
