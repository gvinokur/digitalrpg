package com.digitalrpg.domain.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import com.digitalrpg.domain.model.characters.NonPlayerCharacter;
import com.digitalrpg.domain.model.characters.PlayerCharacter;
import com.digitalrpg.domain.model.messages.InviteToCampaignMessage;
import com.digitalrpg.domain.model.messages.RequestJoinToCampaignMessage;

@Entity
@Table(name = "campaigns")
public class Campaign {

	private Long id;
	
	private String name;
	
	private String description;
	
	private User gameMaster;
	
	private Set<PlayerCharacter> playerCharacters;
	
	private Set<NonPlayerCharacter> nonPlayerCharacters;
	
	private Set<InviteToCampaignMessage> pendingInvitations;
	
	private Set<RequestJoinToCampaignMessage> pendingRequest;
	
	private Boolean isPublic;

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="game_master_id", referencedColumnName="id")
	public User getGameMaster() {
		return gameMaster;
	}

	public void setGameMaster(User gameMaster) {
		this.gameMaster = gameMaster;
	}

	@ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name="campaign_player_characters", 
                joinColumns={@JoinColumn(name="campaign_id")}, 
                inverseJoinColumns={@JoinColumn(name="character_id")})
	public Set<PlayerCharacter> getPlayerCharacters() {
		return playerCharacters;
	}
	
	@ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name="campaign_non_player_characters", 
                joinColumns={@JoinColumn(name="campaign_id")}, 
                inverseJoinColumns={@JoinColumn(name="character_id")})
	public Set<NonPlayerCharacter> getNonPlayerCharacters() {
		return nonPlayerCharacters;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public void setPlayerCharacters(Set<PlayerCharacter> playerCharacters) {
		this.playerCharacters = playerCharacters;
	}

	public void setNonPlayerCharacters(Set<NonPlayerCharacter> nonPlayerCharacters) {
		this.nonPlayerCharacters = nonPlayerCharacters;
	}
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
			mappedBy = "campaign", fetch = FetchType.EAGER)
	public Set<InviteToCampaignMessage> getPendingInvitations() {
		return pendingInvitations;
	}

	public void setPendingInvitations(
			Set<InviteToCampaignMessage> pendingInvitations) {
		this.pendingInvitations = pendingInvitations;
	}

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
			mappedBy = "campaign", fetch = FetchType.EAGER)
	public Set<RequestJoinToCampaignMessage> getPendingRequest() {
		return pendingRequest;
	}

	public void setPendingRequest(Set<RequestJoinToCampaignMessage> pendingRequest) {
		this.pendingRequest = pendingRequest;
	}
	
	
}
