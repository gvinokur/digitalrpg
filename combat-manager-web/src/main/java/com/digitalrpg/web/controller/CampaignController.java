package com.digitalrpg.web.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.SortedSet;

import javax.servlet.http.HttpServletRequest;
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
import com.digitalrpg.web.controller.model.CreateCampaignVO;
import com.digitalrpg.web.service.CampaignService;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;

@Controller
@RequestMapping("/campaigns")
public class CampaignController {

	@Autowired
	private CampaignService campaignService;

	private Function<Campaign, CampaignVO> campaignToVOFunction = new Function<Campaign, CampaignVO>() {
		public CampaignVO apply(Campaign in) {
			CampaignVO out = new CampaignVO();
			out.fromCampaign(in);
			return out;
		}
	};
	
	@ModelAttribute("campaigns")
	public Collection<CampaignVO> getUserCampaigns(Principal principal) {
		User user = (User) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		return Collections2.transform(campaignService.getCampaignsForUser(user.getName()), campaignToVOFunction);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showCampaigns() {
		return new ModelAndView("campaigns");
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView createCampaign(@Valid @ModelAttribute("campaign") CreateCampaignVO campaign, BindingResult result, Principal principal ) {
		User user = (User) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		if(!result.hasErrors()) {
			campaignService.createCampaign(campaign.getName(), campaign.getDescription(), user, campaign.getIsPublic());
			return new ModelAndView("redirect:campaigns");
		}
		return new ModelAndView("campaigns", "show_content", "create_campaign");
	}
	
	@RequestMapping(value = "/search/{searchString}", method = RequestMethod.GET)
	@ResponseBody
	public Collection<CampaignVO> searchCampaigns(@PathVariable String searchString) {
		return Collections2.transform(campaignService.search(searchString, 0, Integer.MAX_VALUE), campaignToVOFunction);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public CampaignVO getCampaign(@PathVariable Long id) {
		CampaignVO vo = new CampaignVO();
		vo.fromCampaign(campaignService.get(id));
		return vo;
	}
	
	@RequestMapping(value = "/{id}/invite/{email:.*}")
	@ResponseBody
	public Boolean requestJoin(@PathVariable Long id, @PathVariable String email, Principal principal, HttpServletRequest request) {
		User user = (User) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		String contextPath = "http://" + request.getServerName() + 
				(request.getContextPath()!=null && !request.getContextPath().isEmpty()? "/" + request.getContextPath(): "");
		
		return campaignService.invite(id, user, email, contextPath);
	}
}
