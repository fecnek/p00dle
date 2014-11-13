package com.gdf.poodle.repositories.entities;

import lombok.Data;

public @Data abstract class Question {
	private String  question;
	private Integer orderNumber;
}
