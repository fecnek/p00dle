package com.gdf.poodle.controllers.forms;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.gdf.poodle.services.Severity;

import lombok.Data;

public @Data class UserData {
	private Long id;
	@NotBlank(message = "Username is mandatory", payload = Severity.Error.class)
	@Length(min = 5, max = 20, message = "Username's length has to be between 5 and 20", payload = Severity.Error.class)
	private String name;
	
	@NotBlank(message = "E-mail address is mandatory", payload = Severity.Error.class)
	@Email(message = "Invalid e-mail format", payload = Severity.Error.class)
	private String email;
}
