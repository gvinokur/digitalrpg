package com.digitalrpg.domain.dao.hibernate;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.digitalrpg.domain.dao.UserDao;
import com.digitalrpg.domain.model.User;

public class UserDaoImpl extends HibernateDao implements UserDao {

	public UserDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	public User createUser(String name, String password, String email) {
		String sha1HexPassword = DigestUtils.sha1Hex(password);
		String activationToken = DigestUtils.sha1Hex(name + sha1HexPassword);
		User user = new User();
		user.setName(name);
		user.setPassword(sha1HexPassword);
		user.setEmail(email);
		user.setActive(false);
		user.setActivationToken(activationToken);
		sessionFactory.getCurrentSession()
			.save(user);
		return user;
	}

	@SuppressWarnings("unchecked")
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void activateUser(String name, String activationToken) {
		List<User> list = sessionFactory.getCurrentSession()
			.createQuery("from User where name = ?")
			.setParameter(0, name)
			.list();
		
		if(list.isEmpty()) {
			//TODO: Throw some kind of invalid user exception
		} else {
			User user = list.iterator().next();
			if(user.getActive()) {
				//TODO: Throw already active or ignore request?
			} else if(user.getActivationToken().equals(activationToken)) {
				user.setActive(true);
				sessionFactory.getCurrentSession().update(user);
			} else {
				//TODO: Throw invalid activationToken
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public User login(String name, String password) {
		List<User> list = sessionFactory.getCurrentSession()
				.createQuery("from User where name = ?")
				.setParameter(0, name)
				.list();
		
		if(list.isEmpty()) {
			//TODO: Throw some kind of invalid user exception
		} else {
			String sha1HexPassword = DigestUtils.sha1Hex(password);
			User user = list.iterator().next();
			if(user.getPassword().equals(sha1HexPassword)) {
				if(user.getActive()) {
					return user;
				} else {
					//TODO: Throw Inactive user
				}
			} else {
				//TODO: Throw InvalidPassword
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Boolean checkUsername(String name) {
		List<User> list = sessionFactory.getCurrentSession()
				.createQuery("from User where name = ?")
				.setParameter(0, name)
				.list();
		return list.isEmpty();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public User findByMail(String emailTo) {
		List<User> list = sessionFactory.getCurrentSession()
				.createQuery("from User where email = ?")
				.setParameter(0, emailTo)
				.list();
		if(list.size() > 0) {
			return list.iterator().next();
		}
		return null;
	}

	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void updateUser(User user) {
		this.sessionFactory.getCurrentSession().update(user);
	}



}
