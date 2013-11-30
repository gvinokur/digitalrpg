package com.digitalrpg.web.controller.model;

public class PathfinderCombatVO extends CombatVO{

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
