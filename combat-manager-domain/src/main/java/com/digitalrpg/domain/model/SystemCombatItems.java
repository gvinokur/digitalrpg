package com.digitalrpg.domain.model;

import java.util.List;

/**
 * For now is just a placeholder for specific data needed in the view for the different systems.
 * 
 * @author gvinokur
 * 
 */
public interface SystemCombatItems<ACTION_TYPE extends SystemAction> {

    public List<ACTION_TYPE> getActions();

    public List<? extends SystemCombatItem> get(String name);
}
