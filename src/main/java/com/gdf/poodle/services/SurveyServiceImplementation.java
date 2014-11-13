package com.gdf.poodle.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdf.poodle.controllers.forms.SurveyData;
import com.gdf.poodle.controllers.forms.UserData;
import com.gdf.poodle.controllers.forms.questions.YesNoQuestionData;
import com.gdf.poodle.repositories.SurveyRepository;
import com.gdf.poodle.repositories.entities.Campaign;
import com.gdf.poodle.repositories.entities.Question;
import com.gdf.poodle.repositories.entities.Survey;
import com.gdf.poodle.repositories.entities.questiontypes.YesNoQuestion;
import com.gdf.poodle.services.exceptions.SurveyException;

@Service
@Slf4j
public class SurveyServiceImplementation implements SurveyService {
	@Autowired
	SurveyRepository surveyRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	MailService mailService;

	/* (non-Javadoc)
	 * @see com.gdf.poodle.services.SurveyService#storeSurvey(com.gdf.poodle.controllers.forms.SurveyData, java.lang.Long)
	 */
	@Override
	public void storeSurvey(@Valid SurveyData surveyData, @NotNull Long campaignId) {
		Survey survey = new Survey();
		survey.setId(surveyData.getId());
		
		survey.setName(surveyData.getName());
		survey.setQuestions(new ArrayList<>());
		for (YesNoQuestionData question : surveyData.getQuestions()) {
			YesNoQuestion yesNoQuestion = new YesNoQuestion();
			yesNoQuestion.setAnswerNo(question.getAnswerNo());
			yesNoQuestion.setAnswerYes(question.getAnswerYes());
			yesNoQuestion.setId(question.getId());
			yesNoQuestion.setQuestion(question.getQuestion());
			survey.getQuestions().add(yesNoQuestion);
		}
		
		surveyRepository.storeSurvey(survey, campaignId);
	}
	
	
	/* (non-Javadoc)
	 * @see com.gdf.poodle.services.SurveyService#fetchSurvey(java.lang.Long)
	 */
	@Override
	public SurveyData fetchSurvey(Long surveyId) {
		Optional<Survey> survey = surveyRepository.fetchSurvey(surveyId);
		if (survey.isPresent()) {
			SurveyData surveyData = createSurveyData(survey.get());
			
			return surveyData;
		}
		else {
			throw new SurveyException();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.gdf.poodle.services.SurveyService#sendSurvey(Long surveyId, String userName) 
	 */
	@Override
	public void sendSurvey(Long surveyId, String systemUserName) {
		Optional<Survey> survey = surveyRepository.fetchSurvey(surveyId);
		if (survey.isPresent()) {
			Campaign campaign = survey.get().getCampaign();
			SurveyData surveyData = createSurveyData(survey.get());
			
			List<UserData> users = userService.getUserList(systemUserName);
			
			for (UserData user : users) {
				sendMail(user, surveyData, campaign.getId());
			}
		}
		else {
			throw new SurveyException();
		}
	}

	private void sendMail(UserData user, SurveyData surveyData, Long campaignId) {
		Long surveyId = surveyData.getId();
		Long userId = user.getId();
		String url = "http://localhost:8080/poodle/vote/" + campaignId +"/" + surveyId + "/" + userId + ".htm";
		String message = "Dear " + user.getName() +" \r\n\r\n"
					   + "I would like to ask you to click on a following link to answer the questions regarding " + surveyData.getName() + "\r\n"
					   + "\r\n"
					   + url 
					   + "\r\n" 
					   + "Thank you for your support! \r\n"
					   + "\r\n"
					   + "Best regards, \r\n"
					   + "P00dle system";
					
		mailService.sendMail("fecnek@gmail.com", user.getEmail(), "Request for voting", message);
		
		log.info(message);
	}


	private SurveyData createSurveyData(Survey survey) {
		SurveyData surveyData = new SurveyData();
		surveyData.setId(survey.getId());
		surveyData.setName(survey.getName());
		
		surveyData.setQuestions(new ArrayList<>());
		if (survey.getQuestions() != null) {
			for (Question question : survey.getQuestions()) {
				if (question instanceof YesNoQuestion) {
					YesNoQuestion yesNoQuestion = (YesNoQuestion) question;
					YesNoQuestionData data = new YesNoQuestionData();
					data.setId(yesNoQuestion.getId());;
					data.setAnswerNo(yesNoQuestion.getAnswerNo());
					data.setAnswerYes(yesNoQuestion.getAnswerYes());
					data.setOrderNumber(yesNoQuestion.getOrderNumber());
					data.setQuestion(yesNoQuestion.getQuestion());
					
					surveyData.getQuestions().add(data);
				}
			}
		}
		
		return surveyData;
	}
	

	/* (non-Javadoc)
	 * @see com.gdf.poodle.services.SurveyService#removeSurvey(java.lang.Long)
	 */
	@Override
	public void removeSurvey(Long surveyId) {
		surveyRepository.removeSurvey(surveyId);
	}
	
	
	/* (non-Javadoc)
	 * @see com.gdf.poodle.services.SurveyService#getSurveyList(java.lang.Long)
	 */
	@Override
	public List<SurveyData> getSurveyList(Long campaignId) {
		List<Survey> surveyList = surveyRepository.getSurveyList(campaignId);
		List<SurveyData> result = new ArrayList<>();
		for (Survey survey : surveyList) {
			SurveyData resultItem = createSurveyData(survey);
			result.add(resultItem);
		}
		
		return result;
	}
}
