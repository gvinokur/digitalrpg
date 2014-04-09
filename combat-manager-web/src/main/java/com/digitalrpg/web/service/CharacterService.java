package com.digitalrpg.web.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.digitalrpg.domain.dao.CharacterDao;
import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.SystemType;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.domain.model.characters.Character;
import com.digitalrpg.domain.model.characters.NonPlayerCharacter;
import com.digitalrpg.domain.model.characters.PlayerCharacter;
import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.domain.model.characters.SystemProperties;
import com.digitalrpg.domain.model.characters.pathfinder.PathfinderCharacter;
import com.digitalrpg.domain.model.characters.pathfinder.PathfinderCharacterProperties;
import com.digitalrpg.web.controller.model.CharacterVO;
import com.digitalrpg.web.controller.model.MessageVO;
import com.digitalrpg.web.controller.model.PathfinderCharacterVO;
import com.digitalrpg.web.service.MailService.MailType;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;

public class CharacterService {

	@Autowired 
	private CharacterDao characterDao;
	
	@Autowired 
	private UserService userService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private MailService mailService;
	
	public static final Function<SystemCharacter, CharacterVO> characterToVOfunction = new Function<SystemCharacter, CharacterVO>() {
	
		public CharacterVO apply(SystemCharacter character) {
			CharacterVO characterVO = null;
			if(character instanceof PathfinderCharacter) {
				characterVO = createPathfinderCharacterVO((PathfinderCharacter)character);
			}
			if(characterVO != null) {
				characterVO.setName(character.getCharacter().getName());
				characterVO.setDescription(character.getCharacter().getDescription());
				characterVO.setPictureUrl(character.getCharacter().getPictureUrl());
				characterVO.setId(character.getId());
				characterVO.setCampaign(CampaignService.campaignToVOFunction.apply(character.getCampaign()));
				if(character.getCharacter() instanceof PlayerCharacter) {
					characterVO.setOwner(((PlayerCharacter)character.getCharacter()).getOwner());
				} else {
					characterVO.setOwner(((NonPlayerCharacter)character.getCharacter()).getCreatedBy());
				}
			}
			return characterVO;
		}
	
		private CharacterVO createPathfinderCharacterVO(
				PathfinderCharacter character) {
			PathfinderCharacterVO vo = new PathfinderCharacterVO();
			vo.setRace(character.getRace());
			vo.setCharacterClass(character.getCharacterClass());
			vo.setHp(character.getHp());
			vo.setCharisma(character.getCharisma());
			vo.setConstitution(character.getConstitution());
			vo.setDexterity(character.getDexterity());
			vo.setIntelligence(character.getIntelligence());
			vo.setStrength(character.getStrength());
			vo.setWisdom(character.getWisdom());
			return vo;
		}
	};

	public SystemCharacter get(Long playerId) {
		return characterDao.get(playerId);
	}

	public Boolean invite(Long id, User from, String emailTo, String contextPath) {
		SystemCharacter character = characterDao.get(id);
		if(character !=null && character.getCharacter() instanceof NonPlayerCharacter) {
			User userTo = userService.findByMail(emailTo);
			MessageVO message = messageService.invite(id, from, emailTo, userTo, character);
			sendMail(from, emailTo, contextPath, character, message);
			return true;
		}
		return null;
	}

	private void sendMail(User from, String email, String contextPath,
			SystemCharacter character, MessageVO message) {
		Map<String, Object> model = new HashMap<String, Object>();
        model.put("email", email);
        model.put("username", from.getName());
        try {
			model.put("contextPath", new URI(contextPath));
		} catch (URISyntaxException ignore) { }
        model.put("character", character);
        model.put("message", message);
        
        mailService.sendMail("Join Campaign on DigitalRPG", from, ImmutableList.of(email), model, MailType.CLAIM_CHARACTER);		
	}

	public void transfer(SystemCharacter systemCharacter, User user) {
		Character oldCharacter = systemCharacter.getCharacter();
		Character newCharacter;
		if(systemCharacter.getCampaign().getGameMaster().equals(user)) {
			newCharacter = characterDao.createNonPlayerCharacter(oldCharacter.getName(), oldCharacter.getPictureUrl(), oldCharacter.getDescription(), true, user);
		} else {
			newCharacter = characterDao.createPlayerCharacter(oldCharacter.getName(), oldCharacter.getPictureUrl(), oldCharacter.getDescription(), user);
		}
		systemCharacter.setCharacter(newCharacter);
		characterDao.save(systemCharacter);
		characterDao.delete(oldCharacter);
	}
	
	@Transactional
	public SystemCharacter createPlayerCharacter(Campaign campaign, String name, String pictureUrl, String description, SystemProperties properties, User user ) {
		PlayerCharacter playerCharacter = characterDao.createPlayerCharacter(name, pictureUrl, description, user);
		SystemCharacter character = null;
		if(SystemType.Pathfinder == campaign.getSystem()) {
			character = characterDao.createPathfinderCharacter(playerCharacter, (PathfinderCharacterProperties) properties, campaign);
		}
		return character;
	}

	public void ceatePlaceholderCharacter(Campaign campaign, User user) {
		PlayerCharacter playerCharacter = characterDao.createPlayerCharacter(null, null, null, user);
		SystemCharacter character = null;
		if(SystemType.Pathfinder == campaign.getSystem()) {
			character = characterDao.createPathfinderCharacter(playerCharacter, null, campaign);
		}
	}

	public Collection<SystemCharacter> getUserPlayerCharacters(String name) {
		return characterDao.getUserPlayerCharacters(name);
	}

	public Collection<SystemCharacter> getUserMonsters(String name) {
		return characterDao.getUserMonsters(name);
	}

	public Boolean hasPlayerCharacter(Campaign campaign, User user) {
		return characterDao.hasPlayerCharacter(campaign, user);
	}

	@Transactional
	public SystemCharacter createNonPlayerCharacter(Campaign campaign, String name,
			String pictureUrl, String description, Boolean isPublic,
			SystemProperties systemProperties, User user) {
		NonPlayerCharacter nonPlayerCharacter = characterDao.createNonPlayerCharacter(name, pictureUrl, description, isPublic, user);
		SystemCharacter character = null;
		if(SystemType.Pathfinder == campaign.getSystem()) {
			character = characterDao.createPathfinderCharacter(nonPlayerCharacter, (PathfinderCharacterProperties) systemProperties, campaign);
		}
		return character;
	}
	
}
