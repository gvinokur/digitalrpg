package com.digitalrpg.domain.dao;

import java.util.Collection;

import com.digitalrpg.domain.model.User;
import com.digitalrpg.domain.model.characters.NonPlayerCharacter;
import com.digitalrpg.domain.model.characters.PlayerCharacter;
import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.domain.model.characters.pathfinder.PathfinderCharacter;
import com.digitalrpg.domain.model.characters.pathfinder.PathfinderCharacterProperties;
import com.digitalrpg.domain.model.characters.Character;

public interface CharacterDao {

	public PlayerCharacter createPlayerCharacter(String name, String pictureUrl, String description, String characterClass, String race, Integer hp, User owner);
	
	public NonPlayerCharacter createNonPlayerCharacter(String name, String pictureUrl, User createdBy, Boolean isPublic);
	
	public PathfinderCharacter createPathfinderCharacter(Character character, PathfinderCharacterProperties properties);

	public Collection<SystemCharacter> getUserPlayerCharacters(String user);
	
	public SystemCharacter get(Long id);
	
}