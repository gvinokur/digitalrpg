package com.digitalrpg.domain.model.messages;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.digitalrpg.domain.model.characters.SystemCharacter;

@Entity
@Table(name = "character_invites")
public class InviteToClaimCharacterMessage extends Message {

    public SystemCharacter character;

    @ManyToOne
    @JoinColumn(name = "character_id", referencedColumnName = "id", updatable = false)
    public SystemCharacter getCharacter() {
        return character;
    }

    public void setCharacter(SystemCharacter character) {
        this.character = character;
    }

    @Transient
    public boolean sendMessageOnAccept() {
        return false;
    }

}
