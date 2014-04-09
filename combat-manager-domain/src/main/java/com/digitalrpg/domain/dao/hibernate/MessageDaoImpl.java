package com.digitalrpg.domain.dao.hibernate;

import java.util.Collection;
import java.util.Date;
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
import com.google.common.collect.ImmutableList;

public class MessageDaoImpl extends HibernateDao implements MessageDao {

	public MessageDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@SuppressWarnings({ "unchecked" })
	@Transactional
	public Message invite(User from, String toMail, User toUser,
			Campaign campaign) {
		
		 List<Message> invites = this.sessionFactory.getCurrentSession()
				.createQuery("from InviteToCampaignMessage m where (m.to = :user or m.toMail = :mail) and m.campaign = :campaign")
				.setParameter("user", toUser)
				.setParameter("mail", toMail)
				.setParameter("campaign", campaign)
				.list();
		
		 if(!invites.isEmpty()) {
			 return invites.get(0);
		 }
		
		InviteToCampaignMessage message = new InviteToCampaignMessage();
		message.setCampaign(campaign);
		message.setFrom(from);
		message.setToMail(toMail);
		message.setTo(toUser);
		this.sessionFactory.getCurrentSession().save(message);
		return message;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public Message requestJoin(User from, User to, Campaign campaign) {
		
		List<Message> invites = this.sessionFactory.getCurrentSession()
				.createQuery("from RequestJoinToCampaignMessage m where m.from = :user and m.campaign = :campaign")
				.setParameter("user", from)
				.setParameter("campaign", campaign)
				.list();
		
		 if(!invites.isEmpty()) {
			 return invites.get(0);
		 }
		 
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
	public Collection<Message> getUserMessages(User user, Date date) {
		Object[] row = (Object[]) sessionFactory.getCurrentSession()
			.createQuery("Select count(*), max(m.createdDate) from Message m where m.to = :user ")
			.setParameter("user", user)
			.iterate().next();
		
		if(Long.valueOf(0).equals(row[0])) {
			return ImmutableList.of();
		}
		
		if(date != null) {
			if(date.after((Date) row[1])) {
				return null;
			}
		}
		
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
