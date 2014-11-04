package com.gpg.ptd.menu;

import com.gpg.ptd.graphics.Font;
import com.gpg.ptd.graphics.Screen;
import com.gpg.ptd.graphics.Sprite;
import com.gpg.ptd.graphics.SpriteSheet;

public class Button {
	
	private Font font;

	private int x, y;
	private int w, h;
	private String text;
	private Sprite image;
	private int scale;
	
	private int id;
	
	private boolean hidden;
	private boolean imageBg;
	private boolean hover = false;
	private int pressTime = 0;
	private int color = 0xffffffff;
	
	public Button(int x, int y, int w, int h, int id, boolean hidden, String text, Font font){
		if(text.length() > 12) throw new RuntimeException("Button text length is greater than 12.");
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.id = id;
		this.hidden = hidden;
		this.text = text;
		this.font = font;
		this.image = null;
	}
	
	public Button(int x, int y, int id, int scale, boolean hidden, Sprite image, Font font){
		this.x = x;
		this.y = y;
		this.id = id;
		this.hidden = hidden;
		this.image = image;
		this.font = font;
		this.text = null;
		this.scale = scale;
		
		w = image.size * scale;
		h = image.size * scale;
	}
	
	public Button(int x, int y, int id, boolean hidden, boolean imageBg, String text, Font font){
		if(text.length() > 12) throw new RuntimeException("Button text length is greater than 12.");
		this.x = x;
		this.y = y;
		this.id = id;
		this.hidden = hidden;
		this.imageBg = imageBg;
		this.text = text;	
		this.font = font;
		this.image = null;

		w = text.length() * font.getSPACING();
		h = 20;
	}
	
	public void update(int x, int y, int button){
		if(collides(x, y)){
			setHover(true);
			if(button != -1) setPressTime(5);
		}else setHover(false);
		
		
		color = 0xff505050;
		if(hover) color = 0xff303030;
		if(pressTime > 0){
			color = 0xff505050;
			pressTime--;
		}
		
	}
		
	public void render(Font font, Screen screen){
		int hov = 0;
		if(hover) hov = 3;
				
		if(text != null){
			screen.render(x - 8, y - 8, 0 + hov, 0, 16, 1, SpriteSheet.gui);
			screen.render(x + w - 16 + 8, y - 8, 2 + hov, 0, 16, 1, SpriteSheet.gui);
			screen.render(x + w - 16 + 8, y + 8, 2 + hov, 1, 16, 1, SpriteSheet.gui);
			screen.render(x - 16 + 8, y + 8, 0 + hov, 1, 16, 1, SpriteSheet.gui);
			for(int i = 0; i < text.length(); i++){
				screen.render(x + (i * 16), y - 8, 1 + hov, 0, 16, 1, SpriteSheet.gui);
				screen.render(x + (i * 16), y + 8, 1 + hov, 1, 16, 1, SpriteSheet.gui);
			}
			font.render(text, x, y, color, 1, true, screen);
		}
		
		if(image != null){
			screen.render(x - 8, y - 8, 0 + hov, 0, 16, 1, SpriteSheet.gui);
			screen.render(x + w, y - 8, 2 + hov, 0, 16, 1, SpriteSheet.gui);
			screen.render(x + w, y + h, 2 + hov, 1, 16, 1, SpriteSheet.gui);
			screen.render(x - 8, y + h, 0 + hov, 1, 16, 1, SpriteSheet.gui);
			
			screen.renderSprite(y, x, scale, -1, image);
		}
		
		
	}

	public boolean pressed(){
		if(pressTime == 1) return true;
		return false;
	}
	
	public boolean collides(int xm, int ym){
		if(xm > x - 8 && xm < x + w + 8 && ym > y - 8 && ym < y + h + 8) return true;
		return false;
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

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isHover() {
		return hover;
	}

	public void setHover(boolean hover) {
		this.hover = hover;
	}

	public int getPressTime() {
		return pressTime;
	}

	public void setPressTime(int pressTime) {
		this.pressTime = pressTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
}
