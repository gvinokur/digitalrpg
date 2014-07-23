package com.digitalrpg.web.controller.model;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.digitalrpg.domain.model.Combat;
import com.digitalrpg.domain.model.CombatCharacter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class CreateCombatVO {

    private Long campaignId;

    private Long id;

    @NotEmpty
    private String name;

    private String description;

    private List<Long> players;

    @Valid
    private Map<Long, PlayerExtraInfoVO> extraInfo;

    public CreateCombatVO(Combat<?> combat) {
        this.campaignId = combat.getCampaign().getId();
        this.id = combat.getId();
        this.name = combat.getName();
        this.description = combat.getDescription();
        this.players = Lists.newArrayList();
        this.extraInfo = Maps.newHashMap();
        for (CombatCharacter<?> cc : combat.getCombatCharacters()) {
            players.add(cc.getCharacter().getId());
            PlayerExtraInfoVO playerExtraInfoVO = new PlayerExtraInfoVO();
            playerExtraInfoVO.setHidden(cc.getHidden());
            extraInfo.put(cc.getCharacter().getId(), playerExtraInfoVO);
        }
    }

    public CreateCombatVO() {
        // TODO Auto-generated constructor stub
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getPlayers() {
        return players;
    }

    public void setPlayers(List<Long> players) {
        this.players = players;
    }

    public Map<Long, PlayerExtraInfoVO> getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(Map<Long, PlayerExtraInfoVO> extraInfo) {
        this.extraInfo = extraInfo;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
