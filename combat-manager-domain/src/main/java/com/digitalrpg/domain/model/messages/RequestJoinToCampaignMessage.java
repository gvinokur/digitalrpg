package com.digitalrpg.domain.model.messages;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.characters.SystemCharacter;

@Entity
@Table(name = "campaign_join_requests")
public class RequestJoinToCampaignMessage extends Message {

	private Campaign campaign;
	
	private SystemCharacter character;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="campaign_id", referencedColumnName="id")
	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="system_character_id", referencedColumnName="id")
	public SystemCharacter getCharacter() {
		return character;
	}

	public void setCharacter(SystemCharacter character) {
		this.character = character;
	}
	
}
