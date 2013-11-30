package com.digitalrpg.domain.model.pathfinder;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.digitalrpg.domain.model.Combat;

@Entity
@Table(name = "pathfinder_combats")
public class PathfinderCombat extends Combat {

	private Integer turns;
	
	private Integer roundsPerTurn;

	private Integer currentTurn;
	
	private Integer currentRound;
	
	public Integer getCurrentTurn() {
		return currentTurn;
	}

	public void setCurrentTurn(Integer currentTurn) {
		this.currentTurn = currentTurn;
	}

	public Integer getCurrentRound() {
		return currentRound;
	}

	public void setCurrentRound(Integer currentRound) {
		this.currentRound = currentRound;
	}

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
