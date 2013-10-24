package com.digitalrpg.web.controller.model;

import java.util.List;

public class CombatVO {

	private Long id;
	
	private String name;
	
	private String description;
	
	private List<CombatCharacterVO> combatCharacters;

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

	public List<CombatCharacterVO> getCombatCharacters() {
		return combatCharacters;
	}

	public void setCombatCharacters(List<CombatCharacterVO> combatCharacters) {
		this.combatCharacters = combatCharacters;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
