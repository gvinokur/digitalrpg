package com.digitalrpg.domain.dao;

import java.util.Collection;

import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.domain.model.characters.Character;
import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.domain.model.characters.pathfinder.PathfinderCharacter;
import com.digitalrpg.domain.model.characters.pathfinder.PathfinderCharacterProperties;

public interface CharacterDao {

    public Character createPlayerCharacter(String name, String pictureUrl, String bio, User owner);

    public Character createNonPlayerCharacter(String name, String pictureUrl, String bio,
            Boolean isPublic, User user);

    public PathfinderCharacter createPathfinderCharacter(Character character,
            PathfinderCharacterProperties properties, Campaign campaign);

    public Collection<SystemCharacter> getUserCharacters(String user);

    public SystemCharacter get(Long id);

    public Collection<SystemCharacter> getUserMonsters(String name);

    public void save(SystemCharacter systemCharacter);

    public void delete(Character oldCharacter);

    public Boolean hasPlayerCharacter(Campaign campaign, User user);

    public void transfer(Long id, User user);

    public void deleteSystemCharacter(Long id);

}
