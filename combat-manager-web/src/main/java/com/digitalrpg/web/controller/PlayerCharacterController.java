package com.digitalrpg.web.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
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
import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.web.controller.model.CreateCharacterVO;
import com.digitalrpg.web.controller.model.InviteClaimCharacterMessageVO;
import com.digitalrpg.web.controller.model.MessageVO;
import com.digitalrpg.web.service.CampaignService;
import com.digitalrpg.web.service.CharacterService;
import com.digitalrpg.web.service.MessageService;

@Controller
@RequestMapping("/player-characters")
public class PlayerCharacterController {

	@Autowired
	private CampaignService campaignService;
	
	@Autowired 
	private MessageService messageService;
	
	@Autowired
	private CharacterService characterService;
	
	@ModelAttribute("characters")
	public Collection<SystemCharacter> getUserCharacters(Principal principal) {
		User user = (User) ((AbstractAuthenticationToken)principal).getPrincipal();
		return characterService.getUserPlayerCharacters(user.getName());
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String showPlayerCharacters(){
		return "player-characters";
	}
	
	@RequestMapping(value = "/create")
	public ModelAndView showCreatePlayerCharacterPage(@RequestParam Long campaignId, @RequestParam Long messageId, Principal principal, RedirectAttributes attributes) {
		User user = (User) ((AbstractAuthenticationToken)principal).getPrincipal();
		MessageVO message = messageService.getMessage(messageId);
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("campaign", CampaignService.campaignToVOFunction.apply(campaignService.get(campaignId)));
		modelMap.put("message", message);
		if(message !=null && message.getTo() != null && message.getTo().equals(user)) {
			modelMap.put("show_content", "create_character");
		} else {
			attributes.addFlashAttribute("error_message", "You tried to Join to a campaign you were not invited, go ahead and ask for an invitation");
			return new ModelAndView("redirect:/campaigns/" + campaignId + "/show");
		}
		return new ModelAndView("/player-characters", modelMap );
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView createPlayerCharacter(@Valid @ModelAttribute("createCharacter") CreateCharacterVO character, BindingResult result, Principal principal, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			Map<String, Object> modelMap = new HashMap<String, Object>();
			modelMap.put("campaign", CampaignService.campaignToVOFunction.apply(campaignService.get(character.getCampaignId())));
			modelMap.put("show_content", "create_character");
			modelMap.put("createCharacter", character);
			return new ModelAndView("player-characters", modelMap);
		}
		User user = (User) ((AbstractAuthenticationToken)principal).getPrincipal();
		Campaign campaign = campaignService.get(character.getCampaignId());
		characterService.createPlayerCharacter(campaign, character.getName(), character.getPictureUrl(), character.getDescription(), character.getSystemProperties(campaign.getSystem()), user);
		if(character.getMessageId() != null) {
			messageService.deleteMessage(character.getMessageId());
		}
		attributes.addFlashAttribute("form_message", "Player Character " + character.getName() + " created!");
		return new ModelAndView("redirect:player-characters");
	}
	
	@RequestMapping(value = "/{id}/show", method = RequestMethod.GET)
	public ModelAndView showCharacter(@PathVariable Long id, @RequestParam(value="messageId", required=false) Long messageId, Principal principal) {
		User user = (User) ((AbstractAuthenticationToken)principal).getPrincipal();
		Map<String, Object> modelMap = new HashMap<String, Object>();
		SystemCharacter systemCharacter = characterService.get(id);
		modelMap.put("systemCharacter", systemCharacter);
		modelMap.put("show_content", "view_character");
		if(messageId != null) {
			MessageVO message = messageService.getMessage(messageId);
			if(message instanceof InviteClaimCharacterMessageVO && message.getTo().equals(user)) {
				modelMap.put("message", message);
			}
		}
		return new ModelAndView("/player-characters", modelMap );
	}
	

}
