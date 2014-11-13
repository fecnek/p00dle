package com.gdf.poodle.repositories.entities;

import java.util.List;

import lombok.Data;

import com.gdf.poodle.persistence.annotations.Entity;
import com.gdf.poodle.persistence.annotations.PrimaryKey;

@Entity
public @Data class Survey {
	
	@PrimaryKey
	Long id;
	
	private String name;
	private Boolean enabled;
	private List<User> users;
	
	private List<Question> questions;
	private List<Answer> answers;
	
	private Campaign campaign;
}
