package com.gpg.ptd.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.gpg.ptd.Game;
import com.gpg.ptd.graphics.Font;
import com.gpg.ptd.graphics.Screen;
import com.gpg.ptd.util.Key;
import com.gpg.ptd.util.Mouse;

public class MainMenu extends Menu{

	private Game game;
	private String error = "";
		
	public MainMenu(Font font, Game game, Mouse mouse, Key key){
		super(font, mouse, key);
		this.game = game;
		
		textFields.add(new TextField(1280 / 2, (720 / 2) - 35, 0, 12, false, false, font, key));
		textFields.add(new TextField(1280 / 2, (720 / 2) + 15, 1, 12, true, true, font, key));
		
		labels.add(new Label("Username", 1280 / 2, (720 / 2) - 55, 0xffffff, 1, font));
		labels.add(new Label("Password", 1280 / 2, (720 / 2) - 5, 0xffffff, 1, font));
		buttons.add(new Button(647, 410, 0, false, true, "Login", font));
	}
	
	public void update(){
		for(Button b : buttons){
			b.update(mouse.getX(), mouse.getY(), mouse.getButton());
			if(b.pressed() && b.getId() == 0){
				String username = "";
				String password = "";
				boolean accepted = false;
				for(TextField tf : textFields){
					if(tf.getText().length() > 0){
						accepted = true;
						if(tf.getId() == 0) username = tf.getText();
						else if(tf.getId() == 1) password = tf.getText();
					}
				}
				
				if(accepted){
					if(!checkUser(username, password)) System.out.println("Couldn't find those users, carrying on anyway.");
					else System.out.println("Logged in with: " + username + " & " + password + ".");
					game.switchState(2);
				}
				else error = "Please enter username and password.";
			}
		}
		
		for(TextField tf : textFields){
			tf.update();
			if(mouse.getButton() == 1 && tf.collides(mouse.getX(), mouse.getY())) tf.setSelected(true);
			if(mouse.getButton() == 1 && !tf.collides(mouse.getX(), mouse.getY())) tf.setSelected(false);
			if(mouse.getButton() == 3 && tf.isSelected()) tf.setSelected(false);
		}
		
	}
	
	public void render(Screen screen){
		for(Button b : buttons) b.render(font, screen);
		for(TextField tf : textFields) tf.render(screen);
		for(Label l : labels) l.render(screen);
		
		if(error != "") font.render(error, 10, 10, 0xffff0000, 1, true, screen);
	}
	
	private boolean checkUser(String user, String pass){
		
		/**
		 *     - FOR TAM -
		 * Database Requirements
		 * 
		 * 
		 * CHECK IF USERNAME AND PASSWORD ENTERED ARE CORRECT.
		 * ALSO, NEED TO GENERATE A RANDOM SESSION ID TO COUNTER MULTIPLE LOGINS.
		 * 
		 * Example is kinda shown below.
		 * 
		 * If the login details are correct, game state will switch to PLAYER SELECT screen.
		 * If the login details are correct, RETURN UNIQUE USER ID. 
		 * 
		 */
		
		String sessionId = "random_string_based_off_username_and_system_time.";
		
		String line = null;
		try {
			URL url = new URL("http://www.games.giftedpineapples.com/ptd/get.php?u=" + user + "&p=" + pass + "&s=" + sessionId);
			URLConnection connection = url.openConnection();
			connection.connect();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			line = in.readLine();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(line);
		if(line.equalsIgnoreCase("yes")) return true;
		return false;
	}
}
