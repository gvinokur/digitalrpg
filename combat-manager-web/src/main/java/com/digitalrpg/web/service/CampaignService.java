package com.digitalrpg.web.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.digitalrpg.domain.dao.CampaignDao;
import com.digitalrpg.domain.dao.CharacterDao;
import com.digitalrpg.domain.dao.MessageDao;
import com.digitalrpg.domain.dao.UserDao;
import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.SystemType;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.domain.model.messages.Message;
import com.digitalrpg.web.controller.model.CampaignVO;
import com.digitalrpg.web.controller.model.MessageVO;
import com.digitalrpg.web.service.MailService.MailType;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;

public class CampaignService {

	@Autowired
	private CampaignDao campaignDao;
	
	@Autowired 
	private UserDao userDao;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private CharacterDao characterDao;
	
	@Autowired
	private MailService mailService;

	public static Function<Campaign, CampaignVO> campaignToVOFunction = new Function<Campaign, CampaignVO>() {
		public CampaignVO apply(Campaign in) {
			CampaignVO out = new CampaignVO();
			out.fromCampaign(in);
			return out;
		}
	};
	
	public boolean invite(Long id, User from, String emailTo, String contextPath) {
		Campaign campaign = campaignDao.get(id);
		if(campaign != null) {
			User userTo = userDao.findByMail(emailTo);
			MessageVO message = messageService.invite(id, from, emailTo, userTo, campaign);
			sendMail(from.getName(), emailTo, contextPath, campaign, message);
			return true;
		}
		return false;
	}
	
	
	
	private void sendMail(final String user, final String email, final String contextPath, final Campaign campaign, final MessageVO inviteMessage) {
		Map<String, Object> model = new HashMap<String, Object>();
        model.put("email", email);
        model.put("username", user);
        try {
			model.put("contextPath", new URI(contextPath));
		} catch (URISyntaxException ignore) { }
        model.put("campaign", campaign);
        model.put("message", inviteMessage);
        
        mailService.sendMail("Join Campaign on DigitalRPG", ImmutableList.of(email), model, MailType.INVITE_TO_CAMPAIGN);
	}

	public Campaign createCampaign(String name, String description, User gm,
			Boolean isPublic, SystemType system) {
		return campaignDao.createCampaign(name, description, gm, isPublic, system);
	}

	public List<Campaign> getCampaignsForUser(String user) {
		return campaignDao.getCampaignsForUser(user);
	}

	public Campaign get(Long id) {
		return campaignDao.get(id);
	}

	public Collection<Campaign> search(String searchString, int offset,
			int limit) {
		return campaignDao.search(searchString, offset, limit);
	}

	public void addPlayerCharacter(Long campaignd, Long characterId) {
		SystemCharacter character = characterDao.get(characterId);
		campaignDao.addPlayerCharacter(campaignd,character);
	}



	public MessageVO requestInvite(Long id, User user) {
		Campaign campaign = campaignDao.get(id);
		MessageVO message =  messageService.requestJoin(user, campaign);
		return message;
	}



	public Boolean acceptRequest(Long id, Long requestId, User user) {
		Campaign campaign = campaignDao.get(id);
		
		return messageService.acceptRequest(requestId, campaign, user);
	}
	
}
