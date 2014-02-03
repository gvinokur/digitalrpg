package com.digitalrpg.domain.dao.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.digitalrpg.domain.dao.SystemDao;
import com.digitalrpg.domain.model.SystemAction;
import com.digitalrpg.domain.model.pathfinder.PathfinderAction;
import com.digitalrpg.domain.model.pathfinder.PathfinderCondition;
import com.digitalrpg.domain.model.pathfinder.PathfinderMagicalEffect;

public class SystemDaoImpl extends HibernateDao implements SystemDao {

	public SystemDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@SuppressWarnings("rawtypes")
	@Transactional(readOnly = true)
	public PathfinderAction getPathfinderInitialAction() {
		Iterator iterator = this.sessionFactory.getCurrentSession().createQuery("from PathfinderAction where initial = true").iterate();
		return (PathfinderAction) (iterator.hasNext() ? iterator.next() : null);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<PathfinderAction> getPathfinderActions() {
		
		return this.sessionFactory.getCurrentSession().createQuery("from PathfinderAction").list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<PathfinderCondition> getPathfinderConditions() {
		return this.sessionFactory.getCurrentSession().createQuery("from PathfinderCondition").list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<PathfinderMagicalEffect> getPathfinderMagicalEffects() {
		return this.sessionFactory.getCurrentSession().createQuery("from PathfinderMagicalEffect").list();
	}
	
}
