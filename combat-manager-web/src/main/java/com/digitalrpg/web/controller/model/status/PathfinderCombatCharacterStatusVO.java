package com.digitalrpg.web.controller.model.status;

public class PathfinderCombatCharacterStatusVO extends CombatCharacterStatusVO {

	private String currentHitPointStatus;
	
	private String currentAction;
	
	private String conditionsAndEffects;

	public String getCurrentHitPointStatus() {
		return currentHitPointStatus;
	}

	public void setCurrentHitPointStatus(String currentHitPointStatus) {
		this.currentHitPointStatus = currentHitPointStatus;
	}

	public String getCurrentAction() {
		return currentAction;
	}

	public void setCurrentAction(String currentAction) {
		this.currentAction = currentAction;
	}

	public String getConditionsAndEffects() {
		return conditionsAndEffects;
	}

	public void setConditionsAndEffects(String conditionAndEffects) {
		this.conditionsAndEffects = conditionAndEffects;
	}
	
	
	
}
