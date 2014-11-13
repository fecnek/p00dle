package com.gdf.poodle.repositories.entities;

import lombok.Data;

import com.gdf.poodle.persistence.annotations.Entity;
import com.gdf.poodle.persistence.annotations.PrimaryKey;

@Entity
public @Data class Tag {

	@PrimaryKey
	private Long id;
	
	private String name;
}
