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
public class VoteController {

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
	
	@RequestMapping(value="/vote/{campaignId}/{surveyId}/{userId}",  method = RequestMethod.GET)
    public String openPage(@PathVariable("campaignId") Long campaignId,
    					@PathVariable("surveyId") Long surveyId,
    					@PathVariable("userId") Long userId,
    		ModelMap model) { 
	 	CampaignData campaignData = campaignService.fetchCampaign(campaignId);		
		SurveyData surveyData = surveyService.fetchSurvey(surveyId);
		
		model.addAttribute("campaignForm", campaignData);
	 	model.addAttribute("surveyForm", surveyData);
	 	
        return "vote";
    }

	
	@RequestMapping(value="/vote/{campaignId}/{surveyId}/{userId}/storeAnswers",  method = RequestMethod.GET)
    public String storeAnswers(@PathVariable("campaignId") Long campaignId,
								@PathVariable("surveyId") Long surveyId,
								@PathVariable("userId") Long userId, 
								ModelMap model) { 
	 	
	 	model.addAttribute("showSurveyForm", true);
	 	model.addAttribute("surveyForm", new SurveyData());
	 	model.addAttribute("campaignId", campaignId);


	 	CampaignData campaign = campaignService.fetchCampaign(campaignId);
	 	model.addAttribute("campaignName", campaign.getName());


        return "surveymanagement";
    }
	
	
}
