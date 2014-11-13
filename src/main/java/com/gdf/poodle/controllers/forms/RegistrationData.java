package com.gdf.poodle.controllers.forms;

import javax.validation.constraints.AssertTrue;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.gdf.poodle.services.Severity;

import lombok.Data;

public @Data class RegistrationData {
	@NotBlank(message = "Username is mandatory", payload = Severity.Error.class)
	@Length(min = 5, max = 20, message = "System user name's length has to be between 5 and 20", payload = Severity.Error.class)
	private String userName;
	
	@NotBlank(message = "E-mail address is mandatory", payload = Severity.Error.class)
	@Email(message = "Invalid e-mail format", payload = Severity.Error.class)
	private String email;
	
	@NotBlank(message = "Password is mandatory", payload = Severity.Error.class)
	@Length(min = 5, max = 20, message = "Password's length has to be between 5 and 20", payload = Severity.Error.class)
	private String password;
	
	@NotBlank(message = "Retyped password is mandatory", payload = Severity.Error.class)
	@Length(min = 5, max = 20, message = "Retyped password's length has to be between 5 and 20", payload = Severity.Error.class)
	private String retypedPassword;
	
	@AssertTrue(message="Password should match retyped password")
    private boolean isValid(){
        if (password == null) {
            return retypedPassword == null;
        } else {
            return password.equals(retypedPassword);
        }
    }
}
