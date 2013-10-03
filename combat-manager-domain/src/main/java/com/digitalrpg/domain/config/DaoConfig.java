package com.digitalrpg.domain.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.digitalrpg.domain.dao.UserDao;
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
	
}
