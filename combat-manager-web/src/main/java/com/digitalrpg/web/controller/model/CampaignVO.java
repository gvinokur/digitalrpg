package com.digitalrpg.web.controller.model;

import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.SystemType;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.web.service.UserService;

public class CampaignVO {

    private Long id;

    private String name;

    private String description;

    private UserVO gameMaster;

    private SystemType system;

    public void fromCampaign(Campaign campaign) {
        this.id = campaign.getId();
        this.name = campaign.getName();
        this.description = campaign.getDescription();
        this.gameMaster = UserService.userToVOFuction.apply(campaign.getGameMaster());
        this.system = campaign.getSystem();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public UserVO getGameMaster() {
        return gameMaster;
    }


    public Long getId() {
        return id;
    }

    public SystemType getSystem() {
        return system;
    }


}
