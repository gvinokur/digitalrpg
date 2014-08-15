package com.digitalrpg.web.controller.model;

import java.util.Collection;
import java.util.Map;

import com.digitalrpg.domain.model.SystemCombatItem;

public class CombatCharacterVO {


    private Long id;

    private String imageUrl;

    private Map<String, Object> stats;

    private Map<String, Collection<Long>> currentItemsMap;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Map<String, Object> getStats() {
        return stats;
    }

    public void setStats(Map<String, Object> stats) {
        this.stats = stats;
    }

    public Map<String, Collection<Long>> getCurrentItemsMap() {
        return currentItemsMap;
    }

    public void setCurrentItemsMap(Map<String, Collection<Long>> currentItemsMap) {
        this.currentItemsMap = currentItemsMap;
    }

}
