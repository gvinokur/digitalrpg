package com.digitalrpg.web.controller.model;

import org.hibernate.validator.constraints.NotEmpty;

public class CharacterVO {

	@NotEmpty
	private String name;
	
	private String description;
	
	private String pictureUrl;
	
	private String race;
	
	private String characterClass;
	
	private int hp;
	
	private String system;
	
	private PathfinderDataVO pathfinder;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public PathfinderDataVO getPathfinder() {
		return pathfinder;
	}

	public void setPathfinder(PathfinderDataVO pathfinder) {
		this.pathfinder = pathfinder;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	
}
