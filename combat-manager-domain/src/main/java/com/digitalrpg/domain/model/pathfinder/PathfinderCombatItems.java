package com.digitalrpg.domain.model.pathfinder;

import java.util.List;

import com.digitalrpg.domain.model.SystemCombatItem;
import com.digitalrpg.domain.model.SystemCombatItems;

public class PathfinderCombatItems implements SystemCombatItems<PathfinderAction> {

    private List<PathfinderAction> actions;

    private List<PathfinderCondition> conditions;

    private List<PathfinderMagicalEffect> magicalEffects;

    public List<PathfinderAction> getActions() {
        return actions;
    }

    public void setActions(List<PathfinderAction> actions) {
        this.actions = actions;
    }

    public List<PathfinderCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<PathfinderCondition> conditions) {
        this.conditions = conditions;
    }

    public List<PathfinderMagicalEffect> getMagicalEffects() {
        return magicalEffects;
    }

    public void setMagicalEffects(List<PathfinderMagicalEffect> magicalEffects) {
        this.magicalEffects = magicalEffects;
    }

    @Override
    public List<? extends SystemCombatItem> get(String name) {
        if ("conditions".equalsIgnoreCase(name)) {
            return this.conditions;
        } else if ("magicalEffects".equalsIgnoreCase(name)) {
            return this.magicalEffects;
        }
        return null;
    }

}
