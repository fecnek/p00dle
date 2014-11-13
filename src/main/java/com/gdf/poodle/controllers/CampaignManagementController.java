package com.gdf.poodle.controllers;

import java.security.Principal;
import java.text.SimpleDateFormat;
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
import com.gdf.poodle.services.CampaignService;

@Controller
public class CampaignManagementController {

	@Autowired
	CampaignService campaignService;
	
	@InitBinder
	private void dateBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value="/campaignmanagement",  method = RequestMethod.GET)
    public String openPage(ModelMap model, Principal principal ) { 
	 	String systemUserName = principal.getName();
	 	model.addAttribute("username", systemUserName);
	 	
	 	model.addAttribute("showCampaignForm", false);
	 	
	 	addCampaignListToModel(model, systemUserName);
        return "campaignmanagement";
    }

	
	@RequestMapping(value="/campaignmanagement/addCampaign",  method = RequestMethod.GET)
    public String addCampaign(ModelMap model, Principal principal ) { 
	 	String systemUserName = principal.getName();
	 	model.addAttribute("username", systemUserName);
	 	model.addAttribute("showCampaignForm", true);
	 	model.addAttribute("campaignForm", new CampaignData());

	 	addCampaignListToModel(model, systemUserName);

        return "campaignmanagement";
    }
	
	@RequestMapping(value="/campaignmanagement/modifyCampaign/{campaignId}",  method = RequestMethod.GET)
    public String addCampaign(@PathVariable("campaignId") Long campaignId, ModelMap model, Principal principal ) { 
	 	String systemUserName = principal.getName();
	 	model.addAttribute("username", systemUserName);
	 	model.addAttribute("showCampaignForm", true);
	 	
	 	CampaignData campaignData = campaignService.fetchCampaign(campaignId);
	 	model.addAttribute("campaignForm", campaignData);

	 	addCampaignListToModel(model, systemUserName);

        return "campaignmanagement";
    }
	
	@RequestMapping(value="/campaignmanagement/addCampaign",  method = RequestMethod.POST)
    public String addCampaign(@Valid CampaignData campaignForm, BindingResult bindingResult, ModelMap model, Principal principal ) { 
	 	String systemUserName = principal.getName();
	 	model.addAttribute("username", systemUserName);
	 
	 	if (bindingResult.hasErrors()) {
	 		model.addAttribute("errorList", bindingResult.getAllErrors());
	 		model.addAttribute("showCampaignForm", true);
		 	model.addAttribute("campaignForm", campaignForm);
		 	addCampaignListToModel(model, systemUserName);
		 	return "campaignmanagement";
	 	}
	 	else {
	 		model.addAttribute("showCampaignForm", false);	 		
	 	}
	 	
	 	campaignService.storeCampaign(campaignForm, systemUserName);
	 	
	 	addCampaignListToModel(model, systemUserName);

        return "campaignmanagement";
    }

	private void addCampaignListToModel(ModelMap model, String systemUserName) {
		List<CampaignData> campaigns = campaignService.getCampaignList(systemUserName);
		model.addAttribute("campaignList", campaigns);
	}
}
