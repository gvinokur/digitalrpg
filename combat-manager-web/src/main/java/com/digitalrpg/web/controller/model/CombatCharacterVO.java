package com.digitalrpg.web.controller.model;

public class CombatCharacterVO implements Comparable<CombatCharacterVO>{

	private CharacterVO characterVO;
	
	private Long initiative;
	
	private Boolean hidden;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	/**
	 * Sorts by initiative from highest to lowest
	 * 
	 * @param o
	 * @return
	 */
	public int compareTo(CombatCharacterVO o) {
		int res = -this.initiative.compareTo(o.initiative);
		if(res == 0) {
			res = this.characterVO.getName().compareTo(o.characterVO.getName());
		}
		return res;
	}
	
}
