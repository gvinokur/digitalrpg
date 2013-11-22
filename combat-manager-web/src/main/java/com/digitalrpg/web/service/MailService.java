package com.digitalrpg.web.service;

import java.net.URI;
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

import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.web.controller.model.MessageVO;

public class MailService {

	public static enum MailType {
		INVITE_TO_CAMPAIGN("com/digitalrpg/templates/mail/invite-to-campaign.vm"),
		CONFIRM_REGISTRATION("com/digitalrpg/templates/mail/registration-confirmation.vm"), 
		CLAIM_CHARACTER("com/digitalrpg/templates/mail/claim-character.vm");
		
		private String templateFile; 
		private MailType(String templateFile) {
			this.templateFile = templateFile;
		}
		public String getTemplateFile() {
			return templateFile;
		}
	}
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired 
	private VelocityEngine velocityEngine;
	
	private String from;
	
	public boolean sendMail(final String subject, final List<String> emails, final Map<String, Object> modelMap, final MailType type) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
	         public void prepare(MimeMessage mimeMessage) throws Exception {
	            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
	            message.setSubject(subject);
	            message.setTo(emails.toArray(new String[0]));
	            message.setFrom(from); // could be parameterized...
	            String text = VelocityEngineUtils.mergeTemplateIntoString(
	               velocityEngine, type.getTemplateFile(), modelMap);
	            message.setText(text, true);
	         }
	      };
	    try {
	    	javaMailSender.send(preparator);
	    } catch (MailException ex) {
            //TODO: simply log it and go on...
            System.err.println(ex.getMessage());
            return false;
        }
	    return true;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	
}
