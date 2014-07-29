package com.digitalrpg.web.controller.model.status;

public class CombatCharacterStatusVO implements Comparable<CombatCharacterStatusVO> {

    private String name;

    private Long id;

    private Long order;

    private Boolean hidden;

    private String currentAction;

    private Boolean editable;

    @Override
    public int compareTo(CombatCharacterStatusVO o) {
        // TODO refactor this
        if (this.getOrder() == null) {
            if (o == null) {
                return 0;
            } else {
                return -1;
            }
        }
        if (o == null || o.getOrder() == null)
            return 1;
        return this.getOrder().compareTo(o.getOrder());
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getCurrentAction() {
        return currentAction;
    }


    public void setCurrentAction(String currentAction) {
        this.currentAction = currentAction;
    }


    public Boolean getEditable() {
        return editable;
    }


    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

}
