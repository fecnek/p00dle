package com.gdf.poodle.controllers;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gdf.poodle.controllers.forms.CampaignData;
import com.gdf.poodle.controllers.forms.SurveyData;
import com.gdf.poodle.controllers.forms.questions.YesNoQuestionData;
import com.gdf.poodle.services.CampaignService;
import com.gdf.poodle.services.SurveyService;

@Controller
public class SurveyManagementController {

	@Autowired
	SurveyService surveyService;
	
	@Autowired
	CampaignService campaignService;
	
	@InitBinder
	private void dateBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value="/campaignmanagement/surveys/{campaignId}",  method = RequestMethod.GET)
    public String openPage(@PathVariable("campaignId") Long campaignId, ModelMap model, Principal principal ) { 
	 	String systemUserName = principal.getName();
	 	model.addAttribute("username", systemUserName);
	 	
	 	CampaignData campaign = campaignService.fetchCampaign(campaignId);
	 	model.addAttribute("campaignName", campaign.getName());
	 	model.addAttribute("showSurveyForm", false);
	 	model.addAttribute("campaignId", campaignId);

	 	
	 	addSurveyListToModel(model, campaignId);
        return "surveymanagement";
    }

	
	@RequestMapping(value="/campaignmanagement/surveys/{campaignId}/addSurvey",  method = RequestMethod.GET)
    public String addSurvey(@PathVariable("campaignId") Long campaignId, ModelMap model, Principal principal ) { 
	 	String systemUserName = principal.getName();
	 	model.addAttribute("username", systemUserName);
	 	model.addAttribute("showSurveyForm", true);
	 	model.addAttribute("surveyForm", new SurveyData());
	 	model.addAttribute("campaignId", campaignId);


	 	CampaignData campaign = campaignService.fetchCampaign(campaignId);
	 	model.addAttribute("campaignName", campaign.getName());

	 	addSurveyListToModel(model, campaignId);

        return "surveymanagement";
    }
	
	@RequestMapping(value="/campaignmanagement/surveys/{campaignId}/modifySurvey/{surveyId}",  method = RequestMethod.GET)
    public String modifySurvey(@PathVariable("campaignId") Long campaignId,
    		@PathVariable("surveyId") Long surveyId, ModelMap model, Principal principal ) { 
	 	String systemUserName = principal.getName();
	 	model.addAttribute("username", systemUserName);
	 	model.addAttribute("showSurveyForm", true);
	 	model.addAttribute("campaignId", campaignId);

	 	
	 	CampaignData campaign = campaignService.fetchCampaign(campaignId);
	 	model.addAttribute("campaignName", campaign.getName());

	 	SurveyData surveyData = surveyService.fetchSurvey(surveyId);
	 	model.addAttribute("surveyForm", surveyData);

	 	addSurveyListToModel(model, campaignId);

        return "surveymanagement";
    }
	
	@RequestMapping(value="/campaignmanagement/surveys/{campaignId}/sendEmail/{surveyId}",  method = RequestMethod.GET)
    public String sendEmail(@PathVariable("campaignId") Long campaignId,
    		@PathVariable("surveyId") Long surveyId, ModelMap model, Principal principal ) { 
	 	String systemUserName = principal.getName();
	 	model.addAttribute("username", systemUserName);
	 	model.addAttribute("showSurveyForm", false);
	 	model.addAttribute("campaignId", campaignId);

	 	
	 	CampaignData campaign = campaignService.fetchCampaign(campaignId);
	 	model.addAttribute("campaignName", campaign.getName());

	 	surveyService.sendSurvey(surveyId, systemUserName);

	 	addSurveyListToModel(model, campaignId);

        return "surveymanagement";
    }
	
	@RequestMapping(value="/campaignmanagement/surveys/{campaignId}/addSurvey",  method = RequestMethod.POST)
    public String addSurvey(@PathVariable("campaignId") Long campaignId, 
    		@Valid SurveyData surveyForm, 
    		BindingResult bindingResult, ModelMap model, Principal principal ) { 
	 	String systemUserName = principal.getName();
	 	model.addAttribute("username", systemUserName);
	 	
	 	CampaignData campaign = campaignService.fetchCampaign(campaignId);
	 	model.addAttribute("campaignName", campaign.getName());
	 	model.addAttribute("campaignId", campaignId);

	 	if ("true".equals(surveyForm.getAddQuestion())) {
	 		if (surveyForm.getQuestions() == null) {
	 			surveyForm.setQuestions(new ArrayList<>());
	 		}
	 		surveyForm.getQuestions().add(new YesNoQuestionData());
	 		model.addAttribute("showSurveyForm", true);
		 	model.addAttribute("surveyForm", surveyForm);
		 	addSurveyListToModel(model, campaignId);
		 	return "surveymanagement";
	 	}
	 	if (surveyForm.getDelQuestion() != null && surveyForm.getQuestions() != null) {
	 		surveyForm.getQuestions().remove(surveyForm.getDelQuestion().intValue());
	 		model.addAttribute("showSurveyForm", true);
		 	model.addAttribute("surveyForm", surveyForm);
		 	addSurveyListToModel(model, campaignId);
		 	return "surveymanagement";
	 	}
	 	
	 	if (bindingResult.hasErrors()) {
	 		model.addAttribute("errorList", bindingResult.getAllErrors());
	 		model.addAttribute("showSurveyForm", true);
		 	model.addAttribute("surveyForm", surveyForm);
		 	addSurveyListToModel(model, campaignId);
		 	return "surveymanagement";
	 	}
	 	else {
	 		model.addAttribute("showSurveyForm", false);	 		
	 	}
	 	
	 	surveyService.storeSurvey(surveyForm, campaignId);
	 	
	 	addSurveyListToModel(model, campaignId);

        return "surveymanagement";
    }

	private void addSurveyListToModel(ModelMap model, Long campaignId ) {
		List<SurveyData> surveys = surveyService.getSurveyList(campaignId);
		model.addAttribute("surveyList", surveys);
	}
}
