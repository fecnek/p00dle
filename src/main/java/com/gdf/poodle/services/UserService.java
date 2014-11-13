package com.gdf.poodle.services;

import java.util.List;

import com.gdf.poodle.controllers.forms.UserData;

public interface UserService {

	public abstract void storeUser(UserData userData, String systemUserName);

	public abstract UserData fetchUser(Long userId);

	public abstract void removeUser(Long userId);

	public abstract List<UserData> getUserList(String systemUserName);

}