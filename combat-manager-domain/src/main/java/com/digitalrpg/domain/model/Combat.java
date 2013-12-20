package com.digitalrpg.domain.model;

import java.util.Set;
import java.util.SortedSet;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.annotations.Type;

import com.google.common.collect.ImmutableSortedSet;

@Entity
@Table(name = "combats")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Combat {

	private Long id;

	private Boolean active;

	private String name;

	private String description;

	private Campaign campaign;

	private Set<CombatCharacter> combatCharacters;

	private CombatCharacter currentCharacter;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "current_character_id", referencedColumnName = "id")
	public CombatCharacter getCurrentCharacter() {
		return currentCharacter;
	}

	public void setCurrentCharacter(CombatCharacter currentCharacter) {
		this.currentCharacter = currentCharacter;
	}

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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "campaign_id", referencedColumnName = "id")
	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "combat")
	public Set<CombatCharacter> getCombatCharacters() {
		return ImmutableSortedSet.copyOf(new CombatCharacterComparator(), this.combatCharacters);
	}

	public void setCombatCharacters(Set<CombatCharacter> combatCharacters) {
		this.combatCharacters = combatCharacters;
	}

	@Id
	@Type(type = "long")
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getActive() {
		return BooleanUtils.isTrue(active);
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * Returns true when no movement has been made, this means the combat has reached the end. 
	 * @return
	 */
	public abstract boolean advance();

	/**
	 * Returns true when no movement has been made, this means the combat is back at the beginning.
	 * @return
	 */
	public abstract boolean back();

}
