package com.digitalrpg.domain.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.domain.model.messages.InviteToCampaignMessage;
import com.digitalrpg.domain.model.messages.RequestJoinToCampaignMessage;

@Entity
@Table(name = "campaigns")
public class Campaign {

	private Long id;
	
	private String name;
	
	private String description;
	
	private User gameMaster;
	
	private SystemType system;
	
	private Set<SystemCharacter> characters;
	
	private Set<InviteToCampaignMessage> pendingInvitations;
	
	private Set<RequestJoinToCampaignMessage> pendingRequest;
	
	private Set<Combat> combats;
	
	private Combat activeCombat;
	
	private Boolean isPublic;
	
	private Set<User> members;

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

	@OneToMany(mappedBy = "campaign",fetch = FetchType.EAGER)
	public Set<SystemCharacter> getCharacters() {
		return characters;
	}
	
	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public void setCharacters(Set<SystemCharacter> playerCharacters) {
		this.characters = playerCharacters;
	}

	@OneToMany(cascade = CascadeType.PERSIST,
			mappedBy = "campaign", fetch = FetchType.EAGER)
	public Set<InviteToCampaignMessage> getPendingInvitations() {
		return pendingInvitations;
	}

	public void setPendingInvitations(
			Set<InviteToCampaignMessage> pendingInvitations) {
		this.pendingInvitations = pendingInvitations;
	}

	@OneToMany(cascade = CascadeType.PERSIST,
			mappedBy = "campaign", fetch = FetchType.EAGER)
	public Set<RequestJoinToCampaignMessage> getPendingRequest() {
		return pendingRequest;
	}

	public void setPendingRequest(Set<RequestJoinToCampaignMessage> pendingRequest) {
		this.pendingRequest = pendingRequest;
	}

	public SystemType getSystem() {
		return system;
	}

	public void setSystem(SystemType system) {
		this.system = system;
	}

	@OneToMany(mappedBy = "campaign", fetch = FetchType.EAGER)
	public Set<Combat> getCombats() {
		return combats;
	}

	public void setCombats(Set<Combat> combats) {
		this.combats = combats;
		
	}

	@Transient
	public Combat getActiveCombat() {
		if(activeCombat == null && combats != null) {
			for (Combat combat : combats) {
				if(combat.getActive()) {
					setActiveCombat(combat);
				}
			}
		}
		return activeCombat;
	}
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = User.class)
	@JoinTable(name = "campaign_users",  
			joinColumns = { @JoinColumn(name = "campaign_id", referencedColumnName = "id", nullable = false)},
			inverseJoinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false) })
	public Set<User> getMembers() {
//		Set<User> members = new HashSet<User>();
//		for(SystemCharacter character : this.getPlayerCharacters()) {
//			User owner = ((PlayerCharacter)character.getCharacter()).getOwner();
//			members.add(owner);
//		}
		return members;
	}
	
	public void setMembers(Set<User> members) {
		this.members = members;
	}
	
	public void addMember(User member) {
		if(this.members == null) {
			this.members = new HashSet<>();
		}
		this.members.add(member);
	}

	public void setActiveCombat(Combat activeCombat) {
		this.activeCombat = activeCombat;
	}

	@Transient
	public List<SystemCharacter> getUserCharacters(User user) {
		List<SystemCharacter> userCharacters = new LinkedList<>();
		for(SystemCharacter character : this.getCharacters()) {
			User owner = character.getCharacter().getOwner();
			if(owner.equals(user)) {
				userCharacters.add(character);
			}
		}
		return userCharacters;
	}

	public boolean isMember(User user) {
		return members.contains(user);
	}


	
	
}
