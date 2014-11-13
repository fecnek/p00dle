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
import com.gdf.poodle.repositories.entities.Campaign;
import com.gdf.poodle.repositories.entities.Question;
import com.gdf.poodle.repositories.entities.Survey;
import com.gdf.poodle.repositories.entities.questiontypes.YesNoQuestion;
import com.gdf.poodle.repositories.exceptions.CampaignDoesNotExists;
import com.gdf.poodle.services.exceptions.SurveyException;

@Repository
@Slf4j
public class SurveyRepositoryImplementation implements SurveyRepository  {
	@Autowired
	PersistenceManager persistenceManager;
	
	@Autowired
	CampaignRepository campaignRepository;
	

	/* (non-Javadoc)
	 * @see com.gdf.poodle.repositories.SurveyRepository#storeSurvey(com.gdf.poodle.repositories.entities.Survey, java.lang.Long)
	 */
	@Override
	public void storeSurvey(Survey surveyData, Long campaignId) {
		Optional<Campaign> fetchedCampaign = campaignRepository.fetchCampaign(campaignId);
		if (!fetchedCampaign.isPresent()) {
			throw new CampaignDoesNotExists();
		}
		Campaign campaign = fetchedCampaign.get();
		if (campaign.getSurveys() == null ) {
			campaign.setSurveys(new ArrayList<>());
		}
		campaign.getSurveys().add(surveyData);
		surveyData.setCampaign(campaign);
		
		try {
			for (Question question : surveyData.getQuestions()) {
				if (question instanceof YesNoQuestion) {
					persistenceManager.merge((YesNoQuestion)question);
				}
			}
			persistenceManager.merge(surveyData);
			persistenceManager.flush();
		} catch (IllegalArgumentException | IllegalAccessException
				| IdFieldNotFoundException | NoResultException
				 | FileNotFoundException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new SurveyException();
		}
		
	}
	
	
	/* (non-Javadoc)
	 * @see com.gdf.poodle.repositories.SurveyRepository#fetchSurvey(java.lang.Long)
	 */
	@Override
	public Optional<Survey> fetchSurvey(Long surveyId) {
		Optional<Survey> result = Optional.empty();
		try {
			result = Optional.of(persistenceManager.find(Survey.class, surveyId));			
		} catch (NoResultException e) {
			log.error(e.getMessage());
		}
		return result;
	}
	
	
	/* (non-Javadoc)
	 * @see com.gdf.poodle.repositories.SurveyRepository#removeSurvey(java.lang.Long)
	 */
	@Override
	public void removeSurvey(Long surveyId) {
		Optional<Survey> survey = fetchSurvey(surveyId);
		if (survey.isPresent()) {
			try {
				persistenceManager.remove(survey.get());
				persistenceManager.flush();

			} catch (IllegalArgumentException | IllegalAccessException
					| NoResultException | IdFieldNotFoundException | FileNotFoundException e) {
				log.error(e.getMessage());
			}
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.gdf.poodle.repositories.SurveyRepository#getSurveyList(java.lang.Long)
	 */
	@Override
	public List<Survey> getSurveyList(Long campaignId) {
		return persistenceManager.select(Survey.class, (Survey survey) -> survey.getCampaign() != null &&
				survey.getCampaign().getId().equals(campaignId));
	}
	
}
