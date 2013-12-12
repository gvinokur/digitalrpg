package com.digitalrpg.web.controller.model;

import java.util.Set;

import com.digitalrpg.domain.model.pathfinder.PathfinderAction;
import com.digitalrpg.domain.model.pathfinder.PathfinderCondition;
import com.digitalrpg.domain.model.pathfinder.PathfinderMagicalEffect;

public class PathfinderCombatCharacterVO extends CombatCharacterVO{

	private Integer currentHitPoints;
	
	private PathfinderAction currentAction;
	
	private Set<PathfinderCondition> currentConditions;
	
	private Set<PathfinderMagicalEffect> currentMagicalEffects;
	
	private String hitPointStatus;
	
	private String conditionsAndEffectsString;

	public Integer getCurrentHitPoints() {
		return currentHitPoints;
	}

	public void setCurrentHitPoints(Integer currentHitPoints) {
		this.currentHitPoints = currentHitPoints;
	}

	public PathfinderAction getCurrentAction() {
		return currentAction;
	}

	public void setCurrentAction(PathfinderAction currentAction) {
		this.currentAction = currentAction;
	}

	public Set<PathfinderCondition> getCurrentConditions() {
		return currentConditions;
	}

	public void setCurrentConditions(Set<PathfinderCondition> currentConditions) {
		this.currentConditions = currentConditions;
	}

	public Set<PathfinderMagicalEffect> getCurrentMagicalEffects() {
		return currentMagicalEffects;
	}

	public void setCurrentMagicalEffects(
			Set<PathfinderMagicalEffect> currentMagicalEffects) {
		this.currentMagicalEffects = currentMagicalEffects;
	}

	public String getHitPointStatus() {
		return hitPointStatus;
	}

	public void setHitPointStatus(String hitPointStatus) {
		this.hitPointStatus = hitPointStatus;
	}

	public String getConditionsAndEffectsString() {
		return conditionsAndEffectsString;
	}

	public void setConditionsAndEffectsString(String conditionsAndEffectsString) {
		this.conditionsAndEffectsString = conditionsAndEffectsString;
	}
	
}
