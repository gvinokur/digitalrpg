package com.digitalrpg.web.controller.model;

public class CombatCharacterVO{

	
	private Long initiative;
	
	private Boolean hidden;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getInitiative() {
		return initiative;
	}

	public void setInitiative(Long initiative) {
		this.initiative = initiative;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

}
