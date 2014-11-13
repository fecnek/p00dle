package com.gdf.poodle.services;

import java.util.Optional;

import com.gdf.poodle.controllers.forms.RegistrationData;
import com.gdf.poodle.repositories.entities.SystemUser;

public interface SystemUserService {

	public abstract Optional<SystemUser> login(String userName, String password);
	public abstract Optional<SystemUser> findUserByUserName(String userName);

	
	public abstract SystemUser registration(RegistrationData registrationData);

}