package com.digitalrpg.web.service;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.digitalrpg.domain.dao.UserDao;

public class RegistrationService {

	@Autowired 
	private UserDao userDao;
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private VelocityEngine velocityEngine;
	
	private String from;
	
	/**
	 * Register user and send email, assume everything is validated;
	 * @param user
	 * @param email
	 * @param password
	 * @return
	 */
	public void registerUser(String user, String email, String password, String contextPath) {
		String activationToken = userDao.createUser(user, password);
		sendMail(user, email, activationToken, contextPath);
	}

	private void sendMail(final String user, final String email, final String activationToken, final String contextPath) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
	         public void prepare(MimeMessage mimeMessage) throws Exception {
	            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
	            message.setSubject("Welcome to Digital RPG");
	            message.setTo(email);
	            message.setFrom(from); // could be parameterized...
	            Map<String, Object> model = new HashMap<String, Object>();
	            model.put("activationToken", activationToken);
	            model.put("email", email);
	            model.put("username", user);
	            model.put("contextPath", new URI(contextPath));
	            String text = VelocityEngineUtils.mergeTemplateIntoString(
	               velocityEngine, "com/digitalrpg/templates/mail/registration-confirmation.vm", model);
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

	public void activate(String username, String activationToken) {
		userDao.activateUser(username, activationToken);
	}

	public Boolean validateUsernameAvailability(String username) {
		return userDao.checkUsername(username);
	}
	
}
