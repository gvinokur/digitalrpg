package com.digitalrpg.domain.model.characters;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.User;


@Entity
@Table(name = "system_character")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class SystemCharacter {
	
	private Long id;
	
	private Character character;

	private Campaign campaign;
	
	private String type;
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="character_id", referencedColumnName="id")
	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
		this.type = character.getClass().getSimpleName();
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="campaign_id", referencedColumnName="id")
	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean belongsTo(User user) {
		return character.belongsTo(user);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SystemCharacter other = (SystemCharacter) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
