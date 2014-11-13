package com.gdf.poodle.controllers.forms;

import java.util.List;

import lombok.Data;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.gdf.poodle.controllers.forms.questions.YesNoQuestionData;
import com.gdf.poodle.services.Severity;

public @Data class SurveyData {
	
	private Long id;

	
	@NotBlank(message = "Survey's name is mandatory", payload = Severity.Error.class)
	@Length(min = 5, max = 20, message = "Survey's name length has to be between 5 and 20", payload = Severity.Error.class)
	private String name;
	private Boolean enabled;
	
	List<YesNoQuestionData> questions;
	
	private String addQuestion;
	private Integer delQuestion;

}
