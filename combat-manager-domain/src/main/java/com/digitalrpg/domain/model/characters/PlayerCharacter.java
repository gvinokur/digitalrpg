package com.digitalrpg.domain.model.characters;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.digitalrpg.domain.model.User;

@Entity
@Table(name = "player_characters")
public class PlayerCharacter extends Character{

	private User owner;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="owner_id", referencedColumnName="id")
	public User getOwner() {
		return owner;
	}

	public void setOwner(User user) {
		this.owner = user;
	}

	@Override
	public boolean belongsTo(User user) {
	
		return owner.equals(user);
	}
	
}
