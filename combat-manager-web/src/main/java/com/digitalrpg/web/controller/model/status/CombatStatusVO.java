package com.digitalrpg.web.controller.model.status;

import java.util.SortedSet;

public class CombatStatusVO {

	private Long version;
	
	private Long currentCharacterId;
	
	private Boolean finished;
	
	private SortedSet<CombatCharacterStatusVO> combatCharacters;

	public Long getCurrentCharacterId() {
		return currentCharacterId;
	}

	public void setCurrentCharacterId(Long currentCharacterId) {
		this.currentCharacterId = currentCharacterId;
	}

	public Boolean getFinished() {
		return finished;
	}

	public void setFinished(Boolean finished) {
		this.finished = finished;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public SortedSet<CombatCharacterStatusVO> getCombatCharacters() {
		return combatCharacters;
	}

	public void setCombatCharacters(SortedSet<CombatCharacterStatusVO> combatCharacters) {
		this.combatCharacters = combatCharacters;
	}

}
