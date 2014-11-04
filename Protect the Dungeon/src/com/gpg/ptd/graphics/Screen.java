package com.gpg.ptd.graphics;

import com.gpg.ptd.level.tile.Tile;

public class Screen {

	public int width, height;
	public int xOffset, yOffset;
	public int[] pixels;
	
	public Screen(int width, int height){
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}
	
	//TODO: THIS METHOD CAN DO TRANSITIONS!
	public void fill(int col, float delta){
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				pixels[x + y * width] = blend(pixels[x + y * width], col, delta);
			}
		}
	}
	
	public void renderPixel(int x, int y, int col){
		if(x  < 0 || y < 0 || x >= width || y >= height) return;
		pixels[x + y * width] = col;
		
	}
	
	public void renderPixelBlend(int x, int y, int colA, int colB){
		if(x  < 0 || y < 0 || x >= width || y >= height) return;
		pixels[x + y * width] = blend(colA, colB, 0.5f);
		
	}
	
	
	public void render(int xp, int yp, int xb, int yb, int size, int scale, SpriteSheet sheet){
		int sheetSize = sheet.SIZE;
		for(int y = 0; y < size * scale; y++){
			int ya = y + yp;
			for(int x = 0; x < size * scale; x++){
				int xa = x + xp;
				if(xa < -sheetSize || xa >= width || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				int xs = x / scale + (xb * (size));
				int ys = y / scale + (yb * (size));
				int col = sheet.pixels[xs + ys * sheetSize];
				if(col != 0xffff00ff && col != 0xff7f007f) pixels[xa + ya * width] = col;
			}
		}
	}
	
	public void renderSprite(int xp, int yp, int scale, int color, Sprite sprite){
		int size = sprite.size;
		for(int y = 0; y < size * scale; y++){
			int ya = y + yp;
			for(int x = 0; x < size * scale; x++){
				int xa = x + xp;
				if(xa < 0 || ya < 0 || xa >= width || ya >= height) continue;
				int col = sprite.pixels[(x / scale) + (y / scale) * size];
				if(col != 0xffff00ff && col != 0xff7f007f){
					if(color != -1) col = blend(col, color, 0.5f);
					pixels[xa + ya * width] = col;
				}
			}
		}
	}
	
	public void renderMob(int xp, int yp, int dir, Sprite sprite){
		for(int y = 0; y < sprite.size; y++){
			int ya = y + yp;
			int ys = y;
			
			for(int x = 0; x < sprite.size; x++){
				int xa = x + xp;
				int xs = x;
				if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				int col = sprite.pixels[xs + ys * sprite.size];
				if(col != 0xffff00ff && col != 0xff7f007f) pixels[xa + ya * width] = col;
			}
		}
	}
	
	public void renderTile(int xp, int yp, int color, Tile tile){
		xp -= xOffset;
		yp -= yOffset;
		int size = tile.sprite.size;
		for(int y = 0; y < size; y++){
			int ya = y + yp;
			for(int x = 0; x < size; x++){
				int xa = x + xp;
				if(xa < -size || xa >= width || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				int col = tile.sprite.pixels[x + y * size];
				
				if(color != -1) col = blend(col, color, 0.5f);
				
				if(col != 0xffff00ff && col != 0xff7f007f) pixels[xa + ya * width]  = col;
			}
		}
	}
	
	public void renderFont(int a, int b, int xp, int yp, int color, float scale, SpriteSheet sheet){
		int size = sheet.SIZE;
		for(int y = 0; y < 8 * scale; y++){
			int ya = y + yp;
			for(int x = 0; x < 8 * scale; x++){
				int xa = x + xp;
				if(xa < -size || xa >= width || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				int xs = (int) ((x / scale) + (a * 8));
				int ys = (int) ((y / scale) + (b * 8));
				int col = sheet.pixels[xs + ys * size];
				if(col != 0xffff00ff && col != 0xff7f007f){
					if(col == 0xff000000) pixels[xa + ya * width] = 0xff000000;
					else pixels[xa + ya * width] = color;
				}
			}
		}
	}
	
	public void clear(){
		for(int i = 0; i < pixels.length; i++) pixels[i] = 0;
	}
	
	public void setOffset(int xOffset, int yOffset){
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public int blend(int a, int b, float ratio){
		if(ratio > 1f) ratio = 1f;
		else if(ratio < 0f) ratio = 0f;
		
		float iRatio = 1.0f - ratio;
		
		int aA = (a >> 24 & 0xff);
		int aR = ((a & 0xff0000) >> 16);
		int aG = ((a & 0xff00) >> 8);
		int aB =(a & 0xff);
		
		int bA = (b >> 24 & 0xff);
		int bR = ((b & 0xff0000) >> 16);
		int bG = ((b & 0xff00) >> 8);
		int bB =(b & 0xff);
		
		int A = (int) ((aA * iRatio) + (bA * ratio));
		int R = (int) ((aR * iRatio) + (bR * ratio));
		int G = (int) ((aG * iRatio) + (bG * ratio));
		int B = (int) ((aB * iRatio) + (bB * ratio));
		
		return A << 24 | R << 16 | G << 8 | B;
	}
}

