package com.digitalrpg.web.service;

import java.util.Date;
import java.util.SortedSet;

import org.springframework.beans.factory.annotation.Autowired;

import com.digitalrpg.domain.dao.UserDao;
import com.digitalrpg.domain.model.RecentItem;
import com.digitalrpg.domain.model.User;

public class UserService {

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
		if(recentItems.contains(recentItem)) {
			recentItems.remove(recentItem);
		} 
		recentItems.add(recentItem);
		while(recentItems.size() > MAX_ITEMS_SAVED) {
			recentItems.remove(recentItems.last());
		}
		userDao.updateUser(user);
	}
}
