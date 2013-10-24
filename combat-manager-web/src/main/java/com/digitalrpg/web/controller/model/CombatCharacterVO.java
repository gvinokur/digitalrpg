package com.digitalrpg.web.controller.model;

public class CombatCharacterVO {

	private CharacterVO characterVO;
	
	private Long initiative;
	
	private Boolean hidden;

	public CharacterVO getCharacterVO() {
		return characterVO;
	}

	public void setCharacterVO(CharacterVO characterVO) {
		this.characterVO = characterVO;
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
