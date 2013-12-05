package com.digitalrpg.domain.model.factory;

import org.springframework.beans.factory.annotation.Autowired;

import com.digitalrpg.domain.dao.SystemDao;
import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.Combat;
import com.digitalrpg.domain.model.CombatCharacter;
import com.digitalrpg.domain.model.SystemCombatCharacterProperties;
import com.digitalrpg.domain.model.SystemCombatProperties;
import com.digitalrpg.domain.model.SystemType;
import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.domain.model.characters.pathfinder.PathfinderCombatCharacter;
import com.digitalrpg.domain.model.pathfinder.PathfinderCombat;
import com.digitalrpg.domain.model.pathfinder.PathfinderCombatProperties;

public class CombatFactory {

	@Autowired
	SystemDao systemDao;
	
	public Combat createCombat(String name, String description,
			Campaign campaign, SystemCombatProperties systemCombatProperties) {
		if(campaign.getSystem() == SystemType.Pathfinder) {
			PathfinderCombat combat = new PathfinderCombat();
			PathfinderCombatProperties combatProperties = (PathfinderCombatProperties) systemCombatProperties;
			combat.setName(name);
			combat.setDescription(description);
			combat.setCampaign(campaign);
			combat.setTurns(combatProperties.getTurns());
			combat.setRoundsPerTurn(combatProperties.getRoundsPerTurn());
			return combat;
		}
		return null;
	}
	
	public CombatCharacter createCombatCharacter(Combat combat, SystemCharacter character,
			Boolean hidden, Long initiative, SystemCombatCharacterProperties properties) {
		if(combat.getCampaign().getSystem() == SystemType.Pathfinder) {
			PathfinderCombatCharacter combatCharacter = new PathfinderCombatCharacter();
			combatCharacter.setCharacter(character);
			combatCharacter.setHidden(hidden);
			combatCharacter.setInitiative(initiative);
			combatCharacter.setCombat(combat);
			combatCharacter.setCurrentAction(systemDao.getPathfinderInitialAction());
			return combatCharacter;
		}
		return null;
	}
	
}
