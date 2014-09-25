package com.digitalrpg.domain.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.annotations.Type;

import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSortedSet;

@Entity
@Table(name = "combats")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Combat<ACTION_TYPE extends SystemAction> {

    private Long id;

    private CombatState state;

    private String name;

    private String description;

    private Campaign campaign;

    private Set<CombatCharacter<ACTION_TYPE>> combatCharacters;

    private CombatCharacter<ACTION_TYPE> currentCharacter;

    private SortedSet<CombatLog> combatLogs;

    private Date lastUpdated;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = CombatCharacter.class)
    @JoinColumn(name = "current_character_id", referencedColumnName = "id")
    public CombatCharacter<ACTION_TYPE> getCurrentCharacter() {
        return currentCharacter;
    }

    public void setCurrentCharacter(CombatCharacter<ACTION_TYPE> currentCharacter) {
        this.currentCharacter = currentCharacter;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "campaign_id", referencedColumnName = "id")
    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "combat", targetEntity = CombatCharacter.class, cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Sort(comparator = CombatCharacterComparator.class, type = SortType.COMPARATOR)
    public Set<CombatCharacter<ACTION_TYPE>> getCombatCharacters() {
        return this.combatCharacters;
    }

    public void setCombatCharacters(Set<CombatCharacter<ACTION_TYPE>> combatCharacters) {
        this.combatCharacters = combatCharacters;
    }

    @Id
    @Type(type = "long")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    /**
     * This method should advance turns, rounds or whatever the system uses and make sure the state
     * of the combat is updated (current character, character status, etc)
     * 
     * @return
     */
    public abstract void advance(List<ACTION_TYPE> availableActions);

    /**
     * This method should undo turns, rounds or whatever the system uses and make sure the state of
     * the combat is updated (current character, character status, etc)
     * 
     * @return
     */
    public abstract void back(List<ACTION_TYPE> list);

    @Transient
    public NavigableSet<CombatCharacter<ACTION_TYPE>> getCombatCharactersAsNavigableSet() {
        SortedSet<CombatCharacter<ACTION_TYPE>> ss = (SortedSet<CombatCharacter<ACTION_TYPE>>) this.combatCharacters;
        return ImmutableSortedSet.copyOfSorted(ss);
    }

    public CombatState getState() {
        return state;
    }

    public void setState(CombatState state) {
        this.state = state;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "combat", cascade = CascadeType.ALL)
    @Sort(comparator = CombatLogComparator.class, type = SortType.COMPARATOR)
    public SortedSet<CombatLog> getCombatLogs() {
        return combatLogs;
    }

    public void setCombatLogs(SortedSet<CombatLog> combatLogs) {
        this.combatLogs = combatLogs;
    }

    @Transient
    public Collection<SystemCharacter> getRemainingCharacters() {
        return Collections2.filter(campaign.getCharacters(), new Predicate<SystemCharacter>() {
            @Override
            public boolean apply(SystemCharacter input) {
                for (CombatCharacter<ACTION_TYPE> cc : Combat.this.combatCharacters) {
                    if (cc.getCharacter().equals(input)) {
                        return false;
                    }
                }
                return true;
            }
        });
    }

    @Transient
    public abstract String getContextDescription();

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
