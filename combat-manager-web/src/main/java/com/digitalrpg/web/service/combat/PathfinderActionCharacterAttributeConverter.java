package com.digitalrpg.web.service.combat;

import org.springframework.beans.factory.annotation.Autowired;

import com.digitalrpg.domain.dao.CombatDao;
import com.digitalrpg.domain.model.pathfinder.PathfinderAction;

public class PathfinderActionCharacterAttributeConverter implements
		CharacterAttributeConverter<PathfinderAction> {

	@Autowired
	CombatDao combatDao;
	
	@Override
	public PathfinderAction convert(String input) {
		return combatDao.getCombatItem(PathfinderAction.class, Long.valueOf(input));
	}

	@Override
	public Class<PathfinderAction> getSupportedType() {
		return PathfinderAction.class;
	}

}
