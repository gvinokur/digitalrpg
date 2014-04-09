package com.digitalrpg.web.service;

import java.util.Collection;

import javax.persistence.Transient;

import org.eclipse.jetty.util.security.Credential.MD5;
import org.postgresql.util.MD5Digest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.digitalrpg.domain.model.User;
import com.google.common.collect.ImmutableList;

public class UserWrapper extends User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private User wrapped;
	
	public UserWrapper(User user) {
		this.setActivationToken(user.getActivationToken());
		this.setActive(user.getActive());
		this.setEmail(user.getEmail());
		this.setId(user.getId());
		this.setName(user.getName());
		this.setPassword(user.getPassword());
		this.setRecentItems(user.getRecentItems());
		this.setSubscriptionType(user.getSubscriptionType());
		this.wrapped = user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return ImmutableList.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getUsername() {
		return this.getName();
	}

	public User unwrap() {
		return wrapped;
	}
	
	@Transient
	public String getMd5Hash() {
		return MD5.digest(getEmail().toLowerCase()).substring(MD5.__TYPE.length());
	}

}
