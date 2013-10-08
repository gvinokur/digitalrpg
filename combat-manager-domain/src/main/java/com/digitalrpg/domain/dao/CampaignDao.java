package com.digitalrpg.domain.dao;

import java.util.Collection;
import java.util.Set;
import java.util.SortedSet;

import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.User;

public interface CampaignDao {

	/**
	 * Creates a campaign
	 * 
	 * @param name
	 * @param description
	 * @param gm
	 * @param isPublic
	 */
	public void createCampaign(String name, String description, User gm, Boolean isPublic);

	/**
	 * Returns a Set of campaigns in which the user is either a GM or has a player on it.
	 * 
	 * @param user
	 * @return
	 */
	public SortedSet<Campaign> getCampaignsForUser(String user);
	
	/**
	 * Get Campaign by id
	 * 
	 * @param id
	 * @return
	 */
	public Campaign get(Long id);
	
	/**
	 * Returns a Set of campaigns based on the search criteria
	 * 
	 * @param searchString
	 * @return
	 */
	public Collection<Campaign> search(String searchString, int offset, int limit);
	
	/**
	 * Adds a player character to a campaign
	 * 
	 * @param campaignd
	 * @param playerCharacterId
	 */
	public void addPlayerCharacter(Long campaignd, Long playerCharacterId);
	
}
