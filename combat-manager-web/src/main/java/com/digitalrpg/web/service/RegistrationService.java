package com.digitalrpg.web.service;

import java.net.URI;
import java.net.URISyntaxException;
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

import com.digitalrpg.domain.dao.MessageDao;
import com.digitalrpg.domain.dao.UserDao;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.web.service.MailService.MailType;
import com.google.common.collect.ImmutableList;

public class RegistrationService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageDao messageDao;

	@Autowired
	private MailService mailService;

	/**
	 * Register user and send email, assume everything is validated;
	 * 
	 * @param user
	 * @param email
	 * @param password
	 * @return
	 */
	public void registerUser(String username, String email, String password,
			String contextPath) {
		User user = userDao.createUser(username, password, email);
		sendMail(username, email, user.getActivationToken(), contextPath);
		messageDao.assignOrphanMessages(user);
	}

	private void sendMail(String user, String email, String activationToken,
			String contextPath) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("activationToken", activationToken);
		model.put("email", email);
		model.put("username", user);
		try {
			model.put("contextPath", new URI(contextPath));
		} catch (URISyntaxException ignore) {
		}
		mailService.sendMail("Welcome to Digital RPG", ImmutableList.of(email),
				model, MailType.CONFIRM_REGISTRATION);
	}

	public void activate(String username, String activationToken) {
		userDao.activateUser(username, activationToken);
	}

	public Boolean validateUsernameAvailability(String username) {
		return userDao.checkUsername(username);
	}

}
