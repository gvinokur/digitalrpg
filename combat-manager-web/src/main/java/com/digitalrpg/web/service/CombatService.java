package com.digitalrpg.web.service;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.digitalrpg.domain.dao.CombatDao;
import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.Combat;
import com.digitalrpg.domain.model.CombatCharacter;
import com.digitalrpg.domain.model.SystemCombatProperties;
import com.digitalrpg.domain.model.SystemType;
import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.domain.model.pathfinder.PathfinderCombat;
import com.digitalrpg.web.controller.model.CombatCharacterVO;
import com.digitalrpg.web.controller.model.CombatVO;
import com.digitalrpg.web.controller.model.PathfinderCombatVO;
import com.digitalrpg.web.controller.model.PlayerExtraInfoVO;
import com.google.common.base.Function;

public class CombatService {

	public static final Function<Combat, CombatVO> combatToVOFunction = new Function<Combat, CombatVO>() {
		public CombatVO apply(Combat in) {
			CombatVO out = null;
			if(in.getCampaign().getSystem() == SystemType.Pathfinder) {
				out = createPathFinderCombatVO((PathfinderCombat) in);
				
			}
			out.setId(in.getId());
			out.setName(in.getName());
			out.setDescription(in.getDescription());
			SortedSet<CombatCharacterVO> combatCharacters = new TreeSet<CombatCharacterVO>();
			for(CombatCharacter combatCharacter: in.getCombatCharacters()) {
				CombatCharacterVO combatCharacterVO = toCombatCharacterVO(combatCharacter);
				combatCharacters.add(combatCharacterVO);
			}
			out.setCombatCharacters(combatCharacters);
			out.setCampaign(CampaignService.campaignToVOFunction.apply(in.getCampaign()));
			return out;
		}

		private CombatVO createPathFinderCombatVO(PathfinderCombat in) {
			PathfinderCombatVO combatVO = new PathfinderCombatVO();
			combatVO.setTurns(in.getTurns());
			combatVO.setRoundsPerTurn(in.getRoundsPerTurn());
			return combatVO;
		}

		private CombatCharacterVO toCombatCharacterVO(
				CombatCharacter in) {
			CombatCharacterVO out = new CombatCharacterVO();
			out.setInitiative(in.getInitiative());
			out.setHidden(in.getHidden());
			out.setId(in.getId());
			out.setCharacterVO(CharacterService.characterToVOfunction.apply(in.getCharacter()));
			return out;
		}
	};

	@Autowired
	private CombatDao combatDao;
	
	@Autowired 
	private CampaignService campaignService;
	
	@Autowired
	private CharacterService characterService;
	
	@Transactional(rollbackFor = Exception.class)
	public Combat createCombat(String name, String description, List<Long> players, Map<Long, PlayerExtraInfoVO> playersExtraInfoMap, Long campaignId, SystemCombatProperties systemCombatProperties){
		Campaign campaign = campaignService.get(campaignId);
		Combat combat = combatDao.createCombat(name, description, campaign, systemCombatProperties);
		for (Long playerId : players) {
			SystemCharacter character = characterService.get(playerId);
			PlayerExtraInfoVO extraInfoVO = playersExtraInfoMap.get(playerId);
			combatDao.createCharacter(combat, character, extraInfoVO.getHidden(), extraInfoVO.getInitative(), null);
		}
		return combat;
	}

	public Combat getCombat(Long id) {
		Combat combat = combatDao.get(id);
		return combat;
	}
	
}
