package com.digitalrpg.web.controller.model;


public class InviteClaimCharacterMessageVO extends MessageVO{

	private Long characterId;
	
	private String characterName;

	public Long getCharacterId() {
		return characterId;
	}

	public void setCharacterId(Long characterId) {
		this.characterId = characterId;
	}

	public String getCharacterName() {
		return characterName;
	}

	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}
	
}
