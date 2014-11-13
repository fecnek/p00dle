package com.gdf.poodle.controllers;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gdf.poodle.controllers.forms.UserData;
import com.gdf.poodle.services.UserService;

@Controller
public class UserManagementController {

	@Autowired
	UserService userService;
	
	@RequestMapping(value="/usermanagement",  method = RequestMethod.GET)
    public String openPage(ModelMap model, Principal principal ) { 
	 	String systemUserName = principal.getName();
	 	model.addAttribute("username", systemUserName);
	 	
	 	model.addAttribute("showUserForm", false);
	 	
	 	addUserListToModel(model, systemUserName);
        return "usermanagement";
    }

	
	@RequestMapping(value="/usermanagement/addUser",  method = RequestMethod.GET)
    public String addUser(ModelMap model, Principal principal ) { 
	 	String systemUserName = principal.getName();
	 	model.addAttribute("username", systemUserName);
	 	model.addAttribute("showUserForm", true);
	 	model.addAttribute("userForm", new UserData());

	 	addUserListToModel(model, systemUserName);

        return "usermanagement";
    }
	
	@RequestMapping(value="/usermanagement/modifyUser/{userId}",  method = RequestMethod.GET)
    public String addUser(@PathVariable("userId") Long userId, ModelMap model, Principal principal ) { 
	 	String systemUserName = principal.getName();
	 	model.addAttribute("username", systemUserName);
	 	model.addAttribute("showUserForm", true);
	 	
	 	UserData userData = userService.fetchUser(userId);
	 	model.addAttribute("userForm", userData);

	 	addUserListToModel(model, systemUserName);

        return "usermanagement";
    }
	
	@RequestMapping(value="/usermanagement/addUser",  method = RequestMethod.POST)
    public String addUser(@Valid UserData userForm, BindingResult bindingResult, ModelMap model, Principal principal ) { 
	 	String systemUserName = principal.getName();
	 	model.addAttribute("username", systemUserName);
	 
	 	if (bindingResult.hasErrors()) {
	 		model.addAttribute("errorList", bindingResult.getAllErrors());
	 		model.addAttribute("showUserForm", true);
		 	model.addAttribute("userForm", userForm);
		 	addUserListToModel(model, systemUserName);
		 	return "usermanagement";
	 	}
	 	else {
	 		model.addAttribute("showUserForm", false);	 		
	 	}
	 	
	 	userService.storeUser(userForm, systemUserName);
	 	
	 	addUserListToModel(model, systemUserName);

        return "usermanagement";
    }

	private void addUserListToModel(ModelMap model, String systemUserName) {
		List<UserData> users = userService.getUserList(systemUserName);
		model.addAttribute("userList", users);
	}
}
