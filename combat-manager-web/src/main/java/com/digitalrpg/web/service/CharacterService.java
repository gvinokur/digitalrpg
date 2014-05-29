package com.digitalrpg.web.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.digitalrpg.domain.dao.CharacterDao;
import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.SystemType;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.domain.model.characters.Character;
import com.digitalrpg.domain.model.characters.Character.CharacterType;
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

    public static final Function<SystemCharacter, CharacterVO> characterToVOfunction =
            new Function<SystemCharacter, CharacterVO>() {

                public CharacterVO apply(SystemCharacter character) {
                    CharacterVO characterVO = null;
                    if (character instanceof PathfinderCharacter) {
                        characterVO = createPathfinderCharacterVO((PathfinderCharacter) character);
                    }
                    if (characterVO != null) {
                        characterVO.setName(character.getCharacter().getName());
                        characterVO.setDescription(character.getCharacter().getBio());
                        characterVO.setPictureUrl(character.getCharacter().getPictureUrl());
                        characterVO.setId(character.getId());
                        characterVO.setCampaign(CampaignService.campaignToVOFunction
                                .apply(character.getCampaign()));
                        characterVO.setOwner(character.getCharacter().getOwner());
                    }
                    return characterVO;
                }

                private CharacterVO createPathfinderCharacterVO(PathfinderCharacter character) {
                    PathfinderCharacterVO vo = new PathfinderCharacterVO();
                    return vo;
                }
            };

    public SystemCharacter get(Long playerId) {
        return characterDao.get(playerId);
    }

    public Boolean invite(Long id, User from, String emailTo, String contextPath) {
        SystemCharacter character = characterDao.get(id);
        // Only the owner or the campaign game master can transfer characters
        if (!character.belongsTo(from) || !character.getCampaign().getGameMaster().equals(from)) {
            return false;
        }
        if (character != null) {
            User userTo = userService.findByMail(emailTo);
            MessageVO message = messageService.invite(id, from, emailTo, userTo, character);
            sendMail(from, emailTo, contextPath, character, message);
            return true;
        }
        return null;
    }

    private void sendMail(User from, String email, String contextPath, SystemCharacter character,
            MessageVO message) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("email", email);
        model.put("username", from.getName());
        try {
            model.put("contextPath", new URI(contextPath));
        } catch (URISyntaxException ignore) {
        }
        model.put("character", character);
        model.put("message", message);

        mailService.sendMail("Join Campaign on DigitalRPG", from, ImmutableList.of(email), model,
                MailType.CLAIM_CHARACTER);
    }

    public void transfer(SystemCharacter systemCharacter, User user) {
        characterDao.transfer(systemCharacter.getId(), user);
    }

    @Transactional
    public SystemCharacter createPlayerCharacter(Campaign campaign, String name, String pictureUrl,
            String bio, Boolean isBioPublic, String webBioUrl, Boolean isWebBioPublic,
            String notes, List<String> links, CharacterType characterType,
            SystemProperties properties, User user) {
        Character playerCharacter = characterDao.createPlayerCharacter(name, pictureUrl, bio, user);
        playerCharacter.setCharacterType(characterType);
        playerCharacter.setLinks(links);
        playerCharacter.setNotes(notes);
        playerCharacter.setPublicBio(isBioPublic);
        playerCharacter.setPublicWebBio(isWebBioPublic);
        playerCharacter.setWebBioUrl(webBioUrl);
        SystemCharacter character = null;
        if (SystemType.Pathfinder == campaign.getSystem()) {
            character =
                    characterDao.createPathfinderCharacter(playerCharacter,
                            (PathfinderCharacterProperties) properties, campaign);
        }
        return character;
    }

    @Transactional
    public SystemCharacter editPlayerCharacter(Long id, String name, String pictureUrl, String bio,
            Boolean isBioPublic, String webBioUrl, Boolean isWebBioPublic, String notes,
            List<String> links, CharacterType characterType, SystemProperties systemProperties,
            User unwrap) {
        SystemCharacter systemCharacter = characterDao.get(id);
        systemCharacter.getCharacter().setName(name);
        systemCharacter.getCharacter().setPictureUrl(pictureUrl);
        systemCharacter.getCharacter().setBio(bio);
        systemCharacter.getCharacter().setLinks(links);
        systemCharacter.getCharacter().setNotes(notes);
        systemCharacter.getCharacter().setPublicBio(isBioPublic);
        systemCharacter.getCharacter().setPublicWebBio(isWebBioPublic);
        systemCharacter.getCharacter().setWebBioUrl(webBioUrl);
        systemCharacter.getCharacter().setCharacterType(characterType);
        systemCharacter.updateProperties(systemProperties);
        return systemCharacter;
    }

    public Collection<SystemCharacter> getUserCharacters(String name) {
        return characterDao.getUserCharacters(name);
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
        Character nonPlayerCharacter =
                characterDao
                        .createNonPlayerCharacter(name, pictureUrl, description, isPublic, user);
        SystemCharacter character = null;
        if (SystemType.Pathfinder == campaign.getSystem()) {
            character =
                    characterDao.createPathfinderCharacter(nonPlayerCharacter,
                            (PathfinderCharacterProperties) systemProperties, campaign);
        }
        return character;
    }

    @Transactional
    public void delete(Long id) {
        characterDao.deleteSystemCharacter(id);
    }



}
