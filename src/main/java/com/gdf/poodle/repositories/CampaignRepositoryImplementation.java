package com.gdf.poodle.repositories;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gdf.poodle.persistence.PersistenceManager;
import com.gdf.poodle.persistence.exceptions.IdFieldNotFoundException;
import com.gdf.poodle.persistence.exceptions.NoResultException;
import com.gdf.poodle.persistence.exceptions.UserException;
import com.gdf.poodle.repositories.entities.Campaign;
import com.gdf.poodle.repositories.entities.SystemUser;
import com.gdf.poodle.repositories.exceptions.SystemUserDoesNotExists;

@Repository
@Slf4j
public class CampaignRepositoryImplementation implements CampaignRepository {
	@Autowired
	PersistenceManager persistenceManager;
	
	@Autowired
	SystemUserRepository systemUserRepository;
	

	/* (non-Javadoc)
	 * @see com.gdf.poodle.repositories.CampaignRepository#storeCampaign(com.gdf.poodle.repositories.entities.Campaign, java.lang.String)
	 */
	@Override
	public void storeCampaign(Campaign campaignData, String systemUserName) {
		Optional<SystemUser> systemUser = systemUserRepository.findUserByName(systemUserName);
		if (!systemUser.isPresent()) {
			throw new SystemUserDoesNotExists();
		}
		SystemUser user = systemUser.get();
		if (user.getCampaigns() == null ) {
			user.setCampaigns(new ArrayList<>());
		}
		user.getCampaigns().add(campaignData);
		campaignData.setOwner(user);
		
		try {
			persistenceManager.merge(campaignData);
			persistenceManager.flush();
		} catch (IllegalArgumentException | IllegalAccessException
				| IdFieldNotFoundException | NoResultException
				 | FileNotFoundException e) {
			throw new UserException();
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.gdf.poodle.repositories.CampaignRepository#fetchCampaign(java.lang.Long)
	 */
	@Override
	public Optional<Campaign> fetchCampaign(Long campaignId) {
		Optional<Campaign> result = Optional.empty();
		try {
			result = Optional.of(persistenceManager.find(Campaign.class, campaignId));			
		} catch (NoResultException e) {
			log.error(e.getMessage());
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.gdf.poodle.repositories.CampaignRepository#removeCampagin(java.lang.Long)
	 */
	@Override
	public void removeCampaign(Long campaignId) {
		Optional<Campaign> campaign = fetchCampaign(campaignId);
		if (campaign.isPresent()) {
			try {
				persistenceManager.remove(campaign.get());
				persistenceManager.flush();

			} catch (IllegalArgumentException | IllegalAccessException
					| NoResultException | IdFieldNotFoundException | FileNotFoundException e) {
				log.error(e.getMessage());
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.gdf.poodle.repositories.CampaignRepository#getCampaignList(java.lang.String)
	 */
	@Override
	public List<Campaign> getCampaignList(String systemUserName) {
		return persistenceManager.select(Campaign.class, (Campaign campaign) -> campaign.getOwner() != null &&
				campaign.getOwner().getName().equals(systemUserName));
	}
	
}
