package com.gdf.poodle.repositories.entities.answertypes;

import lombok.Data;

import com.gdf.poodle.persistence.annotations.Entity;
import com.gdf.poodle.persistence.annotations.PrimaryKey;
import com.gdf.poodle.repositories.entities.Answer;

@Entity
public @Data class YesNoAnswer extends Answer {

	@PrimaryKey
	private Long id;
	
	private boolean answer;
}
