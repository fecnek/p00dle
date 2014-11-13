package com.gdf.poodle.repositories;

import java.util.List;
import java.util.Optional;

import com.gdf.poodle.repositories.entities.Survey;

public interface SurveyRepository {

	public abstract void storeSurvey(Survey surveyData, Long campaignId);

	public abstract Optional<Survey> fetchSurvey(Long surveyId);

	public abstract void removeSurvey(Long surveyId);

	public abstract List<Survey> getSurveyList(Long campaignId);

}