package com.digitalrpg.domain.model;

import java.util.Comparator;

public class CombatCharacterComparator implements Comparator<CombatCharacter> {

	public int compare(CombatCharacter o1, CombatCharacter o2) {
		if(o1 == null || o1.getInitiative() == null) {
			if(o2 == null) {
				return 0; 
			} else {
				return -1;
			}
		}
		if(o2 == null || o2.getInitiative() == null) return 1;
		return -o1.getInitiative().compareTo(o2.getInitiative());
	}

}
