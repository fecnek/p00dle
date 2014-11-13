package com.gdf.poodle.repositories;

import java.util.List;
import java.util.Optional;

import com.gdf.poodle.repositories.entities.Campaign;

public interface CampaignRepository {

	public abstract void storeCampaign(Campaign campaignData,
			String systemUserName);

	public abstract Optional<Campaign> fetchCampaign(Long campaignId);

	public abstract void removeCampaign(Long campaignId);

	public abstract List<Campaign> getCampaignList(String systemUserName);

}