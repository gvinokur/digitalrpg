package com.digitalrpg.domain.model.messages;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.digitalrpg.domain.model.Campaign;

@Entity
@Table(name = "campaign_invites")
public class InviteToCampaignMessage extends Message {

    public Campaign campaign;

    @ManyToOne
    @JoinColumn(name = "campaign_id", referencedColumnName = "id", updatable = false)
    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    @Override
    @Transient
    public boolean sendMessageOnAccept() {
        return false;
    }

}
