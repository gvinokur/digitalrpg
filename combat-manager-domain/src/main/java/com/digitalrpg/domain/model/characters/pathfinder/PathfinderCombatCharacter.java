package com.digitalrpg.domain.model.characters.pathfinder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import com.digitalrpg.domain.model.CombatCharacter;
import com.digitalrpg.domain.model.SystemAction;
import com.digitalrpg.domain.model.SystemCombatItem;
import com.digitalrpg.domain.model.pathfinder.PathfinderAction;
import com.digitalrpg.domain.model.pathfinder.PathfinderCondition;
import com.digitalrpg.domain.model.pathfinder.PathfinderMagicalEffect;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;

@Entity
@Table(name = "pathfinder_combat_characters")
public class PathfinderCombatCharacter extends CombatCharacter<PathfinderAction> {

	private Integer currentHitPoints;
	
	private PathfinderAction currentAction;
	
	private Set<PathfinderCondition> conditions;
	
	private Set<PathfinderMagicalEffect> magicalEffects;
	
	public Integer getCurrentHitPoints() {
		if(currentHitPoints == null) {
			PathfinderCharacter pathfinderCharacter = (PathfinderCharacter) getCharacter();
			this.currentHitPoints = pathfinderCharacter.getHp();
		}
		return currentHitPoints;
	}

	public void setCurrentHitPoints(Integer currentHitPoints) {
		this.currentHitPoints = currentHitPoints;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="action_id", referencedColumnName="id")
	public PathfinderAction getCurrentAction() {
		return currentAction;
	}

	public void setCurrentAction(PathfinderAction currentAction) {
		this.currentAction = currentAction;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "pathfinder_combat_character_conditions", 
			joinColumns = {@JoinColumn(name="character_id")},
			inverseJoinColumns = {@JoinColumn(name="condition_id")})
	public Set<PathfinderCondition> getConditions() {
		return conditions;
	}

	public void setConditions(Set<PathfinderCondition> conditions) {
		this.conditions = conditions;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "pathfinder_combat_character_magical_effects", 
			joinColumns = {@JoinColumn(name="character_id")},
			inverseJoinColumns = {@JoinColumn(name="magical_effect_id")})
	public Set<PathfinderMagicalEffect> getMagicalEffects() {
		return magicalEffects;
	}

	public void setMagicalEffects(Set<PathfinderMagicalEffect> magicalEffects) {
		this.magicalEffects = magicalEffects;
	}
	
	@Transient
	public String getHitPointsStatus() {
		PathfinderCharacter pathfinderCharacter = (PathfinderCharacter)getCharacter();
		int totalHp = pathfinderCharacter.getHp();
		int percent = this.getCurrentHitPoints() * 100 / totalHp;
		if(percent >= 100) return "green";
		else if (percent >= 90) return "white";
		else if (percent >= 50) return "yellow";
		else if (percent >= 0) return "red";
		else return "black";
	}
	
	@Transient
	public String getConditionsAndEffectsString() {
		Set<String> conditionsAndEffects = new HashSet<String>();
		conditionsAndEffects.addAll(Collections2.transform(getConditions(), new Function<PathfinderCondition, String>() {
			public String apply(PathfinderCondition arg0) {
				return arg0.getLabel();
			}
		}));
		conditionsAndEffects.addAll(Collections2.transform(getMagicalEffects(), new Function<PathfinderMagicalEffect, String>() {
			public String apply(PathfinderMagicalEffect arg0) {
				return arg0.getLabel();
			}
		}));
		return StringUtils.join(conditionsAndEffects, ", ");
	}

	@Override
	public void addItem(SystemCombatItem item) {
		if(item instanceof PathfinderCondition) {
			this.conditions.add((PathfinderCondition)item);
		} else if(item instanceof PathfinderMagicalEffect) {
			this.magicalEffects.add((PathfinderMagicalEffect) item);
		}
	}

	@Override
	public void removeItem(SystemCombatItem item) {
		if(item instanceof PathfinderCondition) {
			this.conditions.remove(item);
		} else if(item instanceof PathfinderMagicalEffect) {
			this.magicalEffects.remove(item);
		}
	}

	@Override
	public void played(List<PathfinderAction> availableActions) {
		if(BooleanUtils.isTrue(currentAction.getCurrent())) {
			for(SystemAction action : availableActions) {
				if(BooleanUtils.isTrue(action.getFinished())) {
					currentAction = (PathfinderAction) action;
				}
			}
		}
	}

	@Override
	public void startPlaying(List<PathfinderAction> availableActions) {
		if(BooleanUtils.isTrue(currentAction.getInitial())) {
			for(SystemAction action : availableActions) {
				if(BooleanUtils.isTrue(action.getCurrent())) {
					currentAction = (PathfinderAction) action;
				}
			}
		}
	}

	@Override
	public void notPlayed(List<PathfinderAction> availableActions) {
		if(BooleanUtils.isTrue(currentAction.getCurrent())) {
			for(SystemAction action : availableActions) {
				if(BooleanUtils.isTrue(action.getInitial())) {
					currentAction = (PathfinderAction) action;
				}
			}
		}
	}

	@Override
	public void restartPlaying(List<PathfinderAction> availableActions) {
		if(BooleanUtils.isTrue(currentAction.getFinished())) {
			for(SystemAction action : availableActions) {
				if(BooleanUtils.isTrue(action.getCurrent())) {
					currentAction = (PathfinderAction) action;
				}
			}
		}
	}
	
}
