package com.digitalrpg.domain.model.characters;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.digitalrpg.domain.model.User;

@Entity
@Table(name = "non_player_characters")
public class NonPlayerCharacter extends Character{

	private User createdBy;
	
	private Boolean isPublic;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="created_by_user_id", referencedColumnName="id")
	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User user) {
		this.createdBy = user;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}
	
}
