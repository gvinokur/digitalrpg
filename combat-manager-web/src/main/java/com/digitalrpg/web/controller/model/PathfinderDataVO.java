package com.digitalrpg.web.controller.model;

import com.digitalrpg.domain.model.characters.pathfinder.PathfinderCharacterProperties;

public class PathfinderDataVO {

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
		properties.setCharisma(charisma);
		properties.setConstitution(constitution);
		properties.setDexterity(dexterity);
		properties.setIntelligence(intelligence);
		properties.setStrength(strength);
		properties.setWisdom(wisdom);
		return properties;
	}
	
	
	
}
