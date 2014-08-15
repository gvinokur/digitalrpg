package com.digitalrpg.web.service;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.digitalrpg.domain.dao.CombatDao;
import com.digitalrpg.domain.dao.SystemDao;
import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.Combat;
import com.digitalrpg.domain.model.CombatCharacter;
import com.digitalrpg.domain.model.CombatState;
import com.digitalrpg.domain.model.SystemAction;
import com.digitalrpg.domain.model.SystemCombatItem;
import com.digitalrpg.domain.model.SystemCombatItems;
import com.digitalrpg.domain.model.SystemCombatProperties;
import com.digitalrpg.domain.model.SystemType;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.domain.model.characters.pathfinder.PathfinderCharacter;
import com.digitalrpg.domain.model.characters.pathfinder.PathfinderCombatCharacter;
import com.digitalrpg.domain.model.pathfinder.PathfinderAction;
import com.digitalrpg.domain.model.pathfinder.PathfinderCombat;
import com.digitalrpg.web.controller.model.CombatCharacterVO;
import com.digitalrpg.web.controller.model.OrderAndAction;
import com.digitalrpg.web.controller.model.PlayerExtraInfoVO;
import com.digitalrpg.web.controller.model.status.CombatCharacterStatusVO;
import com.digitalrpg.web.controller.model.status.CombatStatusVO;
import com.digitalrpg.web.controller.model.status.PathfinderCombatCharacterStatusVO;
import com.digitalrpg.web.controller.model.status.PathfinderCombatStatusVO;
import com.digitalrpg.web.service.combat.CharacterAttributeConverter;
import com.digitalrpg.web.service.combat.ItemAction;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;

public class CombatService {

    public static final Function<CombatCharacter<?>, CombatCharacterVO> combatCharacterToVOfunction =
            new Function<CombatCharacter<?>, CombatCharacterVO>() {

                public CombatCharacterVO apply(CombatCharacter<?> in) {
                    CombatCharacterVO out = null;
                    if (in instanceof PathfinderCombatCharacter) {
                        out = createPathfinderCombatCharacterVO((PathfinderCombatCharacter) in);
                    }
                    out.setId(in.getId());
                    out.setImageUrl(in.getCharacter().getCharacter().getPictureUrl());
                    return out;
                }

                private CombatCharacterVO createPathfinderCombatCharacterVO(PathfinderCombatCharacter in) {
                    CombatCharacterVO out = new CombatCharacterVO();
                    Map<String, Object> stats = new LinkedHashMap<>();
                    PathfinderCharacter pathfinderCharacter = (PathfinderCharacter) in.getCharacter();
                    stats.put("HP", pathfinderCharacter.getHp());
                    stats.put("AC", pathfinderCharacter.getAc());
                    stats.put("CMB", pathfinderCharacter.getCmb());
                    stats.put("CMD", pathfinderCharacter.getCmd());
                    stats.put("FORT", pathfinderCharacter.getFort());
                    stats.put("REF", pathfinderCharacter.getRef());
                    stats.put("WILL", pathfinderCharacter.getWill());
                    out.setStats(stats);

                    Map<String, Collection<Long>> currentItemsMap = new HashMap<String, Collection<Long>>();

                    currentItemsMap.put("conditions", Collections2.transform(in.getConditions(), combatItemIdExtractor));
                    currentItemsMap.put("magicalEffects", Collections2.transform(in.getMagicalEffects(), combatItemIdExtractor));

                    out.setCurrentItemsMap(currentItemsMap);
                    return out;
                }

                private final Function<SystemCombatItem, Long> combatItemIdExtractor = new Function<SystemCombatItem, Long>() {
                    public Long apply(SystemCombatItem item) {
                        return item.getId();
                    }
                };
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
    public Combat createCombat(String name, String description, List<Long> players, Map<Long, PlayerExtraInfoVO> playersExtraInfoMap,
            Long campaignId, SystemCombatProperties systemCombatProperties) {
        Campaign campaign = campaignService.get(campaignId);
        Combat combat = combatDao.createCombat(name, description, campaign, systemCombatProperties);
        long i = 0;
        for (Long playerId : players) {
            SystemCharacter character = characterService.get(playerId);
            PlayerExtraInfoVO extraInfoVO = playersExtraInfoMap == null ? null : playersExtraInfoMap.get(playerId);
            combatDao.createCharacter(combat, character, BooleanUtils.isTrue(extraInfoVO == null ? null : extraInfoVO.getHidden()), i, i,
                    null);
            i++;
        }
        return combat;
    }

    @Transactional(rollbackFor = Exception.class)
    public void editCombat(Long id, String name, String description, List<Long> players, Map<Long, PlayerExtraInfoVO> playersExtraInfoMap,
            Long campaignId, SystemCombatProperties createSystemCombatProperties) {
        Combat combat = combatDao.get(id);
        combat.setName(name);
        combat.setDescription(description);
        combat.getCombatCharacters().clear();
        long i = 0;
        for (Long playerId : players) {
            SystemCharacter character = characterService.get(playerId);
            PlayerExtraInfoVO extraInfoVO = playersExtraInfoMap == null ? null : playersExtraInfoMap.get(playerId);
            CombatCharacter combatCharacter =
                    combatDao.createCharacter(combat, character, BooleanUtils.isTrue(extraInfoVO == null ? null : extraInfoVO.getHidden()),
                            i, i, null);
            combat.getCombatCharacters().add(combatCharacter);
            i++;
        }
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
    public CombatCharacterVO updateCombatCharacter(Long id, String attributeName, String value, User user) throws IllegalArgumentException,
            IllegalAccessException {
        CombatCharacter combatCharacter = combatDao.getCombatCharacter(id);
        Field field = ReflectionUtils.findField(combatCharacter.getClass(), attributeName);
        CharacterAttributeConverter<?> characterAttributeConverter = converters.get(field.getType());
        if (characterAttributeConverter != null) {
            field.setAccessible(true);
            field.set(combatCharacter, characterAttributeConverter.convert(value));
        }
        return combatCharacterToVOfunction.apply(combatCharacter);
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public CombatCharacterStatusVO updateCombatCharacterItem(Long id, String itemType, ItemAction action, Long itemId, User user)
            throws IllegalArgumentException, IllegalAccessException {
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
        return getStatus(combatCharacter, system, user);
    }

    private SystemCombatItem getItem(SystemCombatItems systemCombatItems, String itemType, Long itemId) throws IllegalArgumentException,
            IllegalAccessException {
        Field field = ReflectionUtils.findField(systemCombatItems.getClass(), itemType);
        if (field != null) {
            field.setAccessible(true);
            List<? extends SystemCombatItem> items = (List<? extends SystemCombatItem>) field.get(systemCombatItems);
            for (SystemCombatItem item : items) {
                if (item.getId().equals(itemId)) {
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

    public CombatStatusVO getStatus(Combat<?> combat) {
        return getStatus(combat, false, null);
    }

    public CombatStatusVO getStatus(Combat<? extends SystemAction> combat, boolean includeCharacters, User user) {
        CombatStatusVO combatStatusVO = null;
        switch (combat.getCampaign().getSystem()) {
            case Pathfinder:
                combatStatusVO = createPathfinderCombatStatusVO(combat);
                break;
            default:
                break;
        }
        combatStatusVO.setCurrentCharacterId(combat.getCurrentCharacter() != null ? combat.getCurrentCharacter().getId() : null);
        combatStatusVO.setFinished(combat.getCurrentCharacter() == null);
        if (includeCharacters) {
            SortedSet<CombatCharacterStatusVO> combatCharactersVO = new TreeSet<CombatCharacterStatusVO>();
            for (CombatCharacter<? extends SystemAction> combatCharacter : combat.getCombatCharacters()) {
                combatCharactersVO.add(getStatus(combatCharacter, combat.getCampaign().getSystem(), user));
            }
            combatStatusVO.setCombatCharacters(combatCharactersVO);
        }
        return combatStatusVO;
    }

    private CombatStatusVO createPathfinderCombatStatusVO(Combat<?> combat) {
        PathfinderCombatStatusVO combatStatusVO = new PathfinderCombatStatusVO();
        combatStatusVO.setCurrentRound(((PathfinderCombat) combat).getCurrentRound());
        combatStatusVO.setCurrentTurn(((PathfinderCombat) combat).getCurrentTurn());
        return combatStatusVO;
    }

    private CombatCharacterStatusVO getStatus(CombatCharacter<?> combatCharacter, SystemType system, User user) {
        CombatCharacterStatusVO vo = null;
        switch (system) {
            case Pathfinder:
                vo = createPathfinderCombatCharacterStatusVO((PathfinderCombatCharacter) combatCharacter);
                break;
            default:
                break;
        }
        vo.setName(combatCharacter.getCharacter().getCharacter().getName());
        vo.setId(combatCharacter.getId());
        vo.setOrder(combatCharacter.getOrder());
        vo.setHidden(combatCharacter.getHidden());
        vo.setEditable(combatCharacter.getCharacter().getCharacter().getOwner().equals(user));
        vo.setType(combatCharacter.getCharacter().getCharacter().getCharacterType().name());
        return vo;
    }

    private CombatCharacterStatusVO createPathfinderCombatCharacterStatusVO(PathfinderCombatCharacter combatCharacter) {
        PathfinderCombatCharacterStatusVO vo = new PathfinderCombatCharacterStatusVO();
        vo.setCurrentAction(getCurrentAction(combatCharacter.getCurrentAction()));
        vo.setCurrentHitPointStatus(combatCharacter.getHitPointsStatus());
        vo.setHp(combatCharacter.getCurrentHitPoints());
        vo.setMaxHp(((PathfinderCharacter) combatCharacter.getCharacter()).getHp());
        vo.setConditionsAndEffects(combatCharacter.getConditionsAndEffectsString());
        return vo;
    }

    private String getCurrentAction(PathfinderAction currentAction) {
        return currentAction.getReady() || currentAction.getDelayed() ? currentAction.getLabel() : "Playing";
    }

    public SystemCombatItems getSystemCombatItems(SystemType system) {
        return combatDao.getSystemCombatItems(system);
    }

    public List<Combat> getCombats(User user) {
        return combatDao.getCombatsForUser(user.getName());
    }

    @Transactional
    public <T extends SystemAction> Combat<T> updateOrderAndActions(Long id, Map<Long, OrderAndAction> charactersOrderAndActions) {
        Combat<T> combat = combatDao.get(id);
        List<T> actions = combatDao.getSystemCombatItems(combat.getCampaign().getSystem()).getActions();
        for (CombatCharacter<T> character : combat.getCombatCharacters()) {
            OrderAndAction orderAndAction = charactersOrderAndActions.get(character.getId());
            character.setOrder(orderAndAction.getOrder());
            character.setCurrentAction(getCurrentAction(actions, orderAndAction.getAction()));
        }
        return combat;
    }

    private <T extends SystemAction> T getCurrentAction(List<T> actions, String actionString) {
        T currentAction = null;
        for (T action : actions) {
            if (action.getLabel().equalsIgnoreCase(actionString)) {
                currentAction = action;
            }
            if (currentAction == null && action.getInitial()) {
                currentAction = action;
            }
        }
        return currentAction;
    }

    @Transactional
    public void endCombat(Combat combat) {
        combat = this.getCombat(combat.getId());
        combat.setState(CombatState.FINISHED);
    }

    public <T extends SystemAction> CombatCharacter<T> getPlayerCharacter(Combat<T> combat, User user) {
        for (CombatCharacter<T> character : combat.getCombatCharacters()) {
            if (character.getCharacter().belongsTo(user)) {
                return character;
            }
        }
        return null;
    }

    public Combat<? extends SystemAction> addCombatant(Combat<? extends SystemAction> combat, Long characterId) {

        SystemCharacter systemCharacter = characterService.get(characterId);
        combatDao.createCharacter(combat, systemCharacter, Boolean.FALSE, Long.valueOf(0),
                Long.valueOf(combat.getCombatCharactersAsNavigableSet().last().getOrder() + 1), null);
        return this.getCombat(combat.getId());
    }

    public void deleteCombatant(Combat combat, Long characterId) {

        CombatCharacter combatCharacter = combatDao.getCombatCharacter(characterId);
        if (combat.getCurrentCharacter() != null && combat.getCurrentCharacter().getId().equals(combatCharacter.getId())) {
            NavigableSet<CombatCharacter> navigableSet = combat.getCombatCharactersAsNavigableSet();
            NavigableSet<CombatCharacter> tailSet = navigableSet.tailSet(combatCharacter, false);
            if (!tailSet.isEmpty()) {
                combat.setCurrentCharacter(tailSet.first());
            } else {
                NavigableSet<CombatCharacter> headSet = navigableSet.headSet(combatCharacter, false);
                if (!headSet.isEmpty()) {
                    combat.setCurrentCharacter(headSet.last());
                } else {
                    combat.setCurrentCharacter(null);
                }
            }
        }
        combatDao.update(combat);
        combatDao.delete(combatCharacter);

    }

    @Transactional
    public void acceptCombat(Long id) {
        Combat combat = combatDao.get(id);
        combat.setState(CombatState.READY);
    }


    public void deleteCombat(Long id) {
        combatDao.delete(id);
    }



}
