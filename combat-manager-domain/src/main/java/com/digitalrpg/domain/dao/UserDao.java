package com.digitalrpg.domain.dao;

import com.digitalrpg.domain.model.User;

public interface UserDao {

	/**
	 * Creates user and returns activation token
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	public User createUser(String name, String password, String email);
	
	/**
	 * Activates user with tokenCode
	 * 
	 * @param name
	 * @param activationToken
	 */
	public void activateUser(String name, String activationToken);
	
	/**
	 * Attempt login
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	public User login(String name, String password);

	/**
	 * Returns true if the username is available
	 * @param username
	 * @return
	 */
	public Boolean checkUsername(String username);

	public User findByMail(String emailTo);
	
	public void updateUser(User user);
	
}
