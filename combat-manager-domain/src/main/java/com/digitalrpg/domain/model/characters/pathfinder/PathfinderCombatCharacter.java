package com.digitalrpg.domain.model.characters.pathfinder;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.digitalrpg.domain.model.CombatCharacter;
import com.digitalrpg.domain.model.pathfinder.PathfinderAction;
import com.digitalrpg.domain.model.pathfinder.PathfinderCondition;
import com.digitalrpg.domain.model.pathfinder.PathfinderMagicalEffect;

@Entity
@Table(name = "pathfinder_combat_characters")
public class PathfinderCombatCharacter extends CombatCharacter {

	private Integer currentHitPoints;
	
	private PathfinderAction currentAction;
	
	private Set<PathfinderCondition> conditions;
	
	private Set<PathfinderMagicalEffect> magicalEffects;

	public Integer getCurrentHitPoints() {
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

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "pathfinder_combat_character_conditions", 
			joinColumns = {@JoinColumn(name="character_id")},
			inverseJoinColumns = {@JoinColumn(name="condition_id")})
	public Set<PathfinderCondition> getConditions() {
		return conditions;
	}

	public void setConditions(Set<PathfinderCondition> conditions) {
		this.conditions = conditions;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "pathfinder_combat_character_magical_effects", 
			joinColumns = {@JoinColumn(name="character_id")},
			inverseJoinColumns = {@JoinColumn(name="magical_effect_id")})
	public Set<PathfinderMagicalEffect> getMagicalEffects() {
		return magicalEffects;
	}

	public void setMagicalEffects(Set<PathfinderMagicalEffect> magicalEffects) {
		this.magicalEffects = magicalEffects;
	}
	
	
}
