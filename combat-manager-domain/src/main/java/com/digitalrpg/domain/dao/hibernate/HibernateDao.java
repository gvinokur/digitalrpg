package com.digitalrpg.domain.dao.hibernate;

import org.hibernate.SessionFactory;

public abstract class HibernateDao {

	protected SessionFactory sessionFactory;
	
	public HibernateDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
}
