package com.digitalrpg.web.service;

import java.util.Date;
import java.util.SortedSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.digitalrpg.domain.dao.UserDao;
import com.digitalrpg.domain.model.RecentItem;
import com.digitalrpg.domain.model.User;

public class UserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    public User findByMail(String email) {
        return userDao.findByMail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("Cannot find user " + username);
        }
        return new UserWrapper(user);
    }

    public User get(String userToUsername) {
        return userDao.get(userToUsername);
    }

}
