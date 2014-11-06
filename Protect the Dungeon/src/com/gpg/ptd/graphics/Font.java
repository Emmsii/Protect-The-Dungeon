package com.gpg.ptd.graphics;


public class Font {

	public final static int SIZE = 16;
	public final static int SPACING = 7;
		
	private static String chars = "!\"#$%&'()`+,-./0" +
								  "123456789:;<_>?@" +
								  "ABCDEFGHIJKLMNOP" +
								  "QRSTUVWXYZ[\\]^ab" +
								  "cdefghijklmnopqrstuvwxyz";
	
	public Font(){}
	
	public static void render(String msg, int x, int y, int color, float scale, boolean outline, Screen screen){
		if(x == -1) x = (int) ((screen.width / 2) - ((msg.length() / 2) * (SPACING * scale)));
		if(y == -1) y = (int) ((screen.height / 2) - (SIZE * scale));
		
		if(msg == null) System.err.println("Could not draw font.");
		for(int i = 0; i < msg.length(); i++){
			int c = chars.indexOf(msg.charAt(i));
			if(c < 0) continue;
			if(outline) screen.renderFont(c % SIZE, c / SIZE, (int) (x + (i * (SPACING * scale))), y, color, scale, SpriteSheet.fontOutline);
			else{
				screen.renderFont(c % SIZE, c / SIZE, (int) (x + (i * (SPACING * scale) + 1)), y + 1, 0xff000000, scale, SpriteSheet.font);
				screen.renderFont(c % SIZE, c / SIZE, (int) (x + (i * (SPACING * scale))), y, color, scale, SpriteSheet.font);
			}
			
		}
	}

	public int getSPACING() {
		return SPACING;
	}
}
