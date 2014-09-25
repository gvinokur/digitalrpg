package com.digitalrpg.domain.model;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "combat_logs")
public class CombatLog {

    private Long id;

    @JsonIgnore
    private Combat<? extends SystemAction> combat;

    private Long order;

    private String combatContextDescription;

    private String log;

    public CombatLog() {}

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "combat_id", referencedColumnName = "id")
    public Combat<? extends SystemAction> getCombat() {
        return combat;
    }

    public void setCombat(Combat<? extends SystemAction> combat) {
        this.combat = combat;
    }

    @Column(name = "LOG_ORDER")
    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public String getCombatContextDescription() {
        return combatContextDescription;
    }

    public void setCombatContextDescription(String combatContextDescription) {
        this.combatContextDescription = combatContextDescription;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

}
