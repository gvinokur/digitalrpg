package com.digitalrpg.web.controller.model;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.digitalrpg.domain.model.SystemCombatProperties;
import com.digitalrpg.domain.model.pathfinder.PathfinderCombatProperties;

public class CreateCombatVO {

	private Long campaignId;
	
	@NotEmpty
	private String name;
	
	private String description;
	
	private List<Long> players;
	
	@Valid
	private Map<Long, PlayerExtraInfoVO> extraInfo;
	
	@Valid
	private CreateCombatPathfinderPropertiesVO pathfinder;

	public CreateCombatPathfinderPropertiesVO getPathfinder() {
		return pathfinder;
	}

	public void setPathfinder(CreateCombatPathfinderPropertiesVO pathfinder) {
		this.pathfinder = pathfinder;
	}

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

	public SystemCombatProperties getSystemCombatProperties() {
		if(pathfinder != null) {
			PathfinderCombatProperties properties = new PathfinderCombatProperties();
			properties.setTurns(pathfinder.getTurns());
			properties.setRoundsPerTurn(pathfinder.getRoundsPerTurn());
			return properties;
		}
		//Do the same with other system combat properties
		return null;
	}
	
}
