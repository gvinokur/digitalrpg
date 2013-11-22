package com.digitalrpg.domain.dao.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.digitalrpg.domain.dao.CombatDao;
import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.Combat;
import com.digitalrpg.domain.model.CombatCharacter;
import com.digitalrpg.domain.model.characters.SystemCharacter;

public class CombatDaoImpl implements CombatDao{

	private SessionFactory sessionFactory;

	@Transactional
	public Combat createCombat(String name, String description,
			Campaign campaign) {
		Combat combat = new Combat();
		combat.setName(name);
		combat.setDescription(description);
		combat.setCampaign(campaign);
		sessionFactory.getCurrentSession().save(combat);
		return combat;
	}

	@Transactional
	public void createCharacter(Combat combat, SystemCharacter character,
			Boolean hidden, Long initiative) {
		CombatCharacter combatCharacter = new CombatCharacter();
		combatCharacter.setCharacter(character);
		combatCharacter.setHidden(hidden);
		combatCharacter.setInitiative(initiative);
		combatCharacter.setCombat(combat);
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
