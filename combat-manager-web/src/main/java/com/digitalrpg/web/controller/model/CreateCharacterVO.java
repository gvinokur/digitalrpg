package com.digitalrpg.web.controller.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import com.digitalrpg.domain.model.User;
import com.digitalrpg.domain.model.characters.Character.CharacterType;
import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.domain.model.characters.SystemProperties;
import com.google.common.collect.Lists;

public abstract class CreateCharacterVO {

    @NotEmpty
    private String name;

    private String bio;

    private Boolean isBioPublic;

    private String webBio;

    private Boolean isWebBioPublic;

    private String notes;

    private String pictureUrl;

    private Long campaignId;

    private Long messageId;

    private User owner;

    private Long id;

    private List<String> additionalResources;

    private CharacterType characterType;

    public CreateCharacterVO(SystemCharacter systemCharacter) {
        this.name = systemCharacter.getCharacter().getName();
        this.bio = systemCharacter.getCharacter().getBio();
        this.isBioPublic = systemCharacter.getCharacter().getPublicBio();
        this.webBio = systemCharacter.getCharacter().getWebBioUrl();
        this.isWebBioPublic = systemCharacter.getCharacter().getPublicWebBio();
        this.notes = systemCharacter.getCharacter().getNotes();
        this.pictureUrl = systemCharacter.getCharacter().getPictureUrl();
        this.campaignId = systemCharacter.getCampaign().getId();
        this.owner = systemCharacter.getCharacter().getOwner();
        this.id = systemCharacter.getId();
        this.additionalResources = Lists.newArrayList(systemCharacter.getCharacter().getLinks());
        this.characterType = systemCharacter.getCharacter().getCharacterType();
    }

    public CreateCharacterVO() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public abstract SystemProperties getSystemProperties();

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Boolean getIsBioPublic() {
        return isBioPublic;
    }

    public void setIsBioPublic(Boolean isBioPublic) {
        this.isBioPublic = isBioPublic;
    }

    public String getWebBio() {
        return webBio;
    }

    public void setWebBio(String webBio) {
        this.webBio = webBio;
    }

    public Boolean getIsWebBioPublic() {
        return isWebBioPublic;
    }

    public void setIsWebBioPublic(Boolean isWebBioPublic) {
        this.isWebBioPublic = isWebBioPublic;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<String> getAdditionalResources() {
        return additionalResources;
    }

    public String getAdditionalResourcesString() {
        return StringUtils.join(this.additionalResources, ",");
    }

    public void setAdditionalResources(List<String> additionalResources) {
        this.additionalResources = additionalResources;
    }

    public CharacterType getCharacterType() {
        return characterType;
    }

    public void setCharacterType(CharacterType characterType) {
        this.characterType = characterType;
    }

}
