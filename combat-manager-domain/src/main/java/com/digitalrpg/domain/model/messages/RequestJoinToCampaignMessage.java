package com.digitalrpg.domain.model.messages;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.digitalrpg.domain.model.Campaign;

@Entity
@Table(name = "campaign_join_requests")
public class RequestJoinToCampaignMessage extends Message {

    private Campaign campaign;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "campaign_id", referencedColumnName = "id")
    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    @Override
    @Transient
    public boolean sendMessageOnAccept() {
        return true;
    }

}
