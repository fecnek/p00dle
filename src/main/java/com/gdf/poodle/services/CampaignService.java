package com.gdf.poodle.services;

import java.util.List;

import com.gdf.poodle.controllers.forms.CampaignData;

public interface CampaignService {

	public abstract void storeCampaign(CampaignData campaignData,
			String systemUserName);

	public abstract CampaignData fetchCampaign(Long campaignId);

	public abstract void removeCampaign(Long campaignId);

	public abstract List<CampaignData> getCampaignList(String systemUserName);

}