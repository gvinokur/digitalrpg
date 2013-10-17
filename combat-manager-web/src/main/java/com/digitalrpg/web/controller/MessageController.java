package com.digitalrpg.web.controller;

import java.security.Principal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digitalrpg.domain.dao.MessageDao;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.domain.model.messages.InviteToCampaignMessage;
import com.digitalrpg.domain.model.messages.Message;
import com.digitalrpg.domain.model.messages.RequestJoinToCampaignMessage;
import com.digitalrpg.web.controller.model.InviteMessageVO;
import com.digitalrpg.web.controller.model.MessageVO;
import com.digitalrpg.web.controller.model.RequestJoinMessageVO;
import com.digitalrpg.web.service.MessageService;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;

@Controller
@RequestMapping("/messages")
public class MessageController {

	@Autowired
	private MessageService messageService;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Collection<MessageVO> getUserMessages(Principal principal) {
		if(principal== null) return ImmutableList.of();
		User user = (User) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		
		return messageService.getUserMessages(user);
		
	}
	
	@RequestMapping(value= "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteMessage(@PathVariable Long id) {
		messageService.deleteMessage(id);
	}
	
}
