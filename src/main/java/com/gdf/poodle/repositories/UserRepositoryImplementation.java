package com.gdf.poodle.repositories;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gdf.poodle.persistence.PersistenceManager;
import com.gdf.poodle.persistence.exceptions.IdFieldNotFoundException;
import com.gdf.poodle.persistence.exceptions.NoResultException;
import com.gdf.poodle.persistence.exceptions.UserException;
import com.gdf.poodle.repositories.entities.SystemUser;
import com.gdf.poodle.repositories.entities.User;
import com.gdf.poodle.repositories.exceptions.SystemUserDoesNotExists;

@Repository
@Slf4j
public class UserRepositoryImplementation implements UserRepository {
	@Autowired
	PersistenceManager persistenceManager;
	
	@Autowired
	SystemUserRepository systemUserRepository;
	
	/* (non-Javadoc)
	 * @see com.gdf.poodle.repositories.UserRepository#storeUser(com.gdf.poodle.repositories.entities.User, java.lang.String)
	 */
	@Override
	public void storeUser(User userData, String systemUserName) {
		Optional<SystemUser> systemUser = systemUserRepository.findUserByName(systemUserName);
		if (!systemUser.isPresent()) {
			throw new SystemUserDoesNotExists();
		}
		SystemUser user = systemUser.get();
		if (user.getUsers() == null ) {
			user.setUsers(new ArrayList<>());
		}
		user.getUsers().add(userData);
		userData.setOwner(user);
		
		try {
			persistenceManager.merge(userData);
			persistenceManager.flush();
		} catch (IllegalArgumentException | IllegalAccessException
				| IdFieldNotFoundException | NoResultException
				 | FileNotFoundException e) {
			throw new UserException();
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.gdf.poodle.repositories.UserRepository#fetchUser(java.lang.Long)
	 */
	@Override
	public Optional<User> fetchUser(Long userId) {
		Optional<User> result = Optional.empty();
		try {
			result = Optional.of(persistenceManager.find(User.class, userId));			
		} catch (NoResultException e) {
			log.error(e.getMessage());
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.gdf.poodle.repositories.UserRepository#removeUser(java.lang.Long)
	 */
	@Override
	public void removeUser(Long userId) {
		Optional<User> user = fetchUser(userId);
		if (user.isPresent()) {
			try {
				persistenceManager.remove(user.get());
				persistenceManager.flush();

			} catch (IllegalArgumentException | IllegalAccessException
					| NoResultException | IdFieldNotFoundException | FileNotFoundException e) {
				log.error(e.getMessage());
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.gdf.poodle.repositories.UserRepository#getUserList(java.lang.String)
	 */
	@Override
	public List<User> getUserList(String systemUserName) {
		return persistenceManager.select(User.class, (User user) -> user.getOwner() != null &&
																	user.getOwner().getName().equals(systemUserName));
	}
	
}
