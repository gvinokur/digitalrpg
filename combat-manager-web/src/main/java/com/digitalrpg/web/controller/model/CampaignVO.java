package com.digitalrpg.web.controller.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.SystemType;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.domain.model.characters.PlayerCharacter;
import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.domain.model.messages.InviteToCampaignMessage;
import com.digitalrpg.web.service.CharacterService;
import com.digitalrpg.web.service.MessageService;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;

public class CampaignVO {

	private Long id;
	
	private String name;
	
	private String description;
	
	private User gameMaster;
	
	private Collection<CreateCharacterVO> playerCharacters;
	
	private Collection<MessageVO>  pendingInvitations;
	
	private Collection<MessageVO> pendingRequests;
	
	private SystemType system;
	
	public void fromCampaign(Campaign campaign) {
		this.id = campaign.getId();
		this.name = campaign.getName();
		this.description = campaign.getDescription();
		this.gameMaster = campaign.getGameMaster();
		this.playerCharacters = Collections2.transform(campaign.getPlayerCharacters(), CharacterService.systemCharacterToVOFunction);
		this.pendingInvitations = Collections2.transform(campaign.getPendingInvitations(), MessageService.messageToVOFunction);
		this.pendingRequests = Collections2.transform(campaign.getPendingRequest(), MessageService.messageToVOFunction);
		this.system = campaign.getSystem();
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

	public Collection<CreateCharacterVO> getPlayerCharacters() {
		return playerCharacters;
	}

	public Collection<MessageVO> getPendingInvitations() {
		return pendingInvitations;
	}

	public Collection<MessageVO> getPendingRequests() {
		return pendingRequests;
	}

	public Long getId() {
		return id;
	}

	public SystemType getSystem() {
		return system;
	}
	
	
}
