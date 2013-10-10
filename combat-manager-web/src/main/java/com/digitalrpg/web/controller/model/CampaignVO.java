package com.digitalrpg.web.controller.model;

import java.util.Collection;
import java.util.Set;

import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.domain.model.characters.PlayerCharacter;
import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.domain.model.messages.InviteToCampaignMessage;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;

public class CampaignVO {

	private Long id;
	
	private String name;
	
	private String description;
	
	private User gameMaster;
	
	private Set<SystemCharacter> playerCharacters;
	
	private Collection<InviteMessageVO>  pendingInvitations;
	
	private Collection<RequestJoinMessageVO> pendingRequests;
	
	public void fromCampaign(Campaign campaign) {
		this.id = campaign.getId();
		this.name = campaign.getName();
		this.description = campaign.getDescription();
		this.gameMaster = campaign.getGameMaster();
		this.playerCharacters = campaign.getPlayerCharacters();
		this.pendingInvitations = Collections2.transform(campaign.getPendingInvitations(), new Function<InviteToCampaignMessage, InviteMessageVO>() {
			public InviteMessageVO apply(InviteToCampaignMessage in) {
				InviteMessageVO out = new InviteMessageVO();
				out.setMailTo(in.getToMail());
				out.setTo(in.getTo());
				out.setFrom(in.getFrom());
				out.setId(in.getId());
				return out;
			}
		});
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public User getGameMaster() {
		return gameMaster;
	}

	public Set<SystemCharacter> getPlayerCharacters() {
		return playerCharacters;
	}

	public Collection<InviteMessageVO> getPendingInvitations() {
		return pendingInvitations;
	}

	public Collection<RequestJoinMessageVO> getPendingRequests() {
		return pendingRequests;
	}

	public Long getId() {
		return id;
	}
	
	
}
