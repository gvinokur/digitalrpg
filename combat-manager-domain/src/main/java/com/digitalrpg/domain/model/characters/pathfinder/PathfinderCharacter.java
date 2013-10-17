package com.digitalrpg.domain.model.characters.pathfinder;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.digitalrpg.domain.model.characters.SystemCharacter;

@Entity
@Table(name = "pathfinder_character")
public class PathfinderCharacter extends SystemCharacter {

	
	/*
	 *  All pathfinder specific information
	 */
	private String characterClass;
	private String race;
	private int hp;
	private int strength;
	private int dexterity;
	private int constitution;
	private int intelligence;
	private int wisdom;
	private int charisma;
	
	public void fromProperties(PathfinderCharacterProperties properties) {
		this.characterClass = properties.getCharacterClass();
		this.race = properties.getRace();
		this.hp = properties.getHp();
		this.strength = properties.getStrength();
		this.constitution = properties.getConstitution();
		this.dexterity = properties.getDexterity();
		this.charisma = properties.getCharisma();
		this.intelligence = properties.getWisdom();
		this.wisdom = properties.getWisdom();
	}

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

	public String getCharacterClass() {
		return characterClass;
	}

	public void setCharacterClass(String characterClass) {
		this.characterClass = characterClass;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}
}
