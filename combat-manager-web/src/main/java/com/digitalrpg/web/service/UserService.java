package com.digitalrpg.web.service;

import java.util.Date;
import java.util.SortedSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.digitalrpg.domain.dao.UserDao;
import com.digitalrpg.domain.model.RecentItem;
import com.digitalrpg.domain.model.User;
import com.google.common.collect.ImmutableList;

public class UserService implements UserDetailsService {

    private static int MAX_ITEMS_SAVED = 8;

    @Autowired
    private UserDao userDao;

    public void trackItem(User user, String url, String title) {
        RecentItem recentItem = new RecentItem();
        recentItem.setUrl(url);
        recentItem.setTitle(title);
        recentItem.setUser(user);
        recentItem.setDate(new Date());
        SortedSet<RecentItem> recentItems = user.getRecentItems();
        if (recentItems.contains(recentItem)) {
            recentItems.remove(recentItem);
        }
        recentItems.add(recentItem);
        while (recentItems.size() > MAX_ITEMS_SAVED) {
            recentItems.remove(recentItems.last());
        }
        userDao.updateUser(user);
    }

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
