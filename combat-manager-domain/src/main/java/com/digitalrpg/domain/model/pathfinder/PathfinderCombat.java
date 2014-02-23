package com.digitalrpg.domain.model.pathfinder;

import java.util.List;
import java.util.NavigableSet;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.digitalrpg.domain.model.Combat;
import com.digitalrpg.domain.model.CombatCharacter;
import com.digitalrpg.domain.model.SystemAction;

@Entity
@Table(name = "pathfinder_combats")
public class PathfinderCombat extends Combat {

	private Integer turns;
	
	private Integer roundsPerTurn;

	private Integer currentTurn = 1;
	
	private Integer currentRound = 1;
	
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

	@Override
	public void advance(List<? extends SystemAction> availableActions) {
		boolean end = true;
		CombatCharacter currentCharacter = this.getCurrentCharacter();
		CombatCharacter nextCharacter = null;
		NavigableSet<CombatCharacter> combatCharacters = this.getCombatCharactersAsNavigableSet();
		nextCharacter = combatCharacters.higher(currentCharacter);
		if (nextCharacter == null) {
			end = this.advanceTurn();
			if (!end) {
				nextCharacter = combatCharacters.first();
			}
		}
		currentCharacter.played(availableActions);
		if(nextCharacter != null) nextCharacter.startPlaying(availableActions);
		this.setCurrentCharacter(nextCharacter);
	}
	
	public boolean advanceTurn() {
		if(currentTurn < turns || currentRound<roundsPerTurn) {
			currentRound++;
			if(currentRound > roundsPerTurn) {
				currentTurn++;
				currentRound = 1;
			}
			return false;
		}
		return true;
	}
	
	@Override
	public void back(List<? extends SystemAction> availableActions) {
		CombatCharacter currentCharacter = getCurrentCharacter();
		CombatCharacter nextCharacter = null;
		NavigableSet<CombatCharacter> combatCharacters = getCombatCharactersAsNavigableSet();
		if(currentCharacter == null) {
			nextCharacter = combatCharacters.last();
		} else {
			nextCharacter = combatCharacters.lower(currentCharacter);
			if (nextCharacter == null) {
				boolean end = backTurn();
				if (!end) {
					nextCharacter = combatCharacters.last();
				} else {
					nextCharacter = currentCharacter;
				}
			}	
		}
		if(currentCharacter != nextCharacter) {
			currentCharacter.notPlayed(availableActions);
			nextCharacter.restartPlaying(availableActions);
		}
		setCurrentCharacter(nextCharacter);
	}
	
	private boolean backTurn() {
		if(currentTurn > 1 || currentRound > 1) {
			currentRound--;
			if(currentRound == 0) {
				currentRound = roundsPerTurn;
				currentTurn--;
			}
			return false;
		}
		return true;
	}
	
}