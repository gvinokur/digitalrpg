package com.digitalrpg.domain.model.messages;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.digitalrpg.domain.model.Campaign;

@Entity
@Table(name="campaign_request_accepts")
public class AcceptRequestMessage extends Message {

	private Campaign campaign;

	@ManyToOne
	@JoinColumn(name="campaign_id", referencedColumnName="id", updatable = false)
	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}
	
}
