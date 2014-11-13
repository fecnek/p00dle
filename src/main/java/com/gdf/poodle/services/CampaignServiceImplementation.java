package com.gdf.poodle.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdf.poodle.controllers.forms.CampaignData;
import com.gdf.poodle.repositories.CampaignRepository;
import com.gdf.poodle.repositories.entities.Campaign;
import com.gdf.poodle.services.exceptions.CampaignException;

@Service
public class CampaignServiceImplementation implements CampaignService {
	@Autowired
	CampaignRepository campaignRepository;
	
	
	/* (non-Javadoc)
	 * @see com.gdf.poodle.services.CampaignService#storeCampaign(com.gdf.poodle.controllers.forms.CampaignData, java.lang.String)
	 */
	@Override
	public void storeCampaign(@Valid CampaignData campaignData, @NotNull String systemUserName) {
		Campaign campaign = new Campaign();
		campaign.setId(campaignData.getId());
		campaign.setName(campaignData.getName());
		campaign.setPeriodStart(campaignData.getPeriodStart());
		campaign.setPeriodEnd(campaignData.getPeriodEnd());
		campaign.setSurveys(campaignData.getSurveys());
		
		campaignRepository.storeCampaign(campaign, systemUserName);
	}
	
	
	/* (non-Javadoc)
	 * @see com.gdf.poodle.services.CampaignService#fetchCampaign(java.lang.Long)
	 */
	@Override
	public CampaignData fetchCampaign(Long campaignId) {
		Optional<Campaign> campaign = campaignRepository.fetchCampaign(campaignId);
		if (campaign.isPresent()) {
			CampaignData campaignData = createCampaignData(campaign.get());
			
			return campaignData;
		}
		else {
			throw new CampaignException();
		}
	}

	private CampaignData createCampaignData(Campaign campaign) {
		CampaignData campaignData = new CampaignData();
		campaignData.setId(campaign.getId());
		campaignData.setName(campaign.getName());
		campaignData.setPeriodEnd(campaign.getPeriodEnd());
		campaignData.setPeriodStart(campaign.getPeriodStart());
		campaignData.setSurveys(campaign.getSurveys());
		
		return campaignData;
	}
	
	/* (non-Javadoc)
	 * @see com.gdf.poodle.services.CampaignService#removeCampaign(java.lang.Long)
	 */
	@Override
	public void removeCampaign(Long campaignId) {
		campaignRepository.removeCampaign(campaignId);
	}
	
	
	/* (non-Javadoc)
	 * @see com.gdf.poodle.services.CampaignService#getCampaignList(java.lang.String)
	 */
	@Override
	public List<CampaignData> getCampaignList(String systemUserName) {
		List<Campaign> campaignList = campaignRepository.getCampaignList(systemUserName);
		List<CampaignData> result = new ArrayList<>();
		for (Campaign campaign : campaignList) {
			CampaignData resultItem = createCampaignData(campaign);
			result.add(resultItem);
		}
		
		return result;
	}
}
