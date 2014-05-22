package com.digitalrpg.web.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.domain.model.characters.Character.CharacterType;
import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.web.controller.model.CreateCampaignVO;
import com.digitalrpg.web.controller.model.CreateCharacterVO;
import com.digitalrpg.web.controller.model.CreatePathfinderCharacterVO;
import com.digitalrpg.web.controller.model.InviteClaimCharacterMessageVO;
import com.digitalrpg.web.controller.model.MessageVO;
import com.digitalrpg.web.service.CampaignService;
import com.digitalrpg.web.service.CharacterService;
import com.digitalrpg.web.service.MessageService;
import com.digitalrpg.web.service.UserWrapper;
import com.google.common.collect.ImmutableList;

@Controller
@RequestMapping("/characters")
public class CharacterController {

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private CharacterService characterService;

    @ModelAttribute("createCharacterVO")
    public CreateCharacterVO getCreateCharacterVO(@RequestParam(required = false) Long campaignId) {
        if (campaignId != null) {
            Campaign campaign = campaignService.get(campaignId);
            switch (campaign.getSystem()) {
                case Pathfinder:
                    return new CreatePathfinderCharacterVO();
            }

        }
        return null;
    }

    @ModelAttribute("characterTypes")
    public CharacterType[] getCharacterTypes() {
        return CharacterType.values();
    }

    @ModelAttribute("characters")
    public Collection<SystemCharacter> getUserCharacters(@AuthenticationPrincipal UserWrapper user) {
        return characterService.getUserCharacters(user.getName());
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showPlayerCharacters() {
        return "characters";
    }

    @RequestMapping(value = "/create")
    public ModelAndView showCreatePlayerCharacterPage(@RequestParam Long campaignId,
            @AuthenticationPrincipal UserWrapper user, RedirectAttributes attributes) {

        Campaign campaign = campaignService.get(campaignId);
        return showCreateOrEditPage(user, attributes, campaign, null);
    }

    private ModelAndView showCreateOrEditPage(UserWrapper user, RedirectAttributes attributes,
            Campaign campaign, SystemCharacter systemCharacter) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("campaign", campaign);
        if (campaign.isMember(user.unwrap()) || campaign.getGameMaster().equals(user.unwrap())) {
            modelMap.put("show_content", "create_character");
            if (systemCharacter != null) {
                modelMap.put("createCharacterVO", makeCreateCharacteVO(systemCharacter));
            }
        } else if (systemCharacter == null) {
            attributes.addFlashAttribute("error_message",
                    "Only the Game Master or Members of the campaign can create Characters");
            return new ModelAndView("redirect:/campaigns/" + campaign.getId() + "/show");
        } else {
            attributes.addFlashAttribute("error_message",
                    "Only the Game Master or Members of the campaign can edit Characters");
            return new ModelAndView("redirect:/characters/" + systemCharacter.getId() + "/show");
        }
        return new ModelAndView("/characters", modelMap);
    }

    private CreateCharacterVO makeCreateCharacteVO(SystemCharacter systemCharacter) {
        switch (systemCharacter.getCampaign().getSystem()) {
            case Pathfinder:
                return new CreatePathfinderCharacterVO(systemCharacter);
        }
        return null;
    }

    @RequestMapping(value = "/{id}/edit")
    public ModelAndView showEditPlayerCharacterPage(@PathVariable Long id,
            @AuthenticationPrincipal UserWrapper user, RedirectAttributes attributes) {
        SystemCharacter systemCharacter = characterService.get(id);
        return showCreateOrEditPage(user, attributes, systemCharacter.getCampaign(),
                systemCharacter);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView createOrEditPlayerCharacter(
            @Valid @ModelAttribute("createCharacterVO") CreateCharacterVO character,
            BindingResult result, @AuthenticationPrincipal UserWrapper user,
            RedirectAttributes attributes) {
        if (result.hasErrors()) {
            Map<String, Object> modelMap = new HashMap<String, Object>();
            modelMap.put("campaign", CampaignService.campaignToVOFunction.apply(campaignService
                    .get(character.getCampaignId())));
            modelMap.put("show_content", "create_character");
            modelMap.put("createCharacter", character);
            return new ModelAndView("characters", modelMap);
        }
        if (character.getId() != null) {

            SystemCharacter systemCharacter =
                    characterService.editPlayerCharacter(character.getId(), character.getName(),
                            character.getPictureUrl(), character.getBio(),
                            character.getIsBioPublic(), character.getWebBio(),
                            character.getIsWebBioPublic(), character.getNotes(),
                            character.getAdditionalResources(), character.getCharacterType(),
                            character.getSystemProperties(), user.unwrap());
            attributes.addFlashAttribute("form_message", "Character " + character.getName()
                    + " edited!");
            return new ModelAndView("redirect:/characters/" + systemCharacter.getId() + "/show");
        } else {
            Campaign campaign = campaignService.get(character.getCampaignId());
            characterService.createPlayerCharacter(campaign, character.getName(),
                    character.getPictureUrl(), character.getBio(), character.getIsBioPublic(),
                    character.getWebBio(), character.getIsWebBioPublic(), character.getNotes(),
                    character.getAdditionalResources(), character.getCharacterType(),
                    character.getSystemProperties(), user.unwrap());
            if (character.getMessageId() != null) {
                messageService.deleteMessage(character.getMessageId());
            }
            attributes.addFlashAttribute("form_message", "Character " + character.getName()
                    + " created!");
            return new ModelAndView("redirect:/campaigns/" + campaign.getId() + "/show");
        }

    }

    @RequestMapping(value = "/{id}/show", method = RequestMethod.GET)
    public ModelAndView showCharacter(@PathVariable Long id, @RequestParam(value = "messageId",
            required = false) Long messageId, Principal principal) {
        User user = (User) ((AbstractAuthenticationToken) principal).getPrincipal();
        Map<String, Object> modelMap = new HashMap<String, Object>();
        SystemCharacter systemCharacter = characterService.get(id);
        modelMap.put("character", systemCharacter);
        modelMap.put("show_content", "view_character");
        if (messageId != null) {
            MessageVO message = messageService.getMessage(messageId);
            if (message instanceof InviteClaimCharacterMessageVO && message.getTo().equals(user)) {
                modelMap.put("message", message);
            }
        }
        return new ModelAndView("/characters", modelMap);
    }


}
