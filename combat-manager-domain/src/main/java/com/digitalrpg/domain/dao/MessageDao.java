package com.digitalrpg.domain.dao;

import java.util.Collection;

import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.domain.model.messages.Message;

public interface MessageDao {

	/**
	 * Check for messages that have the mailTo as the user mail and no userTo and set
	 * the user as the input user.
	 * 
	 * @param user
	 */
	public void assignOrphanMessages(User user);
	
	public Collection<Message> getUserMessages(User user);
	
	public void deleteMessage(Long id);

	public Boolean invite(Long id, User from, String to, User userTo, Campaign campaign);

	public Message get(Long id);
}
