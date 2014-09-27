package com.digitalrpg.web.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.domain.model.characters.pathfinder.PathfinderCharacter;
import com.digitalrpg.domain.model.messages.InviteToClaimCharacterMessage;
import com.digitalrpg.domain.model.messages.Message;
import com.digitalrpg.web.controller.model.CharacterVO;
import com.digitalrpg.web.controller.model.CreateCharacterVO;
import com.digitalrpg.web.controller.model.PathfinderCharacterVO;
import com.digitalrpg.web.service.CampaignService;
import com.digitalrpg.web.service.CharacterService;
import com.digitalrpg.web.service.MessageService;
import com.google.common.base.Function;

// @Controller
// @RequestMapping("/characters")
public class MonsterController {

    @Autowired
    private CharacterService characterService;

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private MessageService messageService;

    public static final Function<SystemCharacter, CharacterVO> characterToVOfunction = new Function<SystemCharacter, CharacterVO>() {

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
                characterVO.setCampaign(CampaignService.campaignToVOFunction.apply(character.getCampaign()));
            }
            return characterVO;
        }

        private CharacterVO createPathfinderCharacterVO(PathfinderCharacter character) {
            PathfinderCharacterVO vo = new PathfinderCharacterVO();
            return vo;
        }
    };

    @ModelAttribute("characters")
    public Collection<SystemCharacter> getUserCharacters(Principal principal) {
        User user = (User) ((AbstractAuthenticationToken) principal).getPrincipal();
        return characterService.getUserMonsters(user.getName());
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showPlayerCharacters() {
        return "monsters";
    }

    @RequestMapping(value = "/create")
    public ModelAndView showCreatePlayerCharacterPage(@RequestParam Long campaignId) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("campaign", CampaignService.campaignToVOFunction.apply(campaignService.get(campaignId)));
        modelMap.put("show_content", "create_character");
        return new ModelAndView("/monsters", modelMap);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView createPlayerCharacter(@Valid @ModelAttribute("createCharacter") CreateCharacterVO character, BindingResult result,
            Principal principal, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            Map<String, Object> modelMap = new HashMap<String, Object>();
            modelMap.put("campaign", CampaignService.campaignToVOFunction.apply(campaignService.get(character.getCampaignId())));
            modelMap.put("show_content", "create_character");
            modelMap.put("createCharacter", character);
            return new ModelAndView("monsters", modelMap);
        }
        User user = (User) ((AbstractAuthenticationToken) principal).getPrincipal();
        Campaign campaign = campaignService.get(character.getCampaignId());
        characterService.createNonPlayerCharacter(campaign, character.getName(), character.getPictureUrl(), character.getBio(), true,
                character.getSystemProperties(), user);
        attributes.addFlashAttribute("form_message", "Character " + character.getName() + " created!");
        return new ModelAndView("redirect:/campaigns/" + campaign.getId() + "/show");
    }

    @RequestMapping(value = "/{id}/show", method = RequestMethod.GET)
    public ModelAndView showCharacter(@PathVariable Long id, Principal principal) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        SystemCharacter systemCharacter = characterService.get(id);
        modelMap.put("character", characterToVOfunction.apply(systemCharacter));
        modelMap.put("show_content", "view_character");
        return new ModelAndView("/monsters", modelMap);
    }

    @RequestMapping(value = "/{id}/invite/{email:.*}")
    @ResponseBody
    public Boolean invite(@PathVariable Long id, @PathVariable String email, Principal principal, HttpServletRequest request) {
        User user = (User) ((AbstractAuthenticationToken) principal).getPrincipal();
        String contextPath =
                "http://" + request.getServerName()
                        + (request.getContextPath() != null && !request.getContextPath().isEmpty() ? "/" + request.getContextPath() : "");

        return characterService.invite(id, user, email, contextPath);
    }

    @RequestMapping(value = "/{id}/claim/{messageId}", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public String claimCharacter(@PathVariable Long id, @PathVariable Long messageId, Principal principal, RedirectAttributes attributes) {
        User user = (User) ((AbstractAuthenticationToken) principal).getPrincipal();
        SystemCharacter character = characterService.get(id);
        Message message = messageService.getMessage(messageId);
        if (message instanceof InviteToClaimCharacterMessage && message.getTo().equals(user)) {
            characterService.transfer(character, user);
            messageService.deleteMessage(messageId);
            attributes.addFlashAttribute("form_message", "You own this character now!");
        } else {
            attributes.addFlashAttribute("form_error",
                    "You cannot claim this character, either this is an invalid message or it was not sent to you.");
        }

        return "redirect:/player-characters/" + id + "/show";
    }

    @RequestMapping(value = "/{id}/take", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public String takeCharacter(@PathVariable Long id, Principal principal, RedirectAttributes attributes) {
        User user = (User) ((AbstractAuthenticationToken) principal).getPrincipal();
        SystemCharacter character = characterService.get(id);
        if (character.getCampaign().getGameMaster().equals(user)) {
            characterService.transfer(character, user);
            attributes.addFlashAttribute("form_message", "You own this character now!");
        } else {
            attributes.addFlashAttribute("form_error", "You cannot take this character, you must be the campaign game master to do that.");
        }

        return "redirect:/player-characters/" + id + "/show";
    }

}
