package com.digitalrpg.domain.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "recent_items")
public class RecentItem implements Comparable<RecentItem>, Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String url;
	
	private String title;
	
	private User user;
	
	private Date date;

	@Id
	@Type(type = "long")
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id", referencedColumnName="id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.url).append(this.user).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof RecentItem) {
			final RecentItem other = (RecentItem) obj;
			return new EqualsBuilder().append(this.url, other.url).append(this.user, other.user).isEquals();
		}
		return false;
	}

	//Sorts by date starting from the newest one
	public int compareTo(RecentItem o) {
		if(this.equals(o)) return 0;
		return - this.getDate().compareTo(o.getDate());
	}
	
}
