package com.digitalrpg.web.service;

import java.net.URI;
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

public class CampaignService {

	@Autowired
	private CampaignDao campaignDao;
	
	@Autowired 
	private UserDao userDao;
	
	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	private CharacterDao characterDao;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired 
	private VelocityEngine velocityEngine;
	
	private String from;
	
	public boolean invite(Long id, User from, String emailTo, String contextPath) {
		Campaign campaign = campaignDao.get(id);
		if(campaign != null) {
			User userTo = userDao.findByMail(emailTo);
			Message message = messageDao.invite(id, from, emailTo, userTo, campaign);
			sendMail(from.getName(), emailTo, contextPath, campaign, message);
			return true;
		}
		return false;
	}
	
	
	
	private void sendMail(final String user, final String email, final String contextPath, final Campaign campaign, final Message inviteMessage) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
	         public void prepare(MimeMessage mimeMessage) throws Exception {
	            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
	            message.setSubject("Join Campaign on DigitalRPG");
	            message.setTo(email);
	            message.setFrom(from); // could be parameterized...
	            Map<String, Object> model = new HashMap<String, Object>();
	            model.put("email", email);
	            model.put("username", user);
	            model.put("contextPath", new URI(contextPath));
	            model.put("campaign", campaign);
	            model.put("message", inviteMessage);
	            String text = VelocityEngineUtils.mergeTemplateIntoString(
	               velocityEngine, "com/digitalrpg/templates/mail/invite-to-campaign.vm", model);
	            message.setText(text, true);
	         }
	      };
	    try {
	    	javaMailSender.send(preparator);
	    } catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
	}

	public void setFrom(String from) {
		this.from = from;
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
	
}
