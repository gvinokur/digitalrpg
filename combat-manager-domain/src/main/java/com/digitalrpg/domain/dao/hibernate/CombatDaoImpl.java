package com.digitalrpg.domain.dao.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.digitalrpg.domain.dao.CombatDao;
import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.Combat;
import com.digitalrpg.domain.model.CombatCharacter;
import com.digitalrpg.domain.model.SystemCombatCharacterProperties;
import com.digitalrpg.domain.model.SystemCombatProperties;
import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.domain.model.factory.CombatFactory;

public class CombatDaoImpl implements CombatDao{

	private SessionFactory sessionFactory;

	@Transactional
	public Combat createCombat(String name, String description,
			Campaign campaign, SystemCombatProperties systemCombatProperties) {
		Combat combat =CombatFactory.createCombat(name, description, campaign, systemCombatProperties);
		sessionFactory.getCurrentSession().save(combat);
		return combat;
	}

	@Transactional
	public void createCharacter(Combat combat, SystemCharacter character,
			Boolean hidden, Long initiative, SystemCombatCharacterProperties properties) {
		CombatCharacter combatCharacter = CombatFactory.createCombatCharacter(combat, character, hidden, initiative, properties);
		
		sessionFactory.getCurrentSession().save(combatCharacter);
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Combat get(Long id) {
		List<Combat> list = this.sessionFactory.getCurrentSession().createQuery("from Combat where id = ?")
			.setParameter(0, id)
			.list();
		if(list.size() == 1) {
			return list.get(0);
		}
		return null;
	}
	
}
