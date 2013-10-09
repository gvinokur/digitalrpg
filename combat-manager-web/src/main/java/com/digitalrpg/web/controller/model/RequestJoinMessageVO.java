package com.digitalrpg.web.controller.model;

import com.digitalrpg.domain.model.characters.SystemCharacter;

public class RequestJoinMessageVO extends MessageVO{

	private Long campaignId;
	
	private String campaignName;
	
	private SystemCharacter character;

	public SystemCharacter getCharacter() {
		return character;
	}

	public void setCharacter(SystemCharacter character) {
		this.character = character;
	}

	public Long getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
	
}
