package com.gpg.ptd.menu;

import com.gpg.ptd.graphics.Font;
import com.gpg.ptd.graphics.Screen;
import com.gpg.ptd.util.Key;

public class TextField {

	private Key key;
	private Font font;
	
	protected int x, y;
	protected int w, h;
	protected int id;
	
	protected int wait = 0;
	protected boolean numbers;
	protected boolean password;
	protected boolean selected = false;
	
	protected int length;
	protected String text = "";
	
	public TextField(int x, int y, int id, int length, boolean numbers, boolean password, Font font, Key key){
		this.x = x - ((length / 2) * font.getSPACING());
		this.y = y;
		this.id = id;
		this.length = length;
		this.numbers = numbers;
		this.password = password;
		
		w = length * font.getSPACING();
		h = 18;
		
		this.font = font;
		this.key = key;
	}
	
	public void update(){
		key.update();
		if(wait > 0){
			wait--;
			return;
		}
		
		if(key.a) press("a", key.shift);
		if(key.b) press("b", key.shift);
		if(key.c) press("c", key.shift);
		if(key.d) press("d", key.shift);
		if(key.e) press("e", key.shift);
		if(key.f) press("f", key.shift);
		if(key.g) press("g", key.shift);
		if(key.h) press("h", key.shift);
		if(key.i) press("i", key.shift);
		if(key.j) press("j", key.shift);
		if(key.k) press("k", key.shift);
		if(key.l) press("l", key.shift);
		if(key.m) press("m", key.shift);
		if(key.n) press("n", key.shift);
		if(key.o) press("o", key.shift);
		if(key.p) press("p", key.shift);
		if(key.q) press("q", key.shift);
		if(key.r) press("r", key.shift);
		if(key.s) press("s", key.shift);
		if(key.t) press("t", key.shift);
		if(key.u) press("u", key.shift);
		if(key.v) press("v", key.shift);
		if(key.w) press("w", key.shift);
		if(key.x) press("x", key.shift);
		if(key.y) press("y", key.shift);
		if(key.z) press("z", key.shift);
		
		if(key.space) press(" ", false);
		
		if(numbers){
			if(key.one) press("1", false);
			if(key.two) press("2", false);
			if(key.three) press("3", false);
			if(key.four) press("4", false);
			if(key.five) press("5", false);
			if(key.six) press("6", false);
			if(key.seven) press("7", false);
			if(key.eight) press("8", false);
			if(key.nine) press("9", false);
			if(key.zero) press("0", false);
		}
		
		
		if(key.backspace && text.length() > 0 && selected){
			text = text.substring(0, text.length() - 1);
			wait = 10;
		}
		
	}
	
	public void render(Screen screen){	
		for(int ya = y; ya < y + h; ya++){
			for(int xa = x; xa < x + w; xa++){
				int col = 0xff7d7d7d;
				if(xa == x || ya == y || xa == x + w - 1 || ya == y + h - 1){
					if(selected) col = 0xffffffff;
					else col = 0xffbfbfbf;
				}
				screen.renderPixel(xa, ya, col);
			}
		}
		
		
		if(password){
			String pass = "";
			for(int i = 0; i < text.length(); i++) pass = pass + "x";
			font.render(pass, x, y + 1, 0xffffffff, 1, true, screen);
		}else{
			font.render(text, x, y + 1, 0xffffffff, 1, true, screen);
		}
	}
	
	public boolean collides(int xp, int yp){
		if(xp > x && yp > y && xp < x + w && yp < y + h) return true;
		return false;
	}
	
	public void press(String msg, boolean cap){
		if(!selected) return;
		if(text.length() >= length) return;
		if(cap) msg = msg.toUpperCase();
		text = text + msg;
		wait = 10;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
