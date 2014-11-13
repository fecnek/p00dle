package com.gdf.poodle.controllers.forms.questions;

import lombok.Data;

public @Data abstract class QuestionData {
	private String  question;
	private Integer orderNumber;
}
