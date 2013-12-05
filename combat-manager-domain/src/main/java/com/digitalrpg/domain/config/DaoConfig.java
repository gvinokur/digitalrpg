package com.digitalrpg.domain.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.digitalrpg.domain.dao.CampaignDao;
import com.digitalrpg.domain.dao.CharacterDao;
import com.digitalrpg.domain.dao.CombatDao;
import com.digitalrpg.domain.dao.MessageDao;
import com.digitalrpg.domain.dao.SystemDao;
import com.digitalrpg.domain.dao.UserDao;
import com.digitalrpg.domain.dao.hibernate.CampaignDaoImpl;
import com.digitalrpg.domain.dao.hibernate.CharacterDaoImpl;
import com.digitalrpg.domain.dao.hibernate.CombatDaoImpl;
import com.digitalrpg.domain.dao.hibernate.MessageDaoImpl;
import com.digitalrpg.domain.dao.hibernate.SystemDaoImpl;
import com.digitalrpg.domain.dao.hibernate.UserDaoImpl;
import com.digitalrpg.domain.model.factory.CombatFactory;

@Configuration
public class DaoConfig {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Bean
	public UserDao getUserDao() {
		return new UserDaoImpl(sessionFactory);
		
	}
	
	@Bean
	public CampaignDao getCampaignDao() {
		return new CampaignDaoImpl(sessionFactory);
	}
	
	@Bean
	public CharacterDao getCharacterDao() {
		return new CharacterDaoImpl(sessionFactory);
	}
	
	@Bean 
	public MessageDao getMessageDao() {
		return new MessageDaoImpl(sessionFactory);
	}
	
	@Bean
	public CombatDao getCombatDao() {
		return new CombatDaoImpl(sessionFactory, getCombatFactory());
	}
	
	@Bean
	public CombatFactory getCombatFactory() {
		return new CombatFactory();
	}

	@Bean
	public SystemDao getSystemDao(){
		return new SystemDaoImpl(sessionFactory);
	}
}
