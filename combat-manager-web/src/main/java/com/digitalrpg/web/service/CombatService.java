package com.digitalrpg.web.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.digitalrpg.domain.dao.CombatDao;
import com.digitalrpg.domain.dao.SystemDao;
import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.Combat;
import com.digitalrpg.domain.model.CombatCharacter;
import com.digitalrpg.domain.model.SystemCombatProperties;
import com.digitalrpg.domain.model.SystemType;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.domain.model.characters.pathfinder.PathfinderCombatCharacter;
import com.digitalrpg.domain.model.pathfinder.PathfinderCombat;
import com.digitalrpg.web.controller.model.CombatCharacterVO;
import com.digitalrpg.web.controller.model.CombatVO;
import com.digitalrpg.web.controller.model.PathfinderCombatCharacterVO;
import com.digitalrpg.web.controller.model.PathfinderCombatVO;
import com.digitalrpg.web.controller.model.PlayerExtraInfoVO;
import com.digitalrpg.web.service.combat.CharacterAttributeConverter;
import com.google.common.base.Function;

public class CombatService {

	public static final Function<Combat, CombatVO> combatToVOFunction = new Function<Combat, CombatVO>() {
		public CombatVO apply(Combat in) {
			if(in == null) return null;
			CombatVO out = null;
			if(in.getCampaign().getSystem() == SystemType.Pathfinder) {
				out = createPathFinderCombatVO((PathfinderCombat) in);
				
			}
			out.setId(in.getId());
			out.setName(in.getName());
			out.setDescription(in.getDescription());
			SortedSet<CombatCharacterVO> combatCharacters = new TreeSet<CombatCharacterVO>();
			for(CombatCharacter combatCharacter: in.getCombatCharacters()) {
				CombatCharacterVO combatCharacterVO = combatCharacterToVOfunction.apply(combatCharacter);
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

	};
	
	public static final Function<CombatCharacter, CombatCharacterVO> combatCharacterToVOfunction = new Function<CombatCharacter, CombatCharacterVO>() {

		public CombatCharacterVO apply(
				CombatCharacter in) {
			CombatCharacterVO out = null;
			if(in instanceof PathfinderCombatCharacter) {
				out = createPathfinderCombatCharacterVO((PathfinderCombatCharacter)in);
			}
			out.setInitiative(in.getInitiative());
			out.setHidden(in.getHidden());
			out.setId(in.getId());
			return out;
		}

		private CombatCharacterVO createPathfinderCombatCharacterVO(
				PathfinderCombatCharacter in) {
			PathfinderCombatCharacterVO out = new PathfinderCombatCharacterVO();
			out.setCurrentHitPoints(in.getCurrentHitPoints());
			out.setCurrentAction(in.getCurrentAction());
			out.setCurrentConditions(in.getConditions());
			out.setCurrentMagicalEffects(in.getMagicalEffects());
			out.setConditionsAndEffectsString(in.getConditionsAndEffectsString());
			out.setHitPointStatus(in.getHitPointsStatus());
			return out;
		}
	};

	@Autowired
	private CombatDao combatDao;
	
	@Autowired
	private SystemDao systemDao;
	
	@Autowired 
	private CampaignService campaignService;
	
	@Autowired
	private CharacterService characterService;
	
	@Resource(name = "characterAttributeConvertersMap")
	private Map<Class, CharacterAttributeConverter> converters;
	
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

	public void startCombat(Combat combat) {
		combatDao.startCombat(combat);
		combat.getCampaign().setActiveCombat(combat);
	}

	@Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
	public CombatCharacterVO updateCombatCharacter(Long id, String attributeName,
			String value, User user) throws IllegalArgumentException, IllegalAccessException {
		CombatCharacter combatCharacter = combatDao.getCombatCharacter(id);
		Field field = ReflectionUtils.findField(combatCharacter.getClass(), attributeName);
		CharacterAttributeConverter<?> characterAttributeConverter = converters.get(field.getType());
		if(characterAttributeConverter != null) {
			field.setAccessible(true);
			field.set(combatCharacter, characterAttributeConverter.convert(value));
		}
		return combatCharacterToVOfunction.apply(combatCharacter);
	}
	
}
