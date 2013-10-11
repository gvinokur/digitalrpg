package com.digitalrpg.web.service;

import com.digitalrpg.domain.model.characters.PlayerCharacter;
import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.domain.model.characters.pathfinder.PathfinderCharacter;
import com.digitalrpg.web.controller.model.CharacterVO;
import com.google.common.base.Function;

public class CharacterService {

	public static Function<SystemCharacter, CharacterVO> systemCharacterToVOFunction =  new Function<SystemCharacter, CharacterVO>() {
		public CharacterVO apply(SystemCharacter character) {
			CharacterVO out = new CharacterVO();
			out.setId(character.getId());
			out.setName(character.getCharacter().getName());
			out.setDescription(character.getCharacter().getDescription());
			out.setPictureUrl(character.getCharacter().getPictureUrl());
			if(character instanceof PathfinderCharacter) {
				//populate pathfinder
			}//TODO: Other systems
			if(character.getCharacter() instanceof PlayerCharacter) {
				out.setOwner(((PlayerCharacter)character.getCharacter()).getOwner());
			} else { //NonPlayerCharacter
				
			}
			return out;
		}
	};
	
}
