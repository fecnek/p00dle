package com.gdf.poodle.repositories.entities;

import java.util.List;

import lombok.Data;

import com.gdf.poodle.persistence.annotations.Entity;
import com.gdf.poodle.persistence.annotations.PrimaryKey;

@Entity
public @Data class User {

	@PrimaryKey
	private Long id;
	
	private String name;
	private String email;
	
	private List<Tag> tags;
	private SystemUser owner;
}
