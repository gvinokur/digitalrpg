package com.digitalrpg.web.controller.model.status;

public class CombatCharacterStatusVO implements Comparable<CombatCharacterStatusVO>{

	private Long order;
	
	private Boolean hidden;

	@Override
	public int compareTo(CombatCharacterStatusVO o) {
		// TODO refactor this
		if(this.getOrder() == null) {
			if(o == null) {
				return 0; 
			} else {
				return -1;
			}
		}
		if(o == null || o.getOrder() == null) return 1;
		return this.getOrder().compareTo(o.getOrder());
	}



	public Long getOrder() {
		return order;
	}



	public void setOrder(Long order) {
		this.order = order;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

}
