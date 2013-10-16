package com.digitalrpg.web.controller.model;


public class AcceptRequestJoinMessageVO extends MessageVO{

	private Long campaignId;
	
	private String campaignName;
	
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
