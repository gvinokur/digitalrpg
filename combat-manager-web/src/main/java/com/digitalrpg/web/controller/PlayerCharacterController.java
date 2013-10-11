package com.digitalrpg.web.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.digitalrpg.domain.dao.CharacterDao;
import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.SystemType;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.domain.model.characters.PlayerCharacter;
import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.web.controller.model.CharacterVO;
import com.digitalrpg.web.controller.model.MessageVO;
import com.digitalrpg.web.service.CampaignService;
import com.digitalrpg.web.service.MessageService;

@Controller
@RequestMapping("/player-characters")
public class PlayerCharacterController {

	@Autowired
	private CharacterDao characterDao;
	
	@Autowired
	private CampaignService campaignService;
	
	@Autowired 
	private MessageService messageService;
	
	@ModelAttribute("characters")
	public Collection<SystemCharacter> getUserCharacters(Principal principal) {
		User user = (User) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		return characterDao.getUserPlayerCharacters(user.getName());
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String showPlayerCharacters(){
		return "player-characters";
	}
	
	@RequestMapping(value = "/create")
	public ModelAndView showCreatePlayerCharacterPage(@RequestParam Long campaignId, @RequestParam Long messageId, Principal principal, RedirectAttributes attributes) {
		User user = (User) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		MessageVO message = messageService.getMessage(messageId);
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("campaign", CampaignController.campaignToVOFunction.apply(campaignService.get(campaignId)));
		modelMap.put("message", message);
		if(message !=null && message.getTo().equals(user)) {
			modelMap.put("show_content", "create_character");
		} else {
			attributes.addFlashAttribute("error_message", "You tried to Join to a campaign you were not invited, go ahead and ask for an invitation");
			return new ModelAndView("redirect:/campaigns/" + campaignId + "/show");
		}
		return new ModelAndView("/player-characters", modelMap );
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView createPlayerCharacter(@Valid @ModelAttribute("character") CharacterVO character, BindingResult result, Principal principal, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return new ModelAndView("player-characters", "show_content", "create_character");
		}
		User user = (User) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		Campaign campaign = campaignService.get(character.getCampaignId());
		PlayerCharacter playerCharacter = characterDao.createPlayerCharacter(character.getName(), character.getPictureUrl(), 
				character.getDescription(), character.getCharacterClass(), character.getRace(), character.getHp(), user);
		if(SystemType.Pathfinder == campaign.getSystem()) {
			characterDao.createPathfinderCharacter(playerCharacter, character.getPathfinder().toPathfinderProperties(), campaign);
		}
		if(character.getMessageId() != null) {
			messageService.deleteMessage(character.getMessageId());
		}
		attributes.addFlashAttribute("form_message", "Player Character " + character.getName() + " created!");
		return new ModelAndView("redirect:player-characters");
	}
	

}
