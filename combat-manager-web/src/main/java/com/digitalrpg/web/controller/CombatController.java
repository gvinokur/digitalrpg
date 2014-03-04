package com.digitalrpg.web.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.Combat;
import com.digitalrpg.domain.model.CombatCharacter;
import com.digitalrpg.domain.model.SystemAction;
import com.digitalrpg.domain.model.SystemCombatItems;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.web.controller.model.CombatCharacterVO;
import com.digitalrpg.web.controller.model.CreateCombatVO;
import com.digitalrpg.web.controller.model.OrderAndAction;
import com.digitalrpg.web.controller.model.status.CombatStatusVO;
import com.digitalrpg.web.service.CampaignService;
import com.digitalrpg.web.service.CombatService;
import com.digitalrpg.web.service.combat.ItemAction;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("/combats")
public class CombatController {

	@Autowired
	private CampaignService campaignService;
	
	@Autowired 
	private CombatService combatService;
	
	@ModelAttribute("combats")
	public List<Combat> getCombats(Principal principal) {
		User user = (User) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		return combatService.getCombats(user);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String showCombats() {
		return "/combat-admin";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView showCreateCombatPage(@RequestParam Long campaignId) {
		Campaign campaign = campaignService.get(campaignId);
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("campaign", campaign);
		modelMap.put("show_content", "combat_create");
		return new ModelAndView("/combat-admin", modelMap );
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView createCombat(@Valid @ModelAttribute("create-combat") CreateCombatVO createCombatVO,
			BindingResult result, RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			Campaign campaign = campaignService.get(createCombatVO.getCampaignId());
			Map<String, Object> modelMap = new HashMap<String, Object>();
			modelMap.put("campaign", campaign);
			modelMap.put("show_content", "combat_create");
			return new ModelAndView("/combat-admin", modelMap );
		}
		combatService.createCombat(createCombatVO.getName(), createCombatVO.getDescription(), 
				createCombatVO.getPlayers(), createCombatVO.getExtraInfo(), createCombatVO.getCampaignId(), createCombatVO.getSystemCombatProperties());
		redirectAttributes.addFlashAttribute("form_message", "Combat created");
		return new ModelAndView("redirect:/campaigns/"+createCombatVO.getCampaignId() + "/show");
	}
	
	@RequestMapping(value = "/{id}/show", method = RequestMethod.GET)
	public ModelAndView showCombat(@PathVariable Long id) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Combat combat = combatService.getCombat(id);
		modelMap.put("combat", combat);
		modelMap.put("show_content", "combat_view");
		return new ModelAndView("/combat-admin", modelMap);
	}
	

	@RequestMapping(value = "/{id}/start", method = RequestMethod.GET)
	public ModelAndView startCombat(@PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes) {
		User user = (User) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Combat combat = combatService.getCombat(id);
		if(combat.getCampaign().getActiveCombat() != null) {
			redirectAttributes.addFlashAttribute("error_message", "Cannot start combat, a combat is already active");
			return new ModelAndView("redirect:/campaigns/" + combat.getCampaign().getId() + "/show");
		}
		if(!user.equals(combat.getCampaign().getGameMaster())) {
			redirectAttributes.addFlashAttribute("error_message", "Cannot start combat, only the GM of the Campaign can do that");
			return new ModelAndView("redirect:/campaigns/" + combat.getCampaign().getId() + "/show");
		}
		combatService.startCombat(combat);
		SystemCombatItems items = combatService.getSystemCombatItems(combat.getCampaign().getSystem());
		modelMap.put("combat", combat);
		modelMap.put("items", items);	
		return new ModelAndView("/combat-gm", modelMap);
	}
	
	@RequestMapping(value = "/{id}/console/show", method = RequestMethod.GET)
	public ModelAndView showCombatConsole(@PathVariable Long id, Principal principal) {
		User user = (User) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Combat combat = combatService.getCombat(id);
		SystemCombatItems items = combatService.getSystemCombatItems(combat.getCampaign().getSystem());
		modelMap.put("combat", combat);
		modelMap.put("items", items);
		String viewName = null;
		if(combat.getCampaign().getGameMaster().equals(user)) {
			viewName = "/combat-gm";
		} else {
			CombatCharacter<SystemAction> playerCharacter = combatService.getPlayerCharacter(combat, user);
			modelMap.put("playerCharacter", playerCharacter);
			viewName = "/combat-player";
		}
		return new ModelAndView(viewName, modelMap);
	}
	
	@RequestMapping(value = "/{id}/end", method = RequestMethod.GET)
	public ModelAndView endCombat(@PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes) {
		User user = (User) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Combat combat = combatService.getCombat(id);
		String viewName = null;
		if(combat.getCampaign().getGameMaster().equals(user)) {
			combatService.endCombat(combat);
			redirectAttributes.addFlashAttribute("form_message", "Combat " + combat.getName() +" finished");
			viewName = "redirect:/campaigns/" + combat.getCampaign().getId() + "/show";
		} else {
			redirectAttributes.addFlashAttribute("error_message", "Cannot end combat, you must be the GM to end it");
			viewName = "redirect:/combats";
		}
		return new ModelAndView(viewName, modelMap);
	}
	
	@RequestMapping(value = "/character/{attributeName}", method = RequestMethod.POST)
	public ResponseEntity<?> updateCombatCharacterAttribute(@PathVariable String attributeName, @RequestParam("pk") Long id, @RequestParam("value") String value, Principal principal) {
		User user = (User) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		try {
			CombatCharacterVO vo = combatService.updateCombatCharacter(id, attributeName, value, user);
			return new ResponseEntity<CombatCharacterVO>(vo, HttpStatus.OK);	
		} catch (Exception e) {
			return new ResponseEntity<String>("Cannot update data", HttpStatus.BAD_REQUEST);
		} 
	}
	
	@RequestMapping(value ="/character/{itemType}/{action}", method = RequestMethod.POST) 
	public ResponseEntity<?> updateCombatCharacterItem(@PathVariable String itemType, @PathVariable ItemAction action,
			@RequestParam("pk") Long id, @RequestParam("itemId") Long itemId, Principal principal){
		User user = (User) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		try {
			CombatCharacterVO vo = combatService.updateCombatCharacterItem(id, itemType, action, itemId, user);
			return new ResponseEntity<CombatCharacterVO>(vo, HttpStatus.OK);	
		} catch (Exception e) {
			return new ResponseEntity<String>("Cannot update data", HttpStatus.BAD_REQUEST);
		} 
	}
	
	
	@RequestMapping(value = "{id}/character/next", method = RequestMethod.POST)
	public ResponseEntity<?> advanceCharacter(@PathVariable Long id, Principal principal) {
		User user = (User) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		Combat combat = combatService.getCombat(id);
		if(combat.getCampaign().getGameMaster().equals(user)) {
			combat = combatService.nextCharacter(id);
			return new ResponseEntity(combatService.getStatus(combat), HttpStatus.OK);
		} else {
			return new ResponseEntity("Only the game master can update the current user", HttpStatus.FORBIDDEN);
		}
		
	}
	
	@RequestMapping(value = "{id}/character/previous", method = RequestMethod.POST)
	public ResponseEntity<?> previousCharacter(@PathVariable Long id, Principal principal) {
		User user = (User) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		Combat combat = combatService.getCombat(id);
		if(combat.getCampaign().getGameMaster().equals(user)) {
			combat = combatService.previousCharacter(id);
			return new ResponseEntity(combatService.getStatus(combat), HttpStatus.OK);
		} else {
			return new ResponseEntity("Only the game master can update the current user", HttpStatus.FORBIDDEN);
		}
		
	}
	
	@RequestMapping(value = "{id}/character/{characterId}/add", method = RequestMethod.POST)
	public ResponseEntity<?> addCharacter(@PathVariable Long id, @PathVariable Long characterId, Principal principal) {
		User user = (User) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		Combat combat = combatService.getCombat(id);
		if(combat.getCampaign().getGameMaster().equals(user)) {
			combat = combatService.addCombatant(combat, characterId);
			return new ResponseEntity(combatService.getStatus(combat, true), HttpStatus.OK);
		} else {
			return new ResponseEntity("Only the game master can update the current user", HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping(value = "{id}/character/{characterId}/delete", method = RequestMethod.POST)
	public ResponseEntity<?> deleteCharacter(@PathVariable Long id, @PathVariable Long characterId, Principal principal) {
		User user = (User) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		Combat combat = combatService.getCombat(id);
		if(combat.getCampaign().getGameMaster().equals(user)) {
			combatService.deleteCombatant(combat, characterId);
			//Reload changes
			combat = combatService.getCombat(combat.getId());
			return new ResponseEntity(combatService.getStatus(combat, true), HttpStatus.OK);
		} else {
			return new ResponseEntity("Only the game master can update the current user", HttpStatus.FORBIDDEN);
		}
		
	}
	
	@RequestMapping(value = "{id}/character/orderAndAction", method = RequestMethod.POST)
	public ResponseEntity<?> updateOrderAndActions(@PathVariable Long id, @RequestBody Map<Long, OrderAndAction> charactersOrderAndActions, Principal principal) {
		User user = (User) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		Combat combat = combatService.getCombat(id);
		if(combat.getCampaign().getGameMaster().equals(user)) {
			combat = combatService.updateOrderAndActions(id, charactersOrderAndActions);
			return new ResponseEntity(combatService.getStatus(combat), HttpStatus.OK);
		} else {
			return new ResponseEntity("Only the game master can update the current user", HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping(value = "/characters/{id}", method = RequestMethod.GET)
	@ResponseBody
	public CombatCharacterVO getCombatCharacterData(@PathVariable Long id) {
		CombatCharacter combatCharacter = combatService.getCombatCharacter(id);
		return CombatService.combatCharacterToVOfunction.apply(combatCharacter);
	}
	
	@RequestMapping(value = "/{id}/status", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<CombatStatusVO> getCurrrentStatus(@PathVariable Long id) {
		Combat combat = combatService.getCombat(id);
		CombatStatusVO status = combatService.getStatus(combat, true);
		//TODO: Verify if changed, then send only if modified. (Use Last-Modified, If-Modified-Since or ETag headers)
		return new ResponseEntity<CombatStatusVO>(status, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value ="/{id}/nonPlayingCombatants", method = RequestMethod.GET)
	@ResponseBody
	public Map<Long, String> getNonPlayingCombatants(@PathVariable Long id) {
		Combat combat = combatService.getCombat(id);
		
		Campaign campaign = combat.getCampaign();
		Collection<SystemCharacter> playingCharacter =  
				Collections2.transform(combat.getCombatCharacters(), new Function<CombatCharacter, SystemCharacter>() {
					@Override
					public SystemCharacter apply(CombatCharacter input) {
						return input.getCharacter();
					}
				});
		Builder<Long, String> builder = ImmutableMap.builder();
		for(SystemCharacter c : combat.getCampaign().getNonPlayerCharacters()) {
			if(!playingCharacter.contains(c)) {
				builder.put(c.getId(), c.getCharacter().getName());
			}
		}
		for(SystemCharacter c : combat.getCampaign().getPlayerCharacters()) {
			if(!playingCharacter.contains(c)) {
				builder.put(c.getId(), c.getCharacter().getName());
			}
		}
		return builder.build();
	}
	
	
}
