package com.digitalrpg.domain.model;

import java.util.List;

import javax.persistence.CascadeType;
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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "combats")
@Inheritance(strategy = InheritanceType.JOINED)
public class Combat {

	private Long id;
	
	private String name;
	
	private String description;
	
	private Campaign campaign;
	
	private List<CombatCharacter> combatCharacters;

	private CombatCharacter currentCharacter;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="current_character_id", referencedColumnName="id")	
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
	@JoinColumn(name="campaign_id", referencedColumnName="id")
	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy="combat", orphanRemoval = true)
	public List<CombatCharacter> getCombatCharacters() {
		return combatCharacters;
	}

	public void setCombatCharacters(List<CombatCharacter> combatCharacters) {
		this.combatCharacters = combatCharacters;
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
	
	
	
}
