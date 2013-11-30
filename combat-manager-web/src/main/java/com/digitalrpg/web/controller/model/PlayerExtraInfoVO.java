package com.digitalrpg.web.controller.model;

import javax.validation.constraints.NotNull;

public class PlayerExtraInfoVO {

	private Boolean hidden;
	
	@NotNull
	private Long initative;

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public Long getInitative() {
		return initative;
	}

	public void setInitative(Long initative) {
		this.initative = initative;
	}
	
}
