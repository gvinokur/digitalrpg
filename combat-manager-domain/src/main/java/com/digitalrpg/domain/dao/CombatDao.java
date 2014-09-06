package com.digitalrpg.domain.dao;

import java.util.List;

import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.Combat;
import com.digitalrpg.domain.model.CombatCharacter;
import com.digitalrpg.domain.model.SystemCombatCharacterProperties;
import com.digitalrpg.domain.model.SystemCombatItems;
import com.digitalrpg.domain.model.SystemCombatProperties;
import com.digitalrpg.domain.model.SystemType;
import com.digitalrpg.domain.model.characters.SystemCharacter;

public interface CombatDao {

    public Combat createCombat(String name, String description, Campaign campaign, SystemCombatProperties properties);

    public CombatCharacter createCharacter(Combat combat, SystemCharacter character, Boolean hidden, Long initiative, Long order,
            SystemCombatCharacterProperties properties);

    public Combat get(Long id);

    public void startCombat(Combat combat);

    public CombatCharacter getCombatCharacter(Long id);

    public SystemCombatItems getSystemCombatItems(SystemType system);

    public <T> T getCombatItem(Class<T> clazz, Long id);

    public List<Combat> getCombatsForUser(final String user);

    public void delete(CombatCharacter combatCharacter);

    public void update(Combat combat);

    public void update(CombatCharacter<?> combatCharacter);

    public void delete(Long id);

}
