package com.gdf.poodle.repositories;

import java.util.Optional;

import com.gdf.poodle.repositories.entities.SystemUser;

public interface SystemUserRepository {

	public Optional<SystemUser> findUserByNameAndPassword(String userName, String password);
	public Optional<SystemUser> findUserByName(String userName);

	public SystemUser storeUser(SystemUser user);

}