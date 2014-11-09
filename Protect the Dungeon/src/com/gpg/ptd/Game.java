package com.gpg.ptd;

import java.util.Random;

import com.gpg.ptd.graphics.Font;
import com.gpg.ptd.graphics.Screen;
import com.gpg.ptd.level.Dungeon;
import com.gpg.ptd.level.Edit;
import com.gpg.ptd.level.Fight;
import com.gpg.ptd.menu.MainMenu;
import com.gpg.ptd.menu.Menu;
import com.gpg.ptd.menu.PlayerSelect;
import com.gpg.ptd.util.Key;
import com.gpg.ptd.util.Mouse;
import com.gpg.ptd.util.Timer;

public class Game {

	
	private MainComponent main;
	private Screen screen;
	private Random random;
	private Font font;
	private Key key;
	private Mouse mouse;
	private GameState state;
	private User user;
	
	private Dungeon dungeon;
	private Menu menu;
	
	String motd = "";
	
	float amount;
	
	Timer time = new Timer(100, true);
		
	public Game(MainComponent main, User user, Key key, Mouse mouse, Screen screen, Random random){
		this.main = main;
		this.screen = screen;
		this.random = random;
		this.key = key;
		this.mouse = mouse;
		this.user = user;
		
		state = new GameState();
		font = new Font(); 
		switchState(4);
		
		init();
		
				
	}
	
	private void init(){
		
		//TODO: THIS CAN READ FROM URL!
		//http://www.coderblog.de/sending-data-from-java-to-php-via-a-post-request/
//		try {
//			URL url = new URL("http://games.giftedpineapples.com/ptd/motd.html");
//			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
//			String inLine;
//			while((inLine  = in.readLine()) != null) motd = inLine;
//			in.close();
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} 
	}
	
	public void update(){	
		time.update();
		key.update();
		switch(state.getState()){
			case 1: 
				menu.update();
				break;
			case 2:
				menu.update();
				break;
			case 3:
				dungeon.update();
				break;
			case 4:
				dungeon.update();
				break;
			case 5:
				//World map update
				break;
			default:
				menu.update();
				break;	
		}
		
		
	}
	
	public void render(){
		screen.clear();
		
		switch(state.getState()){
			case 1: 
				menu.render(screen);
				break;
			case 2:
				menu.render(screen);
				break;
			case 3:
				dungeon.render(screen);
				break;
			case 4:
				dungeon.render(screen);
				break;
			case 5:
				//World map render
				break;
			default:
				menu.render(screen);
				break;	
		}
		
		//Is state = dungeon state, render. 
		
		
//		font.render("X: " + mouse.getX(), 20, 20, 0xffffff, screen);
//		font.render("Y: " + mouse.getY(), 20, 35, 0xffffff, screen);
//		
		//Font Test
//		font.render("!\"#$%&'()`+,-./", 10, 10, 0xFFff0000, 1, true, screen);
//		font.render("0123456789:;<_>?", 10, 30, 0xFF00ff00, 1, true, screen);
//		font.render("@ABCDEFGHIJKLMNO", 10, 50, 0xFF0000ff, 1, true, screen);
//		font.render("PQRSTUVWXYZ[\\]^a", 10, 80, 0xFFff00FF, 1, true, screen);
//		font.render("bcdefghiklmnopq", 10, 100, 0xFFffFF00, 1, true, screen);
//		font.render("rstuvwxyz", 10, 120, 0xFF00ffff, 1, true, screen);
		
//		font.render(time.getTimeSting(), 450, 560, 0xffffffff, 1, true, screen);
		
		motd = "Semi-working edit mode. Please test for awkwardness. Press E to check/save.";
//		font.render(motd, (screen.width / 2) - ((motd.length() / 2) * font.SPACING), 700, 0xfffffff, 1, true, screen);
		
		Font.render(main.getFps() + "fps " + main.getUps() + "ups", 10, 688, 0xffffffff, 1, true, screen);
				
		for(int i = 0; i < main.getPixels().length; i++) main.getPixels()[i] = screen.pixels[i];
		
		
	}
	
	public void switchState(int state){
		
		/*
		 * STATES:
		 * 
		 * 1 - Login
		 * 2 - Player select
		 * 3 - Edit mode
		 * 4 - Fight mode
		 * 5 - World map
		 */
		
		switch(state){
			case 1:
				menu = new MainMenu(font, this, mouse, key);
				this.state.setState(1);
				break;
			case 2:
				menu = new PlayerSelect(this, font, mouse, key, random);
				this.state.setState(2);
				break;
			case 3:
				dungeon = new Edit(this.state, mouse, key, font, screen, random);
				this.state.setState(3);
				break;
			case 4:
				dungeon = new Fight(this.state, mouse, key, font, screen, random);
				this.state.setState(4);
				break;
			case 5:
				this.state.setState(5);
				break;
			case 6:
				this.state.setState(6);
				break;
			default:
				menu = new MainMenu(font, this, mouse, key);
				this.state.setState(1);
				break;
		}
	}

	public Mouse getMouse() {
		return mouse;
	}

	public void setMouse(Mouse mouse) {
		this.mouse = mouse;
	}

	 
	
}
