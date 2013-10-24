package com.digitalrpg.web.controller.model;

import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.NotEmpty;

public class CreateCombatVO {

	private Long campaignId;
	
	@NotEmpty
	private String name;
	
	private String description;
	
	private List<Long> players;
	
	private Map<Long, PlayerExtraInfoVO> extraInfo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Long> getPlayers() {
		return players;
	}

	public void setPlayers(List<Long> players) {
		this.players = players;
	}

	public Map<Long, PlayerExtraInfoVO> getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(Map<Long, PlayerExtraInfoVO> extraInfo) {
		this.extraInfo = extraInfo;
	}

	public Long getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
	}
	
}
