package com.digitalrpg.domain.dao;

import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.Combat;
import com.digitalrpg.domain.model.SystemCombatCharacterProperties;
import com.digitalrpg.domain.model.SystemCombatProperties;
import com.digitalrpg.domain.model.characters.SystemCharacter;

public interface CombatDao {

	public Combat createCombat(String name, String description, Campaign campaign, SystemCombatProperties properties);
	
	public void createCharacter(Combat combat, SystemCharacter character, Boolean hidden, Long initiative, SystemCombatCharacterProperties properties);

	public Combat get(Long id);

	public void startCombat(Combat combat);
	
}
