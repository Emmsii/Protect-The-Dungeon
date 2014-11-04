package com.gpg.ptd;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

import javax.swing.JFrame;

import com.gpg.ptd.graphics.Screen;
import com.gpg.ptd.util.Key;
import com.gpg.ptd.util.Mouse;

public class MainComponent extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final String NAME = "Protect the Dungeon";
	
	private Random random;
	private Screen screen;
	private Key key;
	private Mouse mouse;
	private Game game;
	private User user;
			
	private boolean running = false;
	private int fps = 0;
	private int ups = 0;
	private double msp = 0;
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		
	public MainComponent(){
		init();
	}
	
	public static void main(String[] args){
		MainComponent main = new MainComponent();
		Dimension size = new Dimension(WIDTH, HEIGHT);
		main.setPreferredSize(size);
		main.setMinimumSize(size);
		main.setMaximumSize(size);
		
		JFrame frame = new JFrame(NAME);
												
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(main);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		main.start();
	}
	
	public void start(){
		running = true;
		new Thread(this, "main").start();
	}
	
	public void stop(){
		running = false;
	}
	
	public void run() {
		double nsPerFrame = 1000000000.0 / 60.0;
		double unprocessedTime = 0;
		double maxSkipFrames = 10;
		
		long lastTime = System.nanoTime();
		long lastFrameTime = System.currentTimeMillis();
		int frames = 0;
		int updates = 0;
		while(running){
			long now = System.nanoTime();
			double passedTime = (now - lastTime) / nsPerFrame;
			lastTime = now;
			
			if(passedTime < -maxSkipFrames) passedTime = -maxSkipFrames;
			if(passedTime > maxSkipFrames) passedTime  = maxSkipFrames;
			
			unprocessedTime += passedTime;
			
			boolean render = false;
			while(unprocessedTime > 1){
				unprocessedTime -= 1;
				update();
				updates++;
				render = true;
				
				if(render){
					render();
					frames++;
				}
				
				if(System.currentTimeMillis() > lastFrameTime + 1000){
					fps = frames;
					ups = updates;
					msp = 1000.0 / frames;
					System.out.println(fps + " fps, " + ups + "ups | " + msp + " ms per frame.");
					lastFrameTime += 1000;
					frames = 0;
					updates = 0;
					msp = 0;
				}
			}
		}
	}
	
	private void init(){
		random = new Random();
		screen = new Screen(WIDTH, HEIGHT);
		key = new Key();
		mouse = new Mouse();
		addKeyListener(key);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		game = new Game(this, user, key, mouse, screen, random);
	}
	
	private void update(){	
		game.update();
	}
	
	private void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			requestFocus();
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH + 2, HEIGHT + 2);
		g.setFont(new Font("Arial", Font.BOLD, 12));
		g.setColor(Color.WHITE);

		//Render here
		game.render();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
//		if(!hasFocus()) g.drawString("Click to focus.", WIDTH / 2 - 33, HEIGHT / 2 + 9);
		
		g.dispose();
		bs.show();
	}

	public int getFps(){
		return fps;
	}

	public int getUps(){
		return ups;
	}
	
	public int[] getPixels(){
		return pixels;
	}
	
}
