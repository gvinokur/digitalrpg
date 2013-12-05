package com.digitalrpg.domain.dao;

import java.util.List;

import com.digitalrpg.domain.model.pathfinder.PathfinderAction;
import com.digitalrpg.domain.model.pathfinder.PathfinderCondition;
import com.digitalrpg.domain.model.pathfinder.PathfinderMagicalEffect;

public interface SystemDao {

	public PathfinderAction getPathfinderInitialAction();
	
	public List<PathfinderAction> getPathfinderActions();
	
	public List<PathfinderCondition> getPathfinderConditions();
	
	public List<PathfinderMagicalEffect> getPathfinderMagicalEffects();
	
}
