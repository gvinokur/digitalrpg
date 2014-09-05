package com.digitalrpg.web.controller.model;

import org.hibernate.validator.constraints.NotEmpty;

import com.digitalrpg.domain.model.SystemType;

public class CreateCampaignVO {

    private Long id;

    @NotEmpty
    private String name;

    private String description;

    private SystemType systemType;

    private Boolean isPublic;

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

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public SystemType getSystemType() {
        return systemType;
    }

    public void setSystemType(SystemType systemType) {
        this.systemType = systemType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
