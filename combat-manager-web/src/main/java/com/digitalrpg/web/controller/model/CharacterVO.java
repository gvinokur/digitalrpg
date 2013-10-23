package com.digitalrpg.web.controller.model;

import com.digitalrpg.domain.model.User;

public class CharacterVO {

	private Long id;
	
	private String name;
	
	private String description;
	
	private String pictureUrl;
	
	private CampaignVO campaign;
	
	private User owner;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public CampaignVO getCampaign() {
		return campaign;
	}

	public void setCampaign(CampaignVO campaign) {
		this.campaign = campaign;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	
}
