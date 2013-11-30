package com.digitalrpg.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.digitalrpg.domain.model.Combat;
import com.digitalrpg.web.controller.model.CampaignVO;
import com.digitalrpg.web.controller.model.CreateCombatVO;
import com.digitalrpg.web.service.CampaignService;
import com.digitalrpg.web.service.CombatService;

@Controller
@RequestMapping("/combats")
public class CombatController {

	@Autowired
	private CampaignService campaignService;
	
	@Autowired 
	private CombatService combatService;
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView showCreateCombatPage(@RequestParam Long campaignId) {
		CampaignVO campaign = CampaignService.campaignToVOFunction.apply(campaignService.get(campaignId));
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("campaign", campaign);
		modelMap.put("show_content", "combat_create");
		return new ModelAndView("/combat-admin", modelMap );
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView createCombat(@Valid @ModelAttribute("create-combat") CreateCombatVO createCombatVO,
			BindingResult result, RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			CampaignVO campaign = CampaignService.campaignToVOFunction.apply(campaignService.get(createCombatVO.getCampaignId()));
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
		modelMap.put("combat", CombatService.combatToVOFunction.apply(combat));
		modelMap.put("show_content", "combat_view");
		return new ModelAndView("/combat-admin", modelMap);
	}
	
}
