package com.digitalrpg.domain.dao.hibernate;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.digitalrpg.domain.dao.CombatDao;
import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.Combat;
import com.digitalrpg.domain.model.CombatCharacter;
import com.digitalrpg.domain.model.SystemCombatCharacterProperties;
import com.digitalrpg.domain.model.SystemCombatItems;
import com.digitalrpg.domain.model.SystemCombatProperties;
import com.digitalrpg.domain.model.SystemType;
import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.domain.model.factory.CombatFactory;
import com.digitalrpg.domain.model.pathfinder.PathfinderCombat;
import com.digitalrpg.domain.model.pathfinder.PathfinderCombatItems;

public class CombatDaoImpl extends HibernateDao implements CombatDao {

	private CombatFactory combatFactory;

	public CombatDaoImpl(SessionFactory sessionFactory,
			CombatFactory combatFactory) {
		super(sessionFactory);
		this.combatFactory = combatFactory;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Combat> getCombatsForUser(final String user) {
		List<Combat> combatAsPlayer = sessionFactory.getCurrentSession().createQuery("select distinct combat from Campaign c join c.playerCharacters psc join c.combats combat, PlayerCharacter pc where combat.active = true and psc!= null and psc.character = pc and pc.owner.name = :user")
			.setParameter("user", user).list();
		List<Combat> combatsAsGameMaster = sessionFactory.getCurrentSession().createQuery("select combat from Campaign c join c.combats combat where c.gameMaster.name = :user and combat.active = true")
				.setParameter("user", user).list();
		List<Combat> sorted = new LinkedList<Combat>();
		sorted.addAll(combatsAsGameMaster);
		sorted.addAll(combatAsPlayer);
		return sorted;
	}
	
	@Transactional
	public Combat createCombat(String name, String description,
			Campaign campaign, SystemCombatProperties systemCombatProperties) {
		Combat combat = combatFactory.createCombat(name, description, campaign,
				systemCombatProperties);
		sessionFactory.getCurrentSession().save(combat);
		return combat;
	}

	@Transactional
	public void createCharacter(Combat combat, SystemCharacter character,
			Boolean hidden, Long initiative,
			SystemCombatCharacterProperties properties) {
		CombatCharacter combatCharacter = combatFactory.createCombatCharacter(
				combat, character, hidden, initiative, properties);

		sessionFactory.getCurrentSession().save(combatCharacter);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Combat get(Long id) {
		List<Combat> list = this.sessionFactory.getCurrentSession()
				.createQuery("from Combat where id = ?").setParameter(0, id)
				.list();
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	@Transactional
	public void startCombat(Combat combat) {
		combat.setActive(true);
		CombatCharacter combatCharacter = combat.getCombatCharacters().iterator().next();
		combat.setCurrentCharacter(combatCharacter);
		// System Specific configuration:
		switch (combat.getCampaign().getSystem()) {
		case Pathfinder:
			startPathFinderCombat(combat);
		}
		sessionFactory.getCurrentSession().saveOrUpdate(combat);
	}

	private void startPathFinderCombat(Combat combat) {
		PathfinderCombat pfCombat = (PathfinderCombat) combat;
		pfCombat.setCurrentRound(1);
		pfCombat.setCurrentTurn(1);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public CombatCharacter getCombatCharacter(Long id) {
		List<CombatCharacter> list = this.sessionFactory.getCurrentSession()
				.createQuery("from CombatCharacter where id = ?")
				.setParameter(0, id).list();
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public SystemCombatItems getSystemCombatItems(SystemType system) {
		switch (system) {
		case Pathfinder:
			PathfinderCombatItems items = new PathfinderCombatItems();
			items.setActions(this.sessionFactory.getCurrentSession().createQuery("from PathfinderAction").list());
			items.setConditions(this.sessionFactory.getCurrentSession().createQuery("from PathfinderCondition").list());
			items.setMagicalEffects(this.sessionFactory.getCurrentSession().createQuery("from PathfinderMagicalEffect").list());
			return items;

		default:
			break;
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly = true)
	public <T> T getCombatItem(Class<T> clazz, Long id) {
		String itemClassName = clazz.getSimpleName();
		List list = this.sessionFactory.getCurrentSession().createQuery("from " + itemClassName + " where id = ?").setParameter(0, id).list();
		if(list.size() == 1) {
			return (T) list.get(0);
		}
		return null;
	}

}
