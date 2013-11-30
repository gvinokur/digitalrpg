package com.digitalrpg.domain.model.pathfinder;

import com.digitalrpg.domain.model.SystemCombatProperties;

public class PathfinderCombatProperties implements SystemCombatProperties {

	private Integer turns;
	
	private Integer roundsPerTurn;

	public Integer getTurns() {
		return turns;
	}

	public void setTurns(Integer turns) {
		this.turns = turns;
	}

	public Integer getRoundsPerTurn() {
		return roundsPerTurn;
	}

	public void setRoundsPerTurn(Integer roundsPerTurn) {
		this.roundsPerTurn = roundsPerTurn;
	}
}
