package com.digitalrpg.domain.model;

import java.util.Arrays;
import java.util.SortedSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "users")
public class User {
	
	private Long id;
	
	private String name;
	
	private String password;
	
	private Boolean active;
	
	private String activationToken;
	
	private String email;
	
	private SubscriptionType subscriptionType;
	
	private SortedSet<RecentItem> recentItems;
	
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
	
	@Column(unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getActivationToken() {
		return activationToken;
	}

	public void setActivationToken(String activationToken) {
		this.activationToken = activationToken;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public SubscriptionType getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(SubscriptionType subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, Arrays.asList("id"));
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, Arrays.asList("id"));
	}

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,  fetch = FetchType.EAGER, mappedBy = "user")
	@Sort(type = SortType.NATURAL)
	public SortedSet<RecentItem> getRecentItems() {
		return recentItems;
	}

	public void setRecentItems(SortedSet<RecentItem> recentItems) {
		this.recentItems = recentItems;
	}
	
}
