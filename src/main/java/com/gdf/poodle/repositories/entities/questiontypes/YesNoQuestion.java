package com.gdf.poodle.repositories.entities.questiontypes;

import lombok.Data;

import com.gdf.poodle.persistence.annotations.Entity;
import com.gdf.poodle.persistence.annotations.PrimaryKey;
import com.gdf.poodle.repositories.entities.Question;

@Entity
public @Data class YesNoQuestion extends Question {
	
	@PrimaryKey
	private Long id;
	
	private String answerYes;
	private String answerNo;
}
