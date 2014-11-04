package com.gpg.ptd.util;

public class Timer {

	private int time;
	private int maxTime;	
	
	private int second;
	private int minute;
	
	private boolean finished = false;
	private boolean backwards;
	
	private String resultTime;
	
	public Timer(int maxTime, boolean backwards){
		this.backwards = backwards;
		
		if(backwards){
			this.time = maxTime;
			//TODO: FIX ME!
			
			int seconds = maxTime / 60;
			int minutes = seconds / 60;
			
			minute = minutes;
			second = minutes % seconds;
//			System.out.println(seconds);
//			System.out.println(minutes);
//			
//			System.out.println(second);
//			System.out.println(minute);
			
		}else this.maxTime = maxTime; 
	}
	
	public void update(){
		if(!finished){
			if(!backwards) tickForwards();
			else tickBackwards();
			createString();
		}
	}
	
	private void tickForwards(){
		time++;
		
		if(time % 60 == 0) second++;
		if(second == 60){
			minute++;
			second = 0;
		}
		
		if(time == maxTime) finished = true;
	}
	
	private void tickBackwards(){
		time--;
		
		if(time % 60 == 0) second--;
		if(second == 0){
			minute--;
			second = 60;
		}
		
		if(time == 0) finished = true;
	}
	
	private void createString(){
		String secondStr = "";
		
		if(second < 10) secondStr = "0" + second;
		else secondStr = Integer.toString(second);
		
		resultTime = minute + ":" + secondStr + "(" + time + ")";
	}
	
	public String getTimeSting(){
		return resultTime;
	}
}
