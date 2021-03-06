package com.digitalrpg.web.controller.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.digitalrpg.domain.model.SystemCombatItem;

public class CombatCharacterVO {

    private Long id;

    private String name;

    private String imageUrl;

    private List<Map<String, Object>> stats;

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

    public List<Map<String, Object>> getStats() {
        return stats;
    }

    public void setStats(List<Map<String, Object>> stats) {
        this.stats = stats;
    }

    public Map<String, Collection<Long>> getCurrentItemsMap() {
        return currentItemsMap;
    }

    public void setCurrentItemsMap(Map<String, Collection<Long>> currentItemsMap) {
        this.currentItemsMap = currentItemsMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
