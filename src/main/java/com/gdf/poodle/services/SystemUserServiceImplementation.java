package com.gdf.poodle.services;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdf.poodle.controllers.forms.RegistrationData;
import com.gdf.poodle.repositories.SystemUserRepository;
import com.gdf.poodle.repositories.entities.SystemUser;
import com.gdf.poodle.services.exceptions.UserExistsException;

@Service
public class SystemUserServiceImplementation implements SystemUserService {
	@Autowired
	SystemUserRepository systemUserRepository;
	
	/* (non-Javadoc)
	 * @see com.gdf.poodle.services.SystemUserService#login(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<SystemUser> login(@NotNull String userName, @NotNull String password) {
		return systemUserRepository.findUserByNameAndPassword(userName, password);
	}
	
	/* (non-Javadoc)
	 * @see com.gdf.poodle.services.SystemUserService#registration(com.gdf.poodle.controllers.forms.RegistrationData)
	 */
	@Override
	public SystemUser registration(@Valid RegistrationData registrationData) {
		Optional<SystemUser> user = findUserByUserName(registrationData.getUserName());
		if (user.isPresent()) {		
			throw new UserExistsException();
		}
		
		SystemUser newUser = new SystemUser();
		newUser.setCampaigns(null);
		newUser.setName(registrationData.getUserName());
		newUser.setPassword(registrationData.getPassword());
		newUser.setUsers(null);
		
		return systemUserRepository.storeUser(newUser);
	}

	@Override
	public Optional<SystemUser> findUserByUserName(String userName) {
		return systemUserRepository.findUserByName(userName);
	}
}
