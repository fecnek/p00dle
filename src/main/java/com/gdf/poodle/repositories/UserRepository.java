package com.gdf.poodle.repositories;

import java.util.List;
import java.util.Optional;

import com.gdf.poodle.repositories.entities.User;

public interface UserRepository {

	public abstract void storeUser(User userData, String systemUserName);

	public abstract Optional<User> fetchUser(Long userId);

	public abstract void removeUser(Long userId);

	public abstract List<User> getUserList(String systemUserName);

}