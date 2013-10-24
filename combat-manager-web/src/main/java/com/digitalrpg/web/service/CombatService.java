package com.digitalrpg.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.digitalrpg.domain.dao.CombatDao;
import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.Combat;
import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.web.controller.model.CombatVO;
import com.digitalrpg.web.controller.model.PlayerExtraInfoVO;
import com.google.common.base.Function;

public class CombatService {

	public static final Function<Combat, CombatVO> combatToVOFunction = new Function<Combat, CombatVO>() {
		public CombatVO apply(Combat in) {
			CombatVO out = new CombatVO();
			out.setId(in.getId());
			out.setName(in.getName());
			out.setDescription(in.getDescription());
			//Add characters
			return out;
		}
	};

	@Autowired
	private CombatDao combatDao;
	
	@Autowired 
	private CampaignService campaignService;
	
	@Autowired
	private CharacterService characterService;
	
	@Transactional
	public Combat createCombat(String name, String description, List<Long> players, Map<Long, PlayerExtraInfoVO> playersExtraInfoMap, Long campaignId){
		Campaign campaign = campaignService.get(campaignId);
		Combat combat = combatDao.createCombat(name, description, campaign);
		for (Long playerId : players) {
			SystemCharacter character = characterService.get(playerId);
			PlayerExtraInfoVO extraInfoVO = playersExtraInfoMap.get(playerId);
			combatDao.createCharacter(combat, character, extraInfoVO.getHidden(), extraInfoVO.getInitative());
		}
		return combat;
	}
	
}
