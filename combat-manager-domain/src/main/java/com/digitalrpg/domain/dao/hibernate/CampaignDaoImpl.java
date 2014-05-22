package com.digitalrpg.domain.dao.hibernate;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.digitalrpg.domain.dao.CampaignDao;
import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.SystemType;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.domain.model.characters.SystemCharacter;

public class CampaignDaoImpl extends HibernateDao implements CampaignDao {

	public CampaignDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public Campaign createCampaign(String name, String description, User gm,
			Boolean isPublic, SystemType system) {
		Campaign campaign = new Campaign();
		campaign.setName(name);
		campaign.setDescription(description);
		campaign.setGameMaster(gm);
		campaign.setIsPublic(isPublic);
		campaign.setSystem(system);
		sessionFactory.getCurrentSession().save(campaign);
		return campaign;
	}
	
	@Override
	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public void joinCampaign(Long id, User user) {
		user = (User) this.sessionFactory.getCurrentSession().merge(user);
		Campaign campaign = this.get(id);
		campaign.addMember(user);
		this.sessionFactory.getCurrentSession().save(campaign);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Campaign> getCampaignsForUser(User user) {
//		user = (User) this.sessionFactory.getCurrentSession().merge(user);
		List<Campaign> campaignsAsPlayer = sessionFactory.getCurrentSession().createQuery("select distinct c from Campaign c where :user in elements(c.members)")
			.setParameter("user", user).list();
		List<Campaign> campaignsAsGameMaster = sessionFactory.getCurrentSession().createQuery("select c from Campaign c where c.gameMaster = :user")
				.setParameter("user", user).list();
		List<Campaign> sorted = new LinkedList<Campaign>();
		sorted.addAll(campaignsAsGameMaster);
		sorted.addAll(campaignsAsPlayer);
		return sorted;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Campaign get(Long id) {
		List<Campaign> campaigns = sessionFactory.getCurrentSession().createQuery("from Campaign where id = ?")
			.setParameter(0, id).list();
		if(campaigns.size() == 1) {
			Campaign campaign = campaigns.iterator().next();
			campaign.getPendingInvitations();
			campaign.getPendingRequest();
			return campaign;
		}
		return null;
	}

	@Transactional(readOnly = true)
	public Collection<Campaign> search(String searchString, int offset, int limit) {
		// TODO Auto-generated method stub
		List list = sessionFactory.getCurrentSession()
			.createQuery("from Campaign c where c.isPublic = true and (upper(c.name) like :searchString or upper(c.description) like :searchString) order by c.name")
			.setParameter("searchString", "%" + searchString.toUpperCase() + "%")
			.setFirstResult(offset)
			.setMaxResults(limit)
			.list();
		
		return list;
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	public void addPlayerCharacter(Long id, SystemCharacter character) {
		Campaign campaign = get(id);
		Set<SystemCharacter> playerCharacters = campaign.getCharacters();
		if(playerCharacters == null) {
			playerCharacters = new HashSet<SystemCharacter>();
			campaign.setCharacters(playerCharacters);
		}
		playerCharacters.add(character);
		sessionFactory.getCurrentSession().save(campaign);
	}

}
