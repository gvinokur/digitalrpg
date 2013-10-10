package com.digitalrpg.web.config;

import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.integration.channel.interceptor.MessageSelectingInterceptor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.digitalrpg.web.service.CampaignService;
import com.digitalrpg.web.service.MessageService;
import com.digitalrpg.web.service.RegistrationService;

@Configuration
@ComponentScan(value = { "com.digitalrpg.web.config", "com.digitalrpg.web.security.config",
					"com.digitalrpg.domain.config"}, excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, value = RootConfiguration.class))
@PropertySource({ "classpath:mail.properties" })
public class RootConfiguration {

	@Autowired
	private Environment env;
	
	@Bean
	public RegistrationService getRegistrationService() {
		RegistrationService registrationService = new RegistrationService();
		registrationService.setFrom(env.getProperty("mail.from"));
		return registrationService;
	}
	
	@Bean 
	public CampaignService getCampaignService() {
		CampaignService campaignService = new CampaignService();
		campaignService.setFrom(env.getProperty("mail.from"));
		return campaignService;
	}
	
	@Bean
	public MessageService getMessageService(){
		return new MessageService();
	}
	
	@Bean 
	public VelocityEngine getVelocityEngine() {
		Properties p = new Properties();
		p.setProperty("resource.loader", "class");
		p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		return new VelocityEngine(p);
	}

	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(env.getProperty("mail.host"));
		sender.setPort(Integer.valueOf(env.getProperty("mail.port")));
		sender.setProtocol(env.getProperty("mail.protocol"));
		sender.setUsername(env.getProperty("mail.username"));
		sender.setPassword(env.getProperty("mail.password"));
		Properties javaMailProperties = new Properties();
		javaMailProperties.setProperty("mail.smtp.auth", env.getProperty("mail.smtp.auth"));
		javaMailProperties.setProperty("mail.smtp.starttls.enable", env.getProperty("mail.smtp.starttls.enable"));
		javaMailProperties.setProperty("mail.smtp.quitwait", env.getProperty("mail.smtp.quitwait"));
		sender.setJavaMailProperties(javaMailProperties );
		return sender;
	}
	
}