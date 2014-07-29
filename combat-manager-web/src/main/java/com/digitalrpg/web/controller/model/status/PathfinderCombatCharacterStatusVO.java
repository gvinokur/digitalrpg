package com.digitalrpg.web.controller.model.status;

public class PathfinderCombatCharacterStatusVO extends CombatCharacterStatusVO {

    private int maxHp;

    private int hp;

    @Deprecated
    private String currentHitPointStatus;

    private String conditionsAndEffects;

    public String getCurrentHitPointStatus() {
        return currentHitPointStatus;
    }

    public void setCurrentHitPointStatus(String currentHitPointStatus) {
        this.currentHitPointStatus = currentHitPointStatus;
    }

    public String getConditionsAndEffects() {
        return conditionsAndEffects;
    }

    public void setConditionsAndEffects(String conditionAndEffects) {
        this.conditionsAndEffects = conditionAndEffects;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }



}
