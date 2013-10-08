package com.digitalrpg.domain.dao.hibernate;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.digitalrpg.domain.dao.CampaignDao;
import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.User;

public class CampaignDaoImpl implements CampaignDao {

	private SessionFactory sessionFactory;
	
	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public void createCampaign(String name, String description, User gm,
			Boolean isPublic) {
		Campaign campaign = new Campaign();
		campaign.setName(name);
		campaign.setDescription(description);
		campaign.setGameMaster(gm);
		campaign.setIsPublic(isPublic);
		sessionFactory.getCurrentSession().save(campaign);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public SortedSet<Campaign> getCampaignsForUser(final String user) {
		List<Campaign> campaigns = sessionFactory.getCurrentSession().createQuery("from Campaign c where c.gameMaster.name = :user")
			.setParameter("user", user).list();
		SortedSet<Campaign> sorted = new TreeSet<Campaign>(new Comparator<Campaign>() {

			public int compare(Campaign o1, Campaign o2) {
				boolean isGMof1 = user.equals(o1.getGameMaster().getName());
				boolean isGMof2 = user.equals(o2.getGameMaster().getName());
				if(isGMof1 && !isGMof2) return -1;
				if(isGMof2 && !isGMof1) return 1;
				
				return o1.getName().compareTo(o2.getName());
			}
		});
		sorted.addAll(campaigns);
		return sorted;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Campaign get(Long id) {
		List<Campaign> campaigns = sessionFactory.getCurrentSession().createQuery("from Campaign where id = ?")
			.setParameter(0, id).list();
		if(campaigns.size() == 1) {
			return campaigns.iterator().next();
		}
		return null;
	}

	@Transactional(readOnly = true)
	public Collection<Campaign> search(String searchString, int offset, int limit) {
		// TODO Auto-generated method stub
		List list = sessionFactory.getCurrentSession()
			.createQuery("from Campaign c where c.isPublic = true and (c.name like :searchString or c.description like :searchString) order by c.name")
			.setParameter("searchString", "%" + searchString + "%")
			.setFirstResult(offset)
			.setMaxResults(limit)
			.list();
		return list;
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	public void addPlayerCharacter(Long campaignd, Long playerCharacterId) {
		// TODO Auto-generated method stub

	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
