package com.digitalrpg.domain.dao;

import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.Combat;
import com.digitalrpg.domain.model.characters.SystemCharacter;

public interface CombatDao {

	public Combat createCombat(String name, String description, Campaign campaign);
	
	public void createCharacter(Combat combat, SystemCharacter character, Boolean hidden, Long initiative);

	public Combat get(Long id);
	
}
