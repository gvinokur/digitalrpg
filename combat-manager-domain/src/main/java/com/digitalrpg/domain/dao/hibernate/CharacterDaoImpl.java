package com.digitalrpg.domain.dao.hibernate;

import java.util.Collection;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.digitalrpg.domain.dao.CharacterDao;
import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.domain.model.characters.Character;
import com.digitalrpg.domain.model.characters.Character.CharacterType;
import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.domain.model.characters.pathfinder.PathfinderCharacter;
import com.digitalrpg.domain.model.characters.pathfinder.PathfinderCharacterProperties;

public class CharacterDaoImpl extends HibernateDao implements CharacterDao {

	public CharacterDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Transactional
	public Character createPlayerCharacter(String name,
			String pictureUrl, String bio, User owner) {
		Character playerCharacter = new Character();
		playerCharacter.setCharacterType(CharacterType.PC);
		playerCharacter.setName(name);
		playerCharacter.setPictureUrl(pictureUrl);
		playerCharacter.setBio(bio);
		playerCharacter.setCreatedBy(owner);
		playerCharacter.setOwner(owner);
		sessionFactory.getCurrentSession().save(playerCharacter);
		return playerCharacter;
	}

	@Transactional
	public Character createNonPlayerCharacter(String name,
			String pictureUrl, String bio, Boolean isPublic, User owner) {
		Character nonPlayerCharacter = new Character();
		nonPlayerCharacter.setCharacterType(CharacterType.NPC);
		nonPlayerCharacter.setName(name);
		nonPlayerCharacter.setBio(bio);
		nonPlayerCharacter.setPictureUrl(pictureUrl);
		nonPlayerCharacter.setCreatedBy(owner);
		nonPlayerCharacter.setOwner(owner);
		sessionFactory.getCurrentSession().save(nonPlayerCharacter);
		return nonPlayerCharacter;
	}

	@Transactional
	public PathfinderCharacter createPathfinderCharacter(Character character,
			PathfinderCharacterProperties properties, Campaign campaign) {
		PathfinderCharacter pathfinderCharacter = new PathfinderCharacter();
		pathfinderCharacter.setCharacter(character);
		pathfinderCharacter.updateProperties(properties);
		pathfinderCharacter.setCampaign(campaign);
		sessionFactory.getCurrentSession().save(pathfinderCharacter);
		return pathfinderCharacter;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Collection<SystemCharacter> getUserCharacters(String user) {
		List<SystemCharacter> characters = sessionFactory
				.getCurrentSession()
				.createQuery(
						"select sc from SystemCharacter sc, Character pc where sc.character = pc and pc.owner.name = :user")
				.setParameter("user", user).list();
		return characters;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public SystemCharacter get(Long id) {
		List<SystemCharacter> list = sessionFactory.getCurrentSession()
				.createQuery("from SystemCharacter sc where sc.id = ?")
				.setParameter(0, id).list();
		if (list.size() == 1) {
			return list.iterator().next();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Collection<SystemCharacter> getUserMonsters(String user) {
		List<SystemCharacter> characters = sessionFactory
				.getCurrentSession()
				.createQuery(
						"select sc from SystemCharacter sc, NonPlayerCharacter npc where sc.character = npc and npc.createdBy.name = :user")
				.setParameter("user", user).list();
		return characters;
	}

	@Transactional
	public void save(SystemCharacter systemCharacter) {
		sessionFactory.getCurrentSession().update(systemCharacter);
	}

	@Transactional
	public void delete(Character oldCharacter) {
		sessionFactory.getCurrentSession().delete(oldCharacter);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Boolean hasPlayerCharacter(Campaign campaign, User user) {
		List<SystemCharacter> characters = sessionFactory
				.getCurrentSession()
				.createQuery(
						"select sc from SystemCharacter sc, PlayerCharacter pc where sc.character = pc and pc.owner = :user and sc.campaign = :campaign")
				.setParameter("user", user)
				.setParameter("campaign", campaign).list();
		
		return !characters.isEmpty();
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public void transfer(Long id, User user) {
		Character character = this.get(id).getCharacter();
		character.setOwner(user);
		this.sessionFactory.getCurrentSession().update(character);
	}

}
