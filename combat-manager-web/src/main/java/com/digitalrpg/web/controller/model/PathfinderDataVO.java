package com.digitalrpg.web.controller.model;

import com.digitalrpg.domain.model.characters.pathfinder.PathfinderCharacterProperties;

public class PathfinderDataVO {

	private String race;
	
	private String characterClass;
	
	private int hp;
	
	private int strength;
	private int dexterity;
	private int constitution;
	private int intelligence;
	private int wisdom;
	private int charisma;
	
	public int getStrength() {
		return strength;
	}
	public void setStrength(int strength) {
		this.strength = strength;
	}
	public int getDexterity() {
		return dexterity;
	}
	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}
	public int getConstitution() {
		return constitution;
	}
	public void setConstitution(int constitution) {
		this.constitution = constitution;
	}
	public int getIntelligence() {
		return intelligence;
	}
	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}
	public int getWisdom() {
		return wisdom;
	}
	public void setWisdom(int wisdom) {
		this.wisdom = wisdom;
	}
	public int getCharisma() {
		return charisma;
	}
	public void setCharisma(int charisma) {
		this.charisma = charisma;
	}
	public PathfinderCharacterProperties toPathfinderProperties() {
		PathfinderCharacterProperties properties = new PathfinderCharacterProperties();
		properties.setCharacterClass(characterClass);
		properties.setRace(race);
		properties.setHp(hp);
		properties.setCharisma(charisma);
		properties.setConstitution(constitution);
		properties.setDexterity(dexterity);
		properties.setIntelligence(intelligence);
		properties.setStrength(strength);
		properties.setWisdom(wisdom);
		return properties;
	}
	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	public String getCharacterClass() {
		return characterClass;
	}
	public void setCharacterClass(String characterClass) {
		this.characterClass = characterClass;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	
	
	
}
