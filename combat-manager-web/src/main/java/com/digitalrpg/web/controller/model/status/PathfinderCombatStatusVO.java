package com.digitalrpg.web.controller.model.status;


public class PathfinderCombatStatusVO extends CombatStatusVO {

	private Integer currentRound;
	
	private Integer currentTurn;

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
