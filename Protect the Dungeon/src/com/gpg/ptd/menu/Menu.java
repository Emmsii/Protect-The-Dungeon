package com.gpg.ptd.menu;

import java.util.ArrayList;
import java.util.List;

import com.gpg.ptd.graphics.Font;
import com.gpg.ptd.graphics.Screen;
import com.gpg.ptd.util.Key;
import com.gpg.ptd.util.Mouse;

public class Menu {
	
	protected Font font; 
	protected Mouse mouse;
	protected Key key;
	
	protected List<Button> buttons = new ArrayList<Button>();
	protected List<TextField> textFields = new ArrayList<TextField>();
	protected List<Label> labels = new ArrayList<Label>();
	
	public Menu(Font font, Mouse mouse, Key key){
		this.font = font;
		this.mouse = mouse;
		this.key = key;
	}
	
	public void render(Screen screen){

	}
	
	public void update(){
		
	}
}
