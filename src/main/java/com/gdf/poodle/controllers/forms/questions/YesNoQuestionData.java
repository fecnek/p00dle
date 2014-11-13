package com.gdf.poodle.controllers.forms.questions;

import lombok.Data;

import org.hibernate.validator.constraints.NotBlank;

public @Data class YesNoQuestionData extends QuestionData {
	private Long id;
	
	@NotBlank
	private String answerYes;
	@NotBlank
	private String answerNo;
}
