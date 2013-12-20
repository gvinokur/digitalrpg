package com.digitalrpg.web.controller.model;

import java.util.Set;

import com.digitalrpg.domain.model.pathfinder.PathfinderAction;
import com.digitalrpg.domain.model.pathfinder.PathfinderCondition;
import com.digitalrpg.domain.model.pathfinder.PathfinderMagicalEffect;

public class PathfinderCombatCharacterVO extends CombatCharacterVO{

	private Integer currentHitPoints;
	
	private PathfinderAction currentAction;
	
	private Set<PathfinderCondition> currentConditions;
	
	private Set<PathfinderMagicalEffect> currentMagicalEffects;
	
	private String hitPointStatus;
	
	private String conditionsAndEffectsString;
	
	private String characterClass;
	
	private String race;
	
	private int hp;
	
	private int strength;
	
	private int dexterity;
	
	private int constitution;
	
	private int intelligence;
	
	private int wisdom;
	
	private int charisma;

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

	public Integer getCurrentHitPoints() {
		return currentHitPoints;
	}

	public void setCurrentHitPoints(Integer currentHitPoints) {
		this.currentHitPoints = currentHitPoints;
	}

	public PathfinderAction getCurrentAction() {
		return currentAction;
	}

	public void setCurrentAction(PathfinderAction currentAction) {
		this.currentAction = currentAction;
	}

	public Set<PathfinderCondition> getCurrentConditions() {
		return currentConditions;
	}

	public void setCurrentConditions(Set<PathfinderCondition> currentConditions) {
		this.currentConditions = currentConditions;
	}

	public Set<PathfinderMagicalEffect> getCurrentMagicalEffects() {
		return currentMagicalEffects;
	}

	public void setCurrentMagicalEffects(
			Set<PathfinderMagicalEffect> currentMagicalEffects) {
		this.currentMagicalEffects = currentMagicalEffects;
	}

	public String getHitPointStatus() {
		return hitPointStatus;
	}

	public void setHitPointStatus(String hitPointStatus) {
		this.hitPointStatus = hitPointStatus;
	}

	public String getConditionsAndEffectsString() {
		return conditionsAndEffectsString;
	}

	public void setConditionsAndEffectsString(String conditionsAndEffectsString) {
		this.conditionsAndEffectsString = conditionsAndEffectsString;
	}
	
}
