package com.digitalrpg.web.controller.model;

import javax.validation.constraints.NotNull;

public class CreateCombatPathfinderPropertiesVO {

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

	@NotNull
	private Integer turns;
	
	@NotNull
	private Integer roundsPerTurn;
	
}
