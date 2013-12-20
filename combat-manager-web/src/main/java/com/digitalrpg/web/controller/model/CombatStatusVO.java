package com.digitalrpg.web.controller.model;

public class CombatStatusVO {

	private Long currentCharacterId;
	
	private Boolean finished;

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

}
