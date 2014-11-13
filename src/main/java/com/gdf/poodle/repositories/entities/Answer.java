package com.gdf.poodle.repositories.entities;

import lombok.Data;

public @Data abstract class Answer {
	private User user;
	private Question question;

}
