package com.digitalrpg.web.service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
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
import com.digitalrpg.domain.model.SystemAction;
import com.digitalrpg.domain.model.SystemCombatItem;
import com.digitalrpg.domain.model.SystemCombatItems;
import com.digitalrpg.domain.model.SystemCombatProperties;
import com.digitalrpg.domain.model.SystemType;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.domain.model.characters.pathfinder.PathfinderCharacter;
import com.digitalrpg.domain.model.characters.pathfinder.PathfinderCombatCharacter;
import com.digitalrpg.domain.model.pathfinder.PathfinderCombat;
import com.digitalrpg.web.controller.model.CombatCharacterDataVO;
import com.digitalrpg.web.controller.model.CombatCharacterVO;
import com.digitalrpg.web.controller.model.CombatStatusVO;
import com.digitalrpg.web.controller.model.CombatVO;
import com.digitalrpg.web.controller.model.OrderAndAction;
import com.digitalrpg.web.controller.model.PathfinderCombatCharacterVO;
import com.digitalrpg.web.controller.model.PathfinderCombatStatusVO;
import com.digitalrpg.web.controller.model.PathfinderCombatVO;
import com.digitalrpg.web.controller.model.PlayerExtraInfoVO;
import com.digitalrpg.web.service.combat.CharacterAttributeConverter;
import com.digitalrpg.web.service.combat.ItemAction;
import com.google.common.base.Function;

public class CombatService {

	public static final Function<Combat, CombatVO> combatToVOFunction = new Function<Combat, CombatVO>() {
		public CombatVO apply(Combat in) {
			if (in == null)
				return null;
			CombatVO out = null;
			if (in.getCampaign().getSystem() == SystemType.Pathfinder) {
				out = createPathFinderCombatVO((PathfinderCombat) in);

			}
			out.setId(in.getId());
			out.setName(in.getName());
			out.setDescription(in.getDescription());
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

		public CombatCharacterVO apply(CombatCharacter in) {
			CombatCharacterVO out = null;
			if (in instanceof PathfinderCombatCharacter) {
				out = createPathfinderCombatCharacterVO((PathfinderCombatCharacter) in);
			}
			out.setInitiative(in.getInitiative());
			out.setHidden(in.getHidden());
			out.setId(in.getId());
			out.setImageUrl(in.getCharacter().getCharacter().getPictureUrl());
			return out;
		}

		private CombatCharacterVO createPathfinderCombatCharacterVO(
				PathfinderCombatCharacter in) {
			PathfinderCombatCharacterVO out = new PathfinderCombatCharacterVO();
			out.setCurrentHitPoints(in.getCurrentHitPoints());
			out.setCurrentAction(in.getCurrentAction());
			out.setCurrentConditions(in.getConditions());
			out.setCurrentMagicalEffects(in.getMagicalEffects());
			out.setConditionsAndEffectsString(in
					.getConditionsAndEffectsString());
			out.setHitPointStatus(in.getHitPointsStatus());
			
			PathfinderCharacter character = (PathfinderCharacter) in.getCharacter();
			out.setCharisma(character.getCharisma());
			out.setConstitution(character.getConstitution());
			out.setDexterity(character.getDexterity());
			out.setHp(character.getHp());
			out.setIntelligence(character.getIntelligence());
			out.setStrength(character.getStrength());
			out.setWisdom(character.getWisdom());
			out.setCharacterClass(character.getCharacterClass());
			out.setRace(character.getRace());
			
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
	public Combat createCombat(String name, String description,
			List<Long> players,
			Map<Long, PlayerExtraInfoVO> playersExtraInfoMap, Long campaignId,
			SystemCombatProperties systemCombatProperties) {
		Campaign campaign = campaignService.get(campaignId);
		Combat combat = combatDao.createCombat(name, description, campaign,
				systemCombatProperties);
		Map<Long, Long> orderByPlayer = getOrdersByInitiative(playersExtraInfoMap);
		for (Long playerId : players) {
			SystemCharacter character = characterService.get(playerId);
			PlayerExtraInfoVO extraInfoVO = playersExtraInfoMap.get(playerId);
			combatDao.createCharacter(combat, character,
					extraInfoVO.getHidden(), extraInfoVO.getInitative(), orderByPlayer.get(playerId), null);
		}
		return combat;
	}

	private Map<Long, Long> getOrdersByInitiative(
			Map<Long, PlayerExtraInfoVO> playersExtraInfoMap) {
		SortedMap<Long, Long> playerByInititative = new TreeMap<Long, Long>();
		for (Long pk : playersExtraInfoMap.keySet()) {
			playerByInititative.put(playersExtraInfoMap.get(pk).getInitative(), pk);
		}
		Long i = Long.valueOf(playerByInititative.size());
		Map<Long, Long> orderByInitiative = new HashMap<Long, Long>();
		for(Long initiative : playerByInititative.keySet()) {
			orderByInitiative.put(playerByInititative.get(initiative), i);
			i--;
		}
		return orderByInitiative;
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
	public CombatCharacterVO updateCombatCharacter(Long id,
			String attributeName, String value, User user)
			throws IllegalArgumentException, IllegalAccessException {
		CombatCharacter combatCharacter = combatDao.getCombatCharacter(id);
		Field field = ReflectionUtils.findField(combatCharacter.getClass(),
				attributeName);
		CharacterAttributeConverter<?> characterAttributeConverter = converters
				.get(field.getType());
		if (characterAttributeConverter != null) {
			field.setAccessible(true);
			field.set(combatCharacter,
					characterAttributeConverter.convert(value));
		}
		return combatCharacterToVOfunction.apply(combatCharacter);
	}
	
	@Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
	public CombatCharacterVO updateCombatCharacterItem(Long id,
			String itemType, ItemAction action, Long itemId, User user) throws IllegalArgumentException, IllegalAccessException {
		CombatCharacter combatCharacter = combatDao.getCombatCharacter(id);
		SystemType system = combatCharacter.getCombat().getCampaign().getSystem();
		SystemCombatItems systemCombatItems = combatDao.getSystemCombatItems(system);
		SystemCombatItem item = getItem(systemCombatItems, itemType, itemId);
		switch (action) {
		case ADD:
			combatCharacter.addItem(item);
			break;
		case REMOVE:
			combatCharacter.removeItem(item);
			break;
		}
		return combatCharacterToVOfunction.apply(combatCharacter);
	}

	private SystemCombatItem getItem(SystemCombatItems systemCombatItems, String itemType,
			Long itemId) throws IllegalArgumentException, IllegalAccessException {
		Field field = ReflectionUtils.findField(systemCombatItems.getClass(), itemType);
		if(field != null) {
			field.setAccessible(true);
			List<? extends SystemCombatItem> items = (List<? extends SystemCombatItem>) field.get(systemCombatItems);
			for (SystemCombatItem item : items) {
				if(item.getId().equals(itemId)) {
					return item;
				}
			}
		}
		return null;
	}

	@Transactional
	public Combat nextCharacter(Long combatId) {
		Combat combat = combatDao.get(combatId);
		SystemCombatItems systemCombatItems = combatDao.getSystemCombatItems(combat.getCampaign().getSystem());
		combat.advance(systemCombatItems.getActions());
		return combat;
	}

	@Transactional
	public Combat previousCharacter(Long combatId) {
		Combat combat = combatDao.get(combatId);
		SystemCombatItems systemCombatItems = combatDao.getSystemCombatItems(combat.getCampaign().getSystem());
		combat.back(systemCombatItems.getActions());
		return combat;
	}
	
	public CombatCharacter getCombatCharacter(Long id) {
		return combatDao.getCombatCharacter(id);
	}
	
	public CombatStatusVO getStatus(Combat combat) {
		CombatStatusVO combatStatusVO = null;
		switch (combat.getCampaign().getSystem()) {
		case Pathfinder:
			combatStatusVO = createPathfinderCombatStatusVO(combat);
			break;
		default:
			break;
		}
		combatStatusVO
				.setCurrentCharacterId(combat.getCurrentCharacter() != null ? combat
						.getCurrentCharacter().getId() : null);
		combatStatusVO.setFinished(combat.getCurrentCharacter() == null);
		return combatStatusVO;
	}

	private CombatStatusVO createPathfinderCombatStatusVO(Combat combat) {
		PathfinderCombatStatusVO combatStatusVO = new PathfinderCombatStatusVO();
		combatStatusVO.setCurrentRound(((PathfinderCombat) combat)
				.getCurrentRound());
		combatStatusVO.setCurrentTurn(((PathfinderCombat) combat)
				.getCurrentTurn());
		return combatStatusVO;
	}
	
	public SystemCombatItems getSystemCombatItems(SystemType system) {
		return combatDao.getSystemCombatItems(system);
	}

	public List<Combat> getCombats(User user) {
		return combatDao.getCombatsForUser(user.getName());
	}

	@Transactional
	public Combat updateOrderAndActions(Long id,
			Map<Long, OrderAndAction> charactersOrderAndActions) {
		Combat combat = combatDao.get(id);
		List<? extends SystemAction> actions = combatDao.getSystemCombatItems(combat.getCampaign().getSystem()).getActions();
		for(CombatCharacter character : combat.getCombatCharacters()) {
			OrderAndAction orderAndAction = charactersOrderAndActions.get(character.getId());
			character.setOrder(orderAndAction.getOrder());
			character.setCurrentAction(getCurrentAction(actions, orderAndAction.getAction()));
		}
		return combat;
	}

	private <T extends SystemAction> T getCurrentAction(List<T> actions,
			String actionString) {
		T currentAction = null;
		for(T action : actions) {
			if(action.getLabel().equalsIgnoreCase(actionString)) {
				currentAction = action;
			}
			if(currentAction == null && action.getInitial()) {
				currentAction = action;
			}
		}
		return currentAction;
	}

	@Transactional
	public void endCombat(Combat combat) {
		combat = this.getCombat(combat.getId());
		combat.setActive(Boolean.FALSE);
	}



}
