package com.gdf.poodle.repositories.entities.questiontypes;

import lombok.Data;

import com.gdf.poodle.persistence.annotations.Entity;
import com.gdf.poodle.persistence.annotations.PrimaryKey;

@Entity
public @Data class ValueSetQuestion {

		@PrimaryKey
		private Long id;
		
		private Integer minValue;
		private Integer maxValue;
		
}
