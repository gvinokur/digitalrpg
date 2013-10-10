package com.digitalrpg.domain.dao.hibernate;

import java.util.Collection;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.digitalrpg.domain.dao.CharacterDao;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.domain.model.characters.Character;
import com.digitalrpg.domain.model.characters.NonPlayerCharacter;
import com.digitalrpg.domain.model.characters.PlayerCharacter;
import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.domain.model.characters.pathfinder.PathfinderCharacter;
import com.digitalrpg.domain.model.characters.pathfinder.PathfinderCharacterProperties;

public class CharacterDaoImpl implements CharacterDao {

	private SessionFactory sessionFactory;

	@Transactional
	public PlayerCharacter createPlayerCharacter(String name,
			String pictureUrl, String description, String characterClass,
			String race, Integer hp, User owner) {
		PlayerCharacter playerCharacter = new PlayerCharacter();
		playerCharacter.setName(name);
		playerCharacter.setPictureUrl(pictureUrl);
		playerCharacter.setCharacterClass(characterClass);
		playerCharacter.setRace(race);
		playerCharacter.setDescription(description);
		playerCharacter.setHp(hp);
		playerCharacter.setOwner(owner);
		sessionFactory.getCurrentSession().save(playerCharacter);
		return playerCharacter;
	}

	@Transactional
	public NonPlayerCharacter createNonPlayerCharacter(String name,
			String pictureUrl, User createdBy, Boolean isPublic) {
		NonPlayerCharacter nonPlayerCharacter = new NonPlayerCharacter();
		nonPlayerCharacter.setName(name);
		nonPlayerCharacter.setPictureUrl(pictureUrl);
		nonPlayerCharacter.setIsPublic(isPublic);
		nonPlayerCharacter.setCreatedBy(createdBy);
		sessionFactory.getCurrentSession().save(nonPlayerCharacter);
		return nonPlayerCharacter;
	}

	@Transactional
	public PathfinderCharacter createPathfinderCharacter(Character character,
			PathfinderCharacterProperties properties) {
		PathfinderCharacter pathfinderCharacter = new PathfinderCharacter();
		pathfinderCharacter.setCharacter(character);
		pathfinderCharacter.fromProperties(properties);
		sessionFactory.getCurrentSession().save(pathfinderCharacter);
		return pathfinderCharacter;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Collection<SystemCharacter> getUserPlayerCharacters(String user) {
		List<SystemCharacter> characters = sessionFactory
				.getCurrentSession()
				.createQuery(
						"select sc from SystemCharacter sc, PlayerCharacter pc where sc.character = pc and pc.owner.name = :user")
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

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}