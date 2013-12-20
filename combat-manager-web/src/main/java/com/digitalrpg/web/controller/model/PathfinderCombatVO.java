package com.digitalrpg.web.controller.model;

public class PathfinderCombatVO extends CombatVO{

	private Integer turns;
	
	private Integer roundsPerTurn;
	
	private Integer currentRound;
	
	private Integer currentTurn;

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

	public Integer getCurrentRound() {
		return currentRound;
	}

	public void setCurrentRound(Integer currentRound) {
		this.currentRound = currentRound;
	}

	public Integer getCurrentTurn() {
		return currentTurn;
	}

	public void setCurrentTurn(Integer currentTurn) {
		this.currentTurn = currentTurn;
	}
	
}
