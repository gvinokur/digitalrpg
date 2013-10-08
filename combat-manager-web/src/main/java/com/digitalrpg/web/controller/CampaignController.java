package com.digitalrpg.web.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.SortedSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.digitalrpg.domain.dao.CampaignDao;
import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.web.controller.model.CampaignVO;

@Controller
@RequestMapping("/campaigns")
public class CampaignController {

	@Autowired
	private CampaignDao campaignDao;

	@ModelAttribute("campaigns")
	public SortedSet<Campaign> getUserCampaigns(Principal principal) {
		User user = (User) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		return campaignDao.getCampaignsForUser(user.getName());
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showCampaigns() {
		return new ModelAndView("campaigns");
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView createCampaign(@Valid @ModelAttribute("campaign") CampaignVO campaign, BindingResult result, Principal principal ) {
		User user = (User) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		if(!result.hasErrors()) {
			campaignDao.createCampaign(campaign.getName(), campaign.getDescription(), user, campaign.getIsPublic());
			return new ModelAndView("redirect:campaigns");
		}
		return new ModelAndView("campaigns", "show_content", "create_campaign");
	}
	
	@RequestMapping(value = "/search/{searchString}", method = RequestMethod.GET)
	@ResponseBody
	public Collection<Campaign> searchCampaigns(@PathVariable String searchString) {
		return campaignDao.search(searchString, 0, Integer.MAX_VALUE);
	}
	
}
