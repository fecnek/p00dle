package com.gdf.poodle.repositories;

import java.io.FileNotFoundException;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gdf.poodle.persistence.PersistenceManager;
import com.gdf.poodle.persistence.exceptions.IdFieldNotFoundException;
import com.gdf.poodle.persistence.exceptions.NoResultException;
import com.gdf.poodle.repositories.entities.SystemUser;

@Repository
@Slf4j
public class SystemUserRepositoryImplementation implements SystemUserRepository {
	
	
	@Autowired
	PersistenceManager persistenceManager;
	
	/* (non-Javadoc)
	 * @see com.gdf.poodle.repositories.SystemUserRepository#findUserByNameAndPassword(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<SystemUser> findUserByNameAndPassword(String userName, String password) {
		return persistenceManager.selectSingle(SystemUser.class, 
														   (SystemUser user) -> user.getName() == userName 
														   					&& user.getPassword() == password);
	}
	
	/* (non-Javadoc)
	 * @see com.gdf.poodle.repositories.SystemUserRepository#storeUser(com.gdf.poodle.repositories.entities.SystemUser)
	 */
	@Override
	public SystemUser storeUser(SystemUser user) {
			try {
				persistenceManager.merge(user);
				persistenceManager.flush();
			} catch (IllegalArgumentException | IllegalAccessException
					| IdFieldNotFoundException | NoResultException | FileNotFoundException e) {
				log.error("Unable to store system user (%s)", user);
			}
		
		return user;
	}

	@Override
	public Optional<SystemUser> findUserByName(String userName) {
		try {
			persistenceManager.refresh();
		} catch (FileNotFoundException exception) {
			log.error(exception.getMessage());
		}
		return persistenceManager.selectSingle(SystemUser.class, 
				   (SystemUser user) -> user.getName().equals(userName));
	}
	
	
}
