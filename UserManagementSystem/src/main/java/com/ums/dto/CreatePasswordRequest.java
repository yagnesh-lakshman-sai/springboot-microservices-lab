package com.ums.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreatePasswordRequest {

	@NotBlank(message = "Password is required")
	private String password;

	@NotBlank(message = "Confirm Password is required")
	private String confirmPassword;

	private String token;
}