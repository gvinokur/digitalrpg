package com.digitalrpg.domain.model.characters;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.digitalrpg.domain.model.User;

/**
 * 
 * @author gvinokur
 * 
 */
@Entity
@Table(name = "characters")
public class Character {

    public enum CharacterType {
        PC, NPC, COMPANION;
    }

    private Long id;

    private String name;

    private int level = 1;

    private String pictureUrl;

    private String bio;

    private Boolean publicBio;

    private User createdBy;

    private User owner;

    private CharacterType characterType;

    private String webBioUrl;

    private Boolean publicWebBio;

    private String notes;

    private Set<String> links;


    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean belongsTo(User user) {
        return owner.equals(user);
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_id", referencedColumnName = "id")
    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public CharacterType getCharacterType() {
        return characterType;
    }

    public void setCharacterType(CharacterType characterType) {
        this.characterType = characterType;
    }

    @Column(columnDefinition = "TEXT")
    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Boolean getPublicBio() {
        return publicBio;
    }

    public void setPublicBio(Boolean publicBio) {
        this.publicBio = publicBio;
    }

    public String getWebBioUrl() {
        return webBioUrl;
    }

    public void setWebBioUrl(String webBioUrl) {
        this.webBioUrl = webBioUrl;
    }

    public Boolean getPublicWebBio() {
        return publicWebBio;
    }

    public void setPublicWebBio(Boolean publicWebBio) {
        this.publicWebBio = publicWebBio;
    }

    @Column(columnDefinition = "TEXT")
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    public Set<String> getLinks() {
        return links;
    }

    protected void setLinks(Set<String> links) {
        this.links = links;
    }

    public void setLinks(List<String> links) {
        if (this.links == null) {
            this.links = new HashSet<>();
        } else {
            this.links.clear();
        }
        this.links.addAll(links);
    }



}
