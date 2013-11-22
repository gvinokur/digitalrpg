package com.digitalrpg.web.controller.model;

import java.util.SortedSet;

public class CombatVO {

	private Long id;
	
	private String name;
	
	private String description;
	
	private SortedSet<CombatCharacterVO> combatCharacters;
	
	private CampaignVO campaign;

	public CampaignVO getCampaign() {
		return campaign;
	}

	public void setCampaign(CampaignVO campaign) {
		this.campaign = campaign;
	}

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

	public SortedSet<CombatCharacterVO> getCombatCharacters() {
		return combatCharacters;
	}

	public void setCombatCharacters(SortedSet<CombatCharacterVO> combatCharacters) {
		this.combatCharacters = combatCharacters;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
