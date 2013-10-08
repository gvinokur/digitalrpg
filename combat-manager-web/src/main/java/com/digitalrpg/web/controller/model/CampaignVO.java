package com.digitalrpg.web.controller.model;

import org.hibernate.validator.constraints.NotEmpty;

public class CampaignVO {

	@NotEmpty
	private String name;
	
	private String description;
	
	private Boolean isPublic;

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

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}


}
