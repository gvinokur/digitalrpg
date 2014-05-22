package com.digitalrpg.domain.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "subscription_types")
public class SubscriptionType implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String name;
	
	private Double cost;
	
	private Boolean adFree;
	
	private Integer maxCampaigns;
	
	private Integer maxPlayerCharacters;
	
	private Integer maxCombatsPerCampaign;
	
	private Boolean enabled;

	@Id
	@Type(type = "long")
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
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

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Boolean getAdFree() {
		return adFree;
	}

	public void setAdFree(Boolean adFree) {
		this.adFree = adFree;
	}

	public Integer getMaxCampaigns() {
		return maxCampaigns;
	}

	public void setMaxCampaigns(Integer maxCampaigns) {
		this.maxCampaigns = maxCampaigns;
	}

	public Integer getMaxPlayerCharacters() {
		return maxPlayerCharacters;
	}

	public void setMaxPlayerCharacters(Integer maxPlayerCharacters) {
		this.maxPlayerCharacters = maxPlayerCharacters;
	}

	public Integer getMaxCombatsPerCampaign() {
		return maxCombatsPerCampaign;
	}

	public void setMaxCombatsPerCampaign(Integer maxCombatsPerCampaign) {
		this.maxCombatsPerCampaign = maxCombatsPerCampaign;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
}
