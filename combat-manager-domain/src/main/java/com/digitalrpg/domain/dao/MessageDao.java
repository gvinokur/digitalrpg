package com.digitalrpg.domain.dao;

import java.util.Collection;
import java.util.Date;

import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.domain.model.messages.Message;

public interface MessageDao {

	/**
	 * Check for messages that have the mailTo as the user mail and no userTo and set
	 * the user as the input user.
	 * 
	 * @param user
	 */
	public void assignOrphanMessages(User user);

	/**
	 * Gets all user messages if there is any message after the selected date.<br>
	 * if date is null, returns all messages or an empty collection if there are no messages.<br>
	 * if data is not null: <br>
	 *  If there are no new messages, returns null. <br>
	 *  If there are new messages, returns the list of messages. <br>
	 * @param user
	 * @param date
	 * @return <br> 
	 * Collection of messages: Always the collection of all messages. <br>
	 * Empty Collection: When there are no messages at all. <br>
	 * null: When there are no new messages. <br>  
	 */
	public Collection<Message> getUserMessages(User user, Date date);
	
	public void deleteMessage(Long id);

	public Message invite(User from, String to, User userTo, Campaign campaign);

	public Message get(Long id);

	public Message requestJoin(User user, User gameMaster, Campaign campaign);

	public void acceptRequest(User user, User from, Campaign campaign);

	public Message invite(Long id, User from, String emailTo, User userTo,
			SystemCharacter character);
}
