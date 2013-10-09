package com.digitalrpg.web.controller;

import java.security.Principal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
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
import com.google.common.base.Function;
import com.google.common.collect.Collections2;

@Controller
@RequestMapping("/messages")
public class MessageController {

	@Autowired
	private MessageDao messageDao;
	
	private static Function<Message, MessageVO> messageToVOFunction = new Function<Message, MessageVO>() {
		public MessageVO apply(Message message) {
			MessageVO out = null;
			if(message instanceof InviteToCampaignMessage) {
				out = translateInviteMessage((InviteToCampaignMessage) message);
			} else if (message instanceof RequestJoinToCampaignMessage) {
				out = translateRequestJoinMessage((RequestJoinToCampaignMessage)message);
			}
			return out;
		}
		
		private MessageVO translateRequestJoinMessage(RequestJoinToCampaignMessage message) {
			RequestJoinMessageVO messageVO = new RequestJoinMessageVO();
			setBaseProperties(messageVO, message);
			messageVO.setCharacter(message.getCharacter());
			messageVO.setCampaignId(message.getCampaign().getId());
			messageVO.setCampaignName(message.getCampaign().getName());
			return messageVO;
		}
	
		private MessageVO translateInviteMessage(InviteToCampaignMessage message) {
			InviteMessageVO messageVO = new InviteMessageVO();
			setBaseProperties(messageVO, message);
			messageVO.setCampaignId(message.getCampaign().getId());
			messageVO.setCampaignName(message.getCampaign().getName());
			return messageVO;
		}
		
		private void setBaseProperties(MessageVO out, Message in) {
			out.setFrom(in.getFrom());
			out.setTo(in.getTo());
			out.setMailTo(in.getToMail());
			out.setId(in.getId());
		}
	};
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Collection<MessageVO> getUserMessages(Principal principal) {
		User user = (User) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		Collection<Message> userMessages = messageDao.getUserMessages(user);
		return Collections2.transform(userMessages, messageToVOFunction);
	}
	
}
