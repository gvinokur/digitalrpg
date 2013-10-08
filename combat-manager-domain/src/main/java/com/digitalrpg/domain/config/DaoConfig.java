package com.digitalrpg.domain.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.digitalrpg.domain.dao.CampaignDao;
import com.digitalrpg.domain.dao.CharacterDao;
import com.digitalrpg.domain.dao.UserDao;
import com.digitalrpg.domain.dao.hibernate.CampaignDaoImpl;
import com.digitalrpg.domain.dao.hibernate.CharacterDaoImpl;
import com.digitalrpg.domain.dao.hibernate.UserDaoImpl;

@Configuration
public class DaoConfig {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Bean
	public UserDao getUserDao() {
		UserDaoImpl userDaoImpl = new UserDaoImpl();
		userDaoImpl.setSessionFactory(sessionFactory);
		return userDaoImpl;
	}
	
	@Bean
	public CampaignDao getCampaignDao() {
		CampaignDaoImpl campaignDaoImpl = new CampaignDaoImpl();
		campaignDaoImpl.setSessionFactory(sessionFactory);
		return campaignDaoImpl;
	}
	
	@Bean
	public CharacterDao getCharacterDao() {
		CharacterDaoImpl characterDaoImpl = new CharacterDaoImpl();
		characterDaoImpl.setSessionFactory(sessionFactory);
		return characterDaoImpl;
	}
}
