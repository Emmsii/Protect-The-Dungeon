package com.gpg.ptd.entity.mob;

public class MobType {

	public static MobType orc = new MobType("orc", 0.75f, 10, 32, 100, 10, 10, 1, 1, 75);
	
	public String name;
	public float speed;
	public int weight;
	public int sight;
	
	public int health;
	public int toughness;
	public int strength;
	public int archery;
	public int magic;
	
	public int totalLevel;
	
	public int scoreReward;
	
	public MobType(String name, float speed, int weight, int sight, int health, int toughness, int strength, int archery, int magic, int scoreReward){
		this.name = name;
		this.speed = speed;
		this.weight = weight;
		this.sight =  sight;
		this.health = health;
		this.toughness = toughness;
		this.strength = strength;
		this.archery = archery;
		this.magic = magic;
		this.scoreReward = scoreReward;
		
		this.totalLevel = (int) Math.floor(((strength + health + toughness) + (archery / 2) + (magic / 2)) * 0.1);
		System.out.println("Mob Total Level: " + totalLevel);
	}
}
