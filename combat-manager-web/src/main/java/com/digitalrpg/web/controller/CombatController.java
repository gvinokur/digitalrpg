package com.digitalrpg.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.digitalrpg.web.controller.model.CampaignVO;
import com.digitalrpg.web.service.CampaignService;

@Controller
@RequestMapping("/combats")
public class CombatController {

	@Autowired
	private CampaignService campaignService;
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createCombat(@RequestParam Long campaignId) {
		CampaignVO campaign = CampaignService.campaignToVOFunction.apply(campaignService.get(campaignId));
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("campaign", campaign);
		modelMap.put("show_content", "combat_create");
		return new ModelAndView("/combat-admin", modelMap );
	}
	
}
