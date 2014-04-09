package com.digitalrpg.web.controller.model;

import org.hibernate.validator.constraints.NotEmpty;

import com.digitalrpg.domain.model.SystemType;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.domain.model.characters.SystemProperties;

public class CreateCharacterVO {

	@NotEmpty
	private String name;
	
	private String description;
	
	private String pictureUrl;
	
	private PathfinderDataVO pathfinder;

	private Long campaignId;
	
	private Long messageId;
	
	private User owner;
	
	private Long id;
	
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

	public PathfinderDataVO getPathfinder() {
		return pathfinder;
	}

	public void setPathfinder(PathfinderDataVO pathfinder) {
		this.pathfinder = pathfinder;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public Long getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	
	public SystemProperties getSystemProperties(SystemType systemType) {
		switch (systemType) {
		case Pathfinder:
			return pathfinder.toSystemProperties();
		default:
			return null;
		}
	}
	
}
