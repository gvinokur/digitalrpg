package com.digitalrpg.web.controller.model;

import com.digitalrpg.domain.model.User;

public class MessageVO {

	private Long id;
	
	private User from;
	
	private User to;
	
	private String mailTo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
	}

	public String getMailTo() {
		return mailTo;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}
	
	public String getType() {
		return this.getClass().getSimpleName();
	}
	
}
