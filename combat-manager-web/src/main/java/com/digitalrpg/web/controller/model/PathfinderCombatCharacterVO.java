package com.digitalrpg.web.controller.model;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.digitalrpg.domain.model.pathfinder.PathfinderAction;
import com.digitalrpg.domain.model.pathfinder.PathfinderCondition;
import com.digitalrpg.domain.model.pathfinder.PathfinderMagicalEffect;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;

public class PathfinderCombatCharacterVO extends CombatCharacterVO{

	private Integer currentHitPoints;
	
	private PathfinderAction currentAction;
	
	private Set<PathfinderCondition> currentConditions;
	
	private Set<PathfinderMagicalEffect> currentMagicalEffects;

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
	
	public String getHitPointsStatus() {
		PathfinderCharacterVO pathfinderCharacter = (PathfinderCharacterVO)getCharacterVO();
		int totalHp = pathfinderCharacter.getHp();
		int percent = this.currentHitPoints * 100 / totalHp;
		if(percent >= 100) return "green";
		else if (percent >= 90) return "white";
		else if (percent >= 50) return "yellow";
		else if (percent >= 0) return "red";
		else return "black";
	}
	
	public String getConditionsAndEffectsString() {
		Set<String> conditionsAndEffects = new HashSet<String>();
		conditionsAndEffects.addAll(Collections2.transform(currentConditions, new Function<PathfinderCondition, String>() {
			public String apply(PathfinderCondition arg0) {
				return arg0.getLabel();
			}
		}));
		conditionsAndEffects.addAll(Collections2.transform(currentMagicalEffects, new Function<PathfinderMagicalEffect, String>() {
			public String apply(PathfinderMagicalEffect arg0) {
				return arg0.getLabel();
			}
		}));
		return StringUtils.join(conditionsAndEffects, ", ");
	}
	
}
