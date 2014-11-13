package com.gdf.poodle.services;

import java.util.List;

import com.gdf.poodle.controllers.forms.SurveyData;

public interface SurveyService {

	public abstract void storeSurvey(SurveyData surveyData, Long campaignId);

	public abstract SurveyData fetchSurvey(Long surveyId);

	public abstract void removeSurvey(Long surveyId);

	public abstract List<SurveyData> getSurveyList(Long campaignId);

	public abstract void sendSurvey(Long surveyId, String systemUserName);

}