package com.gdf.poodle.repositories.entities;

import java.util.List;

import lombok.Data;

import com.gdf.poodle.persistence.annotations.Entity;
import com.gdf.poodle.persistence.annotations.PrimaryKey;

@Entity
public @Data class SystemUser {

	@PrimaryKey
	Long id;
	
	private String name;
	private String password;
	
	private List<User> users;
	private List<Campaign> campaigns;
}
