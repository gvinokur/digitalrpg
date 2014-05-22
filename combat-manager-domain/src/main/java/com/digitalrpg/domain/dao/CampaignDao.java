package com.digitalrpg.domain.dao;

import java.util.Collection;
import java.util.List;

import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.SystemType;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.domain.model.characters.SystemCharacter;

public interface CampaignDao {

	/**
	 * Creates a campaign
	 * 
	 * @param name
	 * @param description
	 * @param gm
	 * @param isPublic
	 * @param system 
	 */
	public Campaign createCampaign(String name, String description, User gm, Boolean isPublic, SystemType system);

	/**
	 * Returns a List of campaigns in which the user is either a GM or has a player on it.
	 * 
	 * @param user
	 * @return
	 */
	public List<Campaign> getCampaignsForUser(User user);
	
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
	 * @param character
	 */
	public void addPlayerCharacter(Long campaignd, SystemCharacter character);

	/**
	 * Adds the user to the campaign member list
	 * @param id
	 * @param user
	 */
	public void joinCampaign(Long id, User user);
	
}
