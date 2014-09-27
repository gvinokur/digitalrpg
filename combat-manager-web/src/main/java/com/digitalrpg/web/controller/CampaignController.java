package com.digitalrpg.web.controller;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.digitalrpg.domain.dao.CharacterDao;
import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.SystemType;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.domain.model.messages.Message;
import com.digitalrpg.web.controller.model.CampaignVO;
import com.digitalrpg.web.controller.model.CreateCampaignVO;
import com.digitalrpg.web.controller.model.MessageVO;
import com.digitalrpg.web.service.CampaignService;
import com.digitalrpg.web.service.CharacterService;
import com.digitalrpg.web.service.MessageService;
import com.digitalrpg.web.service.TransactionalUserService;
import com.digitalrpg.web.service.UserService;
import com.digitalrpg.web.service.UserWrapper;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

@Controller
@RequestMapping("/campaigns")
public class CampaignController {

    private final static String SHOW_CAMPAIGN_URL = "/campaigns/%s/show";

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private CharacterService characterService;

    @Autowired
    private TransactionalUserService userService;


    @ModelAttribute("createCampaignVO")
    public CreateCampaignVO getCreateCampaignVO() {
        return new CreateCampaignVO();
    }

    @ModelAttribute("campaigns")
    public Collection<CampaignVO> getUserCampaigns(@AuthenticationPrincipal UserWrapper user) {
        return Collections2.transform(campaignService.getCampaignsForUser(user.unwrap()), CampaignService.campaignToVOFunction);
    }

    @ModelAttribute("systems")
    public SystemType[] getSystemTypes() {
        return SystemType.values();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showCampaigns(@RequestParam(required = false, value = "show_content") String showContent) {
        return new ModelAndView("campaigns");
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView editCampaign(@PathVariable Long id, @AuthenticationPrincipal UserWrapper user, RedirectAttributes attributes) {
        Campaign campaign = campaignService.get(id);
        if (campaign.getGameMaster().equals(user.unwrap())) {
            ModelMap modelMap = new ModelMap();
            modelMap.put("show_content", "create_campaign");
            CreateCampaignVO createCampaignVO = new CreateCampaignVO();
            createCampaignVO.setId(id);
            createCampaignVO.setIsPublic(campaign.getIsPublic());
            createCampaignVO.setSystemType(campaign.getSystem());
            createCampaignVO.setDescription(campaign.getDescription());
            createCampaignVO.setName(campaign.getName());
            modelMap.put("createCampaignVO", createCampaignVO);
            return new ModelAndView("/campaigns", modelMap);
        } else {
            attributes.addFlashAttribute("error_message", "Only the GM can edit the campaign.");
            return new ModelAndView("redirect:/campaigns/" + id + "/show");
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView createCampaign(@Valid @ModelAttribute("createCampaignVO") CreateCampaignVO createCampaignVO, BindingResult result,
            @AuthenticationPrincipal UserWrapper user, RedirectAttributes attributes) {
        if (!result.hasErrors()) {
            Campaign campaign;
            if (createCampaignVO.getId() != null) {
                campaign =
                        campaignService.editCampaign(createCampaignVO.getId(), createCampaignVO.getName(),
                                createCampaignVO.getDescription(), user.unwrap(), createCampaignVO.getIsPublic());
                attributes.addFlashAttribute("form_message", String.format("Campaign %s was edited successfully", campaign.getName()));
            } else {
                campaign =
                        campaignService.createCampaign(createCampaignVO.getName(), createCampaignVO.getDescription(), user.unwrap(),
                                createCampaignVO.getIsPublic(), createCampaignVO.getSystemType());
                attributes.addFlashAttribute("form_message", String.format("Campaign %s was created successfully", campaign.getName()));
            }
            userService.trackItem(user.unwrap(), String.format(SHOW_CAMPAIGN_URL, campaign.getId()), campaign.getName());
            return new ModelAndView("redirect:campaigns/" + campaign.getId() + "/show");
        }
        return new ModelAndView("campaigns", "show_content", "create_campaign");
    }

    @RequestMapping(value = "/search/{searchString}", method = RequestMethod.GET)
    @ResponseBody
    public Collection<CampaignVO> searchCampaigns(@PathVariable String searchString) {
        return Collections2.transform(campaignService.search(searchString, 0, Integer.MAX_VALUE), CampaignService.campaignToVOFunction);
    }

    @RequestMapping(value = "/{id}/show", method = RequestMethod.GET)
    public ModelAndView showCampaign(@PathVariable Long id, @AuthenticationPrincipal UserWrapper user) {
        Campaign campaign = campaignService.get(id);
        userService.trackItem(user.unwrap(), String.format(SHOW_CAMPAIGN_URL, campaign.getId()), campaign.getName());
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("show_content", "campaign_view");
        modelMap.put("campaign", campaign);
        List<SystemCharacter> userCharacters = campaign.getUserCharacters(user.unwrap());
        modelMap.put("characters", userCharacters);
        List<SystemCharacter> otherCharacters = Lists.newArrayList(campaign.getCharacters());
        otherCharacters.removeAll(userCharacters);
        modelMap.put("otherCharacters", otherCharacters);
        return new ModelAndView("/campaigns", modelMap);
    }

    @RequestMapping(value = "/{id}/invite", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> invite(@PathVariable Long id, @RequestParam String usernameOrEmail, @RequestParam String message,
            @AuthenticationPrincipal UserWrapper user, HttpServletRequest request) {
        String contextPath =
                "http://" + request.getServerName()
                        + (request.getContextPath() != null && !request.getContextPath().isEmpty() ? "/" + request.getContextPath() : "");

        return campaignService.inviteWithUsernameOrEmai(id, user.unwrap(), usernameOrEmail, message, contextPath);
    }


    @RequestMapping(value = "/{id}/invite/{email:.*}")
    @ResponseBody
    public Boolean invite(@PathVariable Long id, @PathVariable String email, @AuthenticationPrincipal UserWrapper user,
            HttpServletRequest request) {
        String contextPath =
                "http://" + request.getServerName()
                        + (request.getContextPath() != null && !request.getContextPath().isEmpty() ? "/" + request.getContextPath() : "");

        return campaignService.invite(id, user.unwrap(), email, contextPath);
    }

    @RequestMapping(value = "/{id}/join/request", method = RequestMethod.POST)
    @ResponseBody
    public MessageVO requestJoin(@PathVariable Long id, @AuthenticationPrincipal UserWrapper user) {
        return campaignService.requestInvite(id, user.unwrap());
    }

    @RequestMapping(value = "/{id}/join", method = RequestMethod.GET)
    public String showJoinCampaign(@PathVariable Long id, @RequestParam Long messageId, @AuthenticationPrincipal UserWrapper user,
            RedirectAttributes attributes) {
        Message message = messageService.getMessage(messageId);
        Campaign campaign = campaignService.get(id);
        if (message.getTo() == null || !message.getTo().equals(user.unwrap())) {
            attributes
                    .addFlashAttribute(
                            "error_message",
                            "The invitation was sent to a different username/mail, make sure you logged in with the right one or request authorization again to the Game Master.");
        } else if (campaign.isMember(user.unwrap())) {
            attributes.addFlashAttribute("warning_message", "You are already a member on this campaign.");
        } else if (campaign.getGameMaster().equals(user.unwrap())) {
            attributes.addFlashAttribute("warning_message", "You are the Game master of this campaign, you cannot be a player also.");
        } else {
            this.campaignService.acceptRequest(id, messageId, user.unwrap());
            attributes.addFlashAttribute("form_message", "Welcome to campaign, now you can create your character.");
        }

        return "redirect:/campaigns/" + id + "/show";
    }

    @RequestMapping(value = "/{id}/accept/{requestId}")
    public String acceptRequest(@PathVariable Long id, @PathVariable Long requestId, @AuthenticationPrincipal UserWrapper user,
            RedirectAttributes attributes) {
        Message message = messageService.getMessage(requestId);
        Campaign campaign = campaignService.get(id);
        if (!message.getTo().equals(user.unwrap()) || !campaign.getGameMaster().equals(user.unwrap())) {
            attributes.addFlashAttribute("error_message", "Only the Game master can accept a request.");
            return "redirect:/lobby";
        } else if (campaign.isMember(message.getFrom())) {
            attributes.addFlashAttribute("warning_message", "The user is already a member on this campaign.");
            this.messageService.deleteMessage(requestId);
        } else {
            this.campaignService.acceptRequest(id, requestId, message.getFrom());
        }
        return "redirect:/campaigns/" + id + "/show";
    }

}
