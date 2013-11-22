package com.digitalrpg.web.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.digitalrpg.domain.dao.MessageDao;
import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.domain.model.messages.AcceptRequestMessage;
import com.digitalrpg.domain.model.messages.InviteToCampaignMessage;
import com.digitalrpg.domain.model.messages.InviteToClaimCharacterMessage;
import com.digitalrpg.domain.model.messages.Message;
import com.digitalrpg.domain.model.messages.RequestJoinToCampaignMessage;
import com.digitalrpg.web.controller.model.AcceptRequestJoinMessageVO;
import com.digitalrpg.web.controller.model.InviteClaimCharacterMessageVO;
import com.digitalrpg.web.controller.model.InviteMessageVO;
import com.digitalrpg.web.controller.model.MessageVO;
import com.digitalrpg.web.controller.model.RequestJoinMessageVO;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;

public class MessageService {


	@Autowired
	private MessageDao messageDao;
	
	public static Function<Message, MessageVO> messageToVOFunction = new Function<Message, MessageVO>() {
		public MessageVO apply(Message message) {
			MessageVO out = null;
			if(message instanceof InviteToCampaignMessage) {
				out = translateInviteMessage((InviteToCampaignMessage) message);
			} else if (message instanceof RequestJoinToCampaignMessage) {
				out = translateRequestJoinMessage((RequestJoinToCampaignMessage)message);
			} else if (message instanceof AcceptRequestMessage) {
				out = translateAcceptRequestJoinMessage((AcceptRequestMessage)message);
			} else if (message instanceof InviteToClaimCharacterMessage) {
				out = translateInviteToClaimCharacterMessage((InviteToClaimCharacterMessage)message);
			}
			return out;
		}
		
		private MessageVO translateInviteToClaimCharacterMessage(
				InviteToClaimCharacterMessage message) {
			InviteClaimCharacterMessageVO messageVO = new InviteClaimCharacterMessageVO();
			setBaseProperties(messageVO, message);
			messageVO.setCharacterId(message.getCharacter().getId());
			messageVO.setCharacterName(message.getCharacter().getCharacter().getName());
			return messageVO;
		}

		private MessageVO translateAcceptRequestJoinMessage(
				AcceptRequestMessage message) {
			AcceptRequestJoinMessageVO messageVO = new AcceptRequestJoinMessageVO();
			setBaseProperties(messageVO, message);
			messageVO.setCampaignId(message.getCampaign().getId());
			messageVO.setCampaignName(message.getCampaign().getName());
			return messageVO;
		}

		private MessageVO translateRequestJoinMessage(RequestJoinToCampaignMessage message) {
			RequestJoinMessageVO messageVO = new RequestJoinMessageVO();
			setBaseProperties(messageVO, message);
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

	public Collection<MessageVO> getUserMessages(User user) {
		Collection<Message> userMessages = messageDao.getUserMessages(user);
		return Collections2.transform(userMessages, messageToVOFunction);
	}

	public void deleteMessage(Long id) {
		messageDao.deleteMessage(id);
	}
	
	public MessageVO getMessage(Long id) {
		return messageToVOFunction.apply(messageDao.get(id));
	}
	
	public MessageVO requestJoin(User user, Campaign campaign) {
		return messageToVOFunction.apply(messageDao.requestJoin(user, campaign.getGameMaster(), campaign));
	}

	/**
	 * Invite a friend to join a campaign and create his own character
	 * 
	 * @param id
	 * @param from
	 * @param emailTo
	 * @param userTo
	 * @param campaign
	 * @return
	 */
	public MessageVO invite(Long id, User from,
			String emailTo, User userTo, Campaign campaign) {
		return messageToVOFunction.apply(messageDao.invite(id, from, emailTo, userTo, campaign));
	}

	/**
	 * Creates a new message for the accepted part, deletes the request message
	 * @param requestId
	 * @param campaign
	 * @param user
	 * @return
	 */
	@Transactional
	public Boolean acceptRequest(Long requestId, Campaign campaign, User user) {
		Message message = messageDao.get(requestId);
		messageDao.acceptRequest(user, message.getFrom(), campaign);
		messageDao.deleteMessage(requestId);
		return true;
	}

	/**
	 * Invite a friend to join a campaign by claiming an already created character
	 * 
	 * @param id
	 * @param from
	 * @param emailTo
	 * @param userTo
	 * @param character
	 * @return
	 */
	public MessageVO invite(Long id, User from, String emailTo, User userTo,
			SystemCharacter character) {
		return messageToVOFunction.apply(messageDao.invite(id, from, emailTo, userTo, character));
	}
		
}
