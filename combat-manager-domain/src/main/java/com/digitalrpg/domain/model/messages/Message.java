package com.digitalrpg.domain.model.messages;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.digitalrpg.domain.model.User;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Message {

    private Long id;

    private User from;

    private User to;

    private String toMail;

    private Date createdDate = new Date();

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_id", referencedColumnName = "id")
    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_id", referencedColumnName = "id")
    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public String getToMail() {
        return toMail;
    }

    public void setToMail(String toMail) {
        this.toMail = toMail;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Check if the message requires for a separate message to be sent to the original requester
     * when accepted
     * 
     * @return
     */
    @Transient
    public abstract boolean sendMessageOnAccept();

}
