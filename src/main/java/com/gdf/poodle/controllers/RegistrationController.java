package com.gdf.poodle.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gdf.poodle.controllers.forms.RegistrationData;
import com.gdf.poodle.services.SystemUserService;
import com.gdf.poodle.services.exceptions.UserExistsException;

@Controller
public class RegistrationController {
	
	@Autowired
	SystemUserService systemUserService;
	
    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public RegistrationData getNewForm() {
        return new RegistrationData();
    }
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@Valid RegistrationData registrationData, BindingResult result,  ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("errorList", result.getAllErrors());
			model.addAttribute("registrationForm", registrationData);
            return "registration";
        }
		try {
			systemUserService.registration(registrationData);
			model.addAttribute("successed", Boolean.TRUE);
			
		} catch (UserExistsException exception) {
			List<ObjectError> errors = new ArrayList<>();
			errors.add(new ObjectError("error", "The given username exists already! Please, choose an other one!"));
			model.addAttribute("errorList", errors);
			model.addAttribute("registrationForm", registrationData);

		}
        return "registration";   
    }
}
