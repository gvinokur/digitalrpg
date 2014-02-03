package com.digitalrpg.domain.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.digitalrpg.domain.model.characters.SystemCharacter;

@Entity
@Table(name = "combat_characters")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class CombatCharacter<ACTION_TYPE extends SystemAction> {

	private Long id;
	
	private Combat combat;
	
	private SystemCharacter character;
	
	private Long initiative;
	
	private Long order;
	
	private Boolean hidden;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="combat_id", referencedColumnName="id")
	public Combat getCombat() {
		return combat;
	}

	public void setCombat(Combat combat) {
		this.combat = combat;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="character_id", referencedColumnName="id")
	public SystemCharacter getCharacter() {
		return character;
	}

	public void setCharacter(SystemCharacter character) {
		this.character = character;
	}

	public Long getInitiative() {
		return initiative;
	}

	public void setInitiative(Long initiative) {
		this.initiative = initiative;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

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

	@Column(name = "CHARACTER_ORDER")
	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}
	
	public abstract void addItem(SystemCombatItem item);

	public abstract void removeItem(SystemCombatItem item);

	/**
	 * The current character has finished playing, if it can be done, automatically mark as played
	 */
	public abstract void played(List<ACTION_TYPE> availableActions);
	
	/**
	 * The current character has become the active player, if it can be done, mark automatically as playing
	 */
	public abstract void startPlaying(List<ACTION_TYPE> availableActions);

	/**
	 * Used when rolling back, if the user was the active payer, mark it as not played.
	 */
	public abstract void notPlayed(List<ACTION_TYPE> availableActions);

	/**
	 * Used when rolling back, if the user had finished playing, restart playing.
	 */
	public abstract void restartPlaying(List<ACTION_TYPE> availableActions) ;

	public abstract void setCurrentAction(ACTION_TYPE action);
	
}
