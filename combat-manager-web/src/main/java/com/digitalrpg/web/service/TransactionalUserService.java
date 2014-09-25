package com.digitalrpg.web.service;

import java.util.Date;
import java.util.SortedSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.digitalrpg.domain.dao.UserDao;
import com.digitalrpg.domain.model.RecentItem;
import com.digitalrpg.domain.model.User;
import com.google.common.collect.Sets;

public class TransactionalUserService {

    private static int MAX_ITEMS_SAVED = 8;

    @Autowired
    private UserDao userDao;

    @Transactional
    public void trackItem(User user, String url, String title) {
        RecentItem recentItem = new RecentItem();
        recentItem.setUrl(url);
        recentItem.setTitle(title);
        recentItem.setUser(user);
        recentItem.setDate(new Date());
        user = userDao.get(user.getName());
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


    @Transactional
    public SortedSet<RecentItem> getRecentItems(User user) {
        return Sets.newTreeSet(userDao.get(user.getName()).getRecentItems());
    }

}
