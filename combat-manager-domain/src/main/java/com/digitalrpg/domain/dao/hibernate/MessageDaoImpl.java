package com.digitalrpg.domain.dao.hibernate;

import java.util.Collection;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.digitalrpg.domain.dao.MessageDao;
import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.domain.model.messages.AcceptRequestMessage;
import com.digitalrpg.domain.model.messages.InviteToCampaignMessage;
import com.digitalrpg.domain.model.messages.InviteToClaimCharacterMessage;
import com.digitalrpg.domain.model.messages.Message;
import com.digitalrpg.domain.model.messages.RequestJoinToCampaignMessage;

public class MessageDaoImpl extends HibernateDao implements MessageDao {

	public MessageDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Transactional
	public Message invite(Long id, User from, String toMail, User toUser,
			Campaign campaign) {
		InviteToCampaignMessage message = new InviteToCampaignMessage();
		message.setCampaign(campaign);
		message.setFrom(from);
		message.setToMail(toMail);
		message.setTo(toUser);
		this.sessionFactory.getCurrentSession().save(message);
		return message;
	}

	@Transactional
	public Message requestJoin(User from, User to, Campaign campaign) {
		RequestJoinToCampaignMessage message = new RequestJoinToCampaignMessage();
		message.setCampaign(campaign);
		message.setFrom(from);
		message.setTo(to);
		this.sessionFactory.getCurrentSession().save(message);
		return message;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public void assignOrphanMessages(User user) {
		List<Message> list = sessionFactory
				.getCurrentSession()
				.createQuery(
						"from Message m where m.to = null and m.toMail = ? ")
				.setParameter(0, user.getEmail()).list();
		for (Message message : list) {
			message.setTo(user);
			sessionFactory.getCurrentSession().update(message);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Collection<Message> getUserMessages(User user) {
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"from Message m where m.to = :user order by m.createdDate")
				.setParameter("user", user).list();
	}

	@SuppressWarnings("rawtypes")
	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public void deleteMessage(Long id) {

		List list = sessionFactory.getCurrentSession()
				.createQuery("from Message m where m.id = :id")
				.setParameter("id", id).list();
		for (Object object : list) {
			sessionFactory.getCurrentSession().delete(object);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Message get(Long id) {
		List<Message> list = sessionFactory.getCurrentSession()
				.createQuery("from Message m where m.id = :id")
				.setParameter("id", id).list();
		if(list.size() == 1) {
			return list.iterator().next();
		}
		
		return null;
	}

	@Transactional
	public void acceptRequest(User from, User to, Campaign campaign) {
		AcceptRequestMessage message = new AcceptRequestMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setCampaign(campaign);
		sessionFactory.getCurrentSession().save(message);
	}

	@Transactional
	public Message invite(Long id, User from, String toMail, User toUser,
			SystemCharacter character) {
		InviteToClaimCharacterMessage message = new InviteToClaimCharacterMessage();
		message.setCharacter(character);
		message.setFrom(from);
		message.setToMail(toMail);
		message.setTo(toUser);
		this.sessionFactory.getCurrentSession().save(message);
		return message;
	}


}
