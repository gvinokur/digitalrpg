package com.digitalrpg.domain.dao.hibernate;

import java.util.Collection;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.digitalrpg.domain.dao.MessageDao;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.domain.model.messages.Message;

public class MessageDaoImpl implements MessageDao {

	private SessionFactory sessionFactory;
	
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public void assignOrphanMessages(User user) {
		List<Message> list = sessionFactory.getCurrentSession().createQuery("from Message m where m.to = null and m.toMail = ? ")
			.setParameter(0, user.getEmail()).list();
		for (Message message : list) {
			message.setTo(user);
			sessionFactory.getCurrentSession().update(message);
		}
	}

	@Transactional(readOnly = true)
	public Collection<Message> getUserMessages(User user) {
		return sessionFactory.getCurrentSession().createQuery("from Message m where m.to = :user order by m.createdDate")
				.setParameter("user", user).list();
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public void deleteMessage(Long id) {
		// TODO Auto-generated method stub
		
	}

}
