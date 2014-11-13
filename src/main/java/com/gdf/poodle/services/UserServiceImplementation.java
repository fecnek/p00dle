package com.gdf.poodle.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdf.poodle.controllers.forms.UserData;
import com.gdf.poodle.persistence.exceptions.UserException;
import com.gdf.poodle.repositories.UserRepository;
import com.gdf.poodle.repositories.entities.User;

@Service
public class UserServiceImplementation implements UserService {
	@Autowired
	UserRepository userRepository;
	
	/* (non-Javadoc)
	 * @see com.gdf.poodle.services.UserService#storeUser(com.gdf.poodle.controllers.forms.UserData, java.lang.String)
	 */
	@Override
	public void storeUser(@Valid UserData userData, @NotNull String systemUserName) {
		User user = new User();
		user.setId(userData.getId());
		user.setEmail(userData.getEmail());
		user.setName(userData.getName());
		userRepository.storeUser(user, systemUserName);
	}
	
	/* (non-Javadoc)
	 * @see com.gdf.poodle.services.UserService#fetchUser(java.lang.Long)
	 */
	@Override
	public UserData fetchUser(Long userId) {
		Optional<User> user = userRepository.fetchUser(userId);
		if (user.isPresent()) {
			UserData userData = createUserData(user.get());
			
			return userData;
		}
		else {
			throw new UserException();
		}
	}

	private UserData createUserData(User user) {
		UserData userData = new UserData();
		userData.setId(user.getId());
		userData.setName(user.getName());
		userData.setEmail(user.getEmail());
		return userData;
	}
	
	/* (non-Javadoc)
	 * @see com.gdf.poodle.services.UserService#removeUser(java.lang.Long)
	 */
	@Override
	public void removeUser(Long userId) {
		userRepository.removeUser(userId);
	}
	
	/* (non-Javadoc)
	 * @see com.gdf.poodle.services.UserService#getUserList(java.lang.String)
	 */
	@Override
	public List<UserData> getUserList(String systemUserName) {
		List<User> userList = userRepository.getUserList(systemUserName);
		List<UserData> result = new ArrayList<>();
		for (User user : userList) {
			UserData resultItem = createUserData(user);
			result.add(resultItem);
		}
		
		return result;
	}
}
